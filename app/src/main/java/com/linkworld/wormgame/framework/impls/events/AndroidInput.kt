package com.linkworld.wormgame.framework.impls.events

import android.content.Context
import android.view.View
import com.linkworld.wormgame.framework.interfaces.Input
import com.linkworld.wormgame.framework.interfaces.Input.TouchEvent

/**
 * @author wangyu
 * @date 18-3-24
 * @describe 安卓的输入事件，触摸或者是按键事件
 */
class AndroidInput(context: Context, view: View, scaleX: Float, scaleY: Float) : Input {

    // 加速度传感器
    private val accelHandler: AccelerometerHandler = AccelerometerHandler(context)
    // 触摸事件
    private var touchHandler: TouchHandler = MultiTouchHandler(view, scaleX, scaleY)

    override fun getAccelX(): Float {
        return accelHandler.accelX
    }

    override fun getAccelY(): Float {
        return accelHandler.accelY
    }

    override fun getAccelZ(): Float {
        return accelHandler.accelZ
    }

    override fun getTouchEvents(): List<TouchEvent> {
        return touchHandler.getTouchEvents()
    }

    override fun isTouchDown(pointer: Int): Boolean {
        return touchHandler.isTouchDown(pointer)
    }

    override fun getTouchX(pointer: Int): Int {
        return touchHandler.getTouchX(pointer)
    }

    override fun getTouchY(pointer: Int): Int {
        return touchHandler.getTouchY(pointer)
    }
}
