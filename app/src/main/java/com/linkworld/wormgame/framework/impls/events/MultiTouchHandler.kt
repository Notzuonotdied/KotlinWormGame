package com.linkworld.wormgame.framework.impls.events

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import com.linkworld.wormgame.framework.Pool
import com.linkworld.wormgame.framework.interfaces.Input.TouchEvent
import java.util.*

/**
 * @author wangyu
 * @date 18-3-24
 * @describe 多点触控事件处理
 */
class MultiTouchHandler(view: View, private val scaleX: Float, private val scaleY: Float) : TouchHandler {

    private val isTouched = BooleanArray(MAX_TOUCHPOINTS)
    private val touchX = IntArray(MAX_TOUCHPOINTS)
    private val touchY = IntArray(MAX_TOUCHPOINTS)
    private val id = IntArray(MAX_TOUCHPOINTS)
    private val touchEventPool: Pool<TouchEvent>
    private val touchEvents = ArrayList<TouchEvent>()
    private val touchEventsBuffer = ArrayList<TouchEvent>()

    init {
        val factory = object : Pool.PoolObjectFactory<TouchEvent> {
            override fun createObject(): TouchEvent {
                return TouchEvent()
            }
        }
        touchEventPool = Pool(factory, 100)
        view.setOnTouchListener(this)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        synchronized(this) {
            val action = event.action and MotionEvent.ACTION_MASK
            val pointerIndex = event.action and MotionEvent.ACTION_POINTER_ID_MASK shr MotionEvent.ACTION_POINTER_ID_SHIFT
            val pointerCount = event.pointerCount
            var touchEvent: TouchEvent
            for (i in 0 until MAX_TOUCHPOINTS) {
                if (i >= pointerCount) {
                    isTouched[i] = false
                    id[i] = -1
                    continue
                }
                // 同一点的id值保持不变
                val pointerId = event.getPointerId(i)
                if (event.action != MotionEvent.ACTION_MOVE && i != pointerIndex) {
                    // if it's an up/down/cancel/out event, mask the id to see if we should process it for this touch
                    // point
                    continue
                }
                when (action) {
                    MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                        touchEvent = touchEventPool.newObject()
                        touchEvent.type = TouchEvent.TOUCH_DOWN
                        touchEvent.pointer = pointerId
                        touchX[i] = (event.getX(i) * scaleX).toInt()
                        touchEvent.x = touchX[i]
                        touchY[i] = (event.getY(i) * scaleY).toInt()
                        touchEvent.y = touchY[i]
                        isTouched[i] = true
                        id[i] = pointerId
                        touchEventsBuffer.add(touchEvent)
                    }
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP, MotionEvent.ACTION_CANCEL -> {
                        touchEvent = touchEventPool.newObject()
                        touchEvent.type = TouchEvent.TOUCH_UP
                        touchEvent.pointer = pointerId
                        touchX[i] = (event.getX(i) * scaleX).toInt()
                        touchEvent.x = touchX[i]
                        touchY[i] = (event.getY(i) * scaleY).toInt()
                        touchEvent.y = touchY[i]
                        isTouched[i] = false
                        id[i] = -1
                        touchEventsBuffer.add(touchEvent)
                    }
                    MotionEvent.ACTION_MOVE -> {
                        touchEvent = touchEventPool.newObject()
                        touchEvent.type = TouchEvent.TOUCH_DRAGGED
                        touchEvent.pointer = pointerId
                        touchX[i] = (event.getX(i) * scaleX).toInt()
                        touchEvent.x = touchX[i]
                        touchY[i] = (event.getY(i) * scaleY).toInt()
                        touchEvent.y = touchY[i]
                        isTouched[i] = true
                        id[i] = pointerId
                        touchEventsBuffer.add(touchEvent)
                    }
                }
            }
            return true
        }
    }

    override fun isTouchDown(pointer: Int): Boolean {
        synchronized(this) {
            val index = getIndex(pointer)
            return !(index < 0 || index >= MAX_TOUCHPOINTS) && isTouched[index]
        }
    }

    override fun getTouchX(pointer: Int): Int {
        synchronized(this) {
            val index = getIndex(pointer)
            return if (index < 0 || index >= MAX_TOUCHPOINTS) 0 else touchX[index]
        }
    }

    override fun getTouchY(pointer: Int): Int {
        synchronized(this) {
            val index = getIndex(pointer)
            return if (index < 0 || index >= MAX_TOUCHPOINTS) 0 else touchY[index]
        }
    }

    /**
     * 获取点击的位置
     *
     * @return 返回多点触控的事件列表
     * */
    override fun getTouchEvents(): List<TouchEvent> {
        synchronized(this) {
            val len = touchEvents.size
            for (i in 0 until len) touchEventPool.free(touchEvents[i])
            touchEvents.clear()
            touchEvents.addAll(touchEventsBuffer)
            touchEventsBuffer.clear()
            return touchEvents
        }
    }

    /**
     * 返回索引
     *
     * @param pointerId 触摸位置的索引
     * @return          如果不存在就返回-1
     * */
    private fun getIndex(pointerId: Int): Int {
        for (i in 0 until MAX_TOUCHPOINTS) {
            if (id[i] == pointerId) return i
        }
        return -1
    }

    companion object {
        /**
         * 多点触控的最大数量
         * */
        private const val MAX_TOUCHPOINTS = 10
    }
}
