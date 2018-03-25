package com.linkworld.wormgame.framework.impls

import android.content.res.AssetManager
import android.graphics.*
import com.linkworld.wormgame.framework.interfaces.Graphics
import com.linkworld.wormgame.framework.interfaces.Graphics.PixmapFormat
import com.linkworld.wormgame.framework.interfaces.Pixmap
import java.io.IOException
import java.io.InputStream

/**
 * @author wangyu
 * @date 18-3-24
 * @describe TODO
 */
class AndroidGraphics(private val assets: AssetManager, private val frameBuffer: Bitmap) : Graphics {
    private val canvas: Canvas = Canvas(frameBuffer)
    private val paint: Paint = Paint()
    private val srcRect = Rect()
    private val dstRect = Rect()

    override fun newPixmap(fileName: String, format: PixmapFormat): Pixmap {
        var format = format

        val config = when (format) {
            PixmapFormat.RGB565 -> Bitmap.Config.RGB_565
            PixmapFormat.ARGB4444 -> Bitmap.Config.ARGB_4444
            else -> Bitmap.Config.ARGB_8888
        }

        val options = BitmapFactory.Options()
        options.inPreferredConfig = config

        var inputStream: InputStream? = null
        val bitmap: Bitmap?
        try {
            inputStream = assets.open(fileName)
            bitmap = BitmapFactory.decodeStream(inputStream, null, options)
            if (bitmap == null) {
                throw RuntimeException("Couldn't load bitmap from asset $fileName")
            }
        } catch (e: IOException) {
            throw RuntimeException("Couldn't load bitmap from asset $fileName")
        } finally {
            inputStream?.close()
        }

        format = when (bitmap!!.config) {
            Bitmap.Config.RGB_565 -> PixmapFormat.RGB565
            Bitmap.Config.ARGB_4444 -> PixmapFormat.ARGB4444
            else -> PixmapFormat.ARGB8888
        }

        return AndroidPixmap(bitmap, format)
    }

    override fun clear(color: Int) {
        canvas.drawRGB(color and 0xff0000 shr 16, color and 0xff00 shr 8, color and 0xff)
    }

    override fun getWidth(): Int {
        return frameBuffer.width
    }

    override fun getHeight(): Int {
        return frameBuffer.height
    }

    override fun drawPixel(x: Int, y: Int, color: Int) {
        paint.color = color
        canvas.drawPoint(x.toFloat(), y.toFloat(), paint)
    }

    override fun drawLine(x: Int, y: Int, x2: Int, y2: Int, color: Int) {
        paint.color = color
        canvas.drawLine(x.toFloat(), y.toFloat(), x2.toFloat(), y2.toFloat(), paint)
    }

    override fun drawRect(x: Int, y: Int, width: Int, height: Int, color: Int) {
        paint.color = color
        paint.style = Paint.Style.FILL
        canvas.drawRect(x.toFloat(), y.toFloat(), (x + width - 1).toFloat(), (y + height - 1).toFloat(), paint)
    }

    override fun drawPixmap(pixmap: Pixmap, x: Int, y: Int, srcX: Int, srcY: Int,
                            srcWidth: Int, srcHeight: Int) {
        srcRect.left = srcX
        srcRect.top = srcY
        srcRect.right = srcX + srcWidth - 1
        srcRect.bottom = srcY + srcHeight - 1

        dstRect.left = x
        dstRect.top = y
        dstRect.right = x + srcWidth - 1
        dstRect.bottom = y + srcHeight - 1

        canvas.drawBitmap((pixmap as AndroidPixmap).bitmap, srcRect, dstRect, null)
    }

    override fun drawPixmap(pixmap: Pixmap, x: Int, y: Int) {
        canvas.drawBitmap((pixmap as AndroidPixmap).bitmap, x.toFloat(), y.toFloat(), null)
    }
}