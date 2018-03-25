package com.linkworld.wormgame.framework.interfaces

/**
 * @author wangyu
 * @date 18-3-24
 * @describe TODO
 */
interface Pixmap {
    fun getWidth(): Int

    fun getHeight(): Int

    fun getFormat(): Graphics.PixmapFormat

    fun dispose()
}