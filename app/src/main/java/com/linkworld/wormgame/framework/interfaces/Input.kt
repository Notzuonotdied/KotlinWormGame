package com.linkworld.wormgame.framework.interfaces

/**
 * @author wangyu
 * @date 18-3-24
 * @describe TODO
 */
interface Input {

    /**
     * 是否有触摸按下
     *
     * @param pointer
     * */
    fun isTouchDown(pointer: Int): Boolean

    /**
     * 获取触摸的X坐标
     * */
    fun getTouchX(pointer: Int): Int

    /**
     * 获取触摸的Y坐标
     * */
    fun getTouchY(pointer: Int): Int

    /**
     * 获取三轴传感器的X轴加速度
     * */
    fun getAccelX(): Float

    /**
     * 获取三轴传感器的Y轴加速度
     * */
    fun getAccelY(): Float

    /**
     * 获取三轴传感器的Z轴加速度
     * */
    fun getAccelZ(): Float

    /**
     * 触摸事件
     * */
    fun getTouchEvents(): List<TouchEvent>

    class TouchEvent {

        var type: Int = 0
        var x: Int = 0
        var y: Int = 0
        var pointer: Int = 0

        override fun toString(): String {
            val builder = StringBuilder()
            if (type == TOUCH_DOWN) {
                builder.append("touch down, ")
            } else if (type == TOUCH_DRAGGED) {
                builder.append("touch dragged, ")
            } else {
                builder.append("touch up, ")
            }
            builder.append(pointer)
            builder.append(",")
            builder.append(x)
            builder.append(",")
            builder.append(y)
            return builder.toString()
        }

        /**
         * 伴生对象
         * */
        companion object {
            const val TOUCH_DOWN = 0
            const val TOUCH_UP = 1
            const val TOUCH_DRAGGED = 2
        }
    }
}