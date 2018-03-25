package com.linkworld.wormgame.framework.impls.events

import android.view.View
import com.linkworld.wormgame.framework.interfaces.Input

/**
 * @author wangyu
 * @date 18-3-24
 * @describe TODO
 */
interface TouchHandler : View.OnTouchListener {

    fun isTouchDown(pointer: Int): Boolean

    fun getTouchX(pointer: Int): Int

    fun getTouchY(pointer: Int): Int

    fun getTouchEvents(): List<Input.TouchEvent>
}