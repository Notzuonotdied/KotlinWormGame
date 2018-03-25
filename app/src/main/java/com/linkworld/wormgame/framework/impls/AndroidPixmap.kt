package com.linkworld.wormgame.framework.impls

import android.graphics.Bitmap
import com.linkworld.wormgame.framework.interfaces.Graphics.PixmapFormat
import com.linkworld.wormgame.framework.interfaces.Pixmap

/**
 * @author wangyu
 * @date 18-3-24
 * @describe TODO
 */
class AndroidPixmap(internal var bitmap: Bitmap, private val format: PixmapFormat) : Pixmap {
    override fun getFormat(): PixmapFormat {
        return format
    }

    override fun dispose() {
        return bitmap.recycle()
    }

    override fun getWidth(): Int {
        return bitmap.width
    }

    override fun getHeight(): Int {
        return bitmap.height
    }

}