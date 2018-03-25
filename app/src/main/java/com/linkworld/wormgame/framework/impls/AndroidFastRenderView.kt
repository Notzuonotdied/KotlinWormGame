package com.linkworld.wormgame.framework.impls

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Rect
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.linkworld.wormgame.BaseAndroidGame

/**
 * @author wangyu
 * @date 18-3-24
 * @describe 游戏界面渲染
 */
@SuppressLint("ViewConstructor")
class AndroidFastRenderView(internal var game: BaseAndroidGame, internal var framebuffer: Bitmap) : SurfaceView(game), Runnable {
    internal var renderThread: Thread? = null
    internal var holder: SurfaceHolder

    @Volatile
    internal var running = false

    init {
        this.holder = getHolder()
    }

    fun resume() {
        running = true
        renderThread = Thread(this)
        renderThread!!.start()
    }

    override fun run() {
        val dstRect = Rect()
        var startTime = System.nanoTime()
        while (running) {
            if (!holder.surface.isValid) continue

            val deltaTime = (System.nanoTime() - startTime) / 1000000000.0f
            startTime = System.nanoTime()

            game.getCurrentScreen().update(deltaTime)
            game.getCurrentScreen().present(deltaTime)

            val canvas = holder.lockCanvas()
            canvas.getClipBounds(dstRect)
            canvas.drawBitmap(framebuffer, null, dstRect, null)
            holder.unlockCanvasAndPost(canvas)
        }
    }

    fun pause() {
        running = false
        while (true) {
            renderThread!!.join()
            break
        }
    }
}