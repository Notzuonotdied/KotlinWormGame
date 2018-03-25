package com.linkworld.wormgame

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Bundle
import android.os.PowerManager
import android.view.Window
import android.view.WindowManager
import com.linkworld.wormgame.framework.abstracts.Screen
import com.linkworld.wormgame.framework.impls.AndroidFastRenderView
import com.linkworld.wormgame.framework.impls.AndroidFileIO
import com.linkworld.wormgame.framework.impls.AndroidGraphics
import com.linkworld.wormgame.framework.impls.events.AndroidInput
import com.linkworld.wormgame.framework.impls.sounds.AndroidAudio
import com.linkworld.wormgame.framework.interfaces.*

/**
 * @author wangyu
 * @date 18-3-24
 * @describe 游戏逻辑的具体实现类
 */
abstract class BaseAndroidGame : Activity(), Game {
    // 渲染
    internal lateinit var renderView: AndroidFastRenderView
    // 绘图
    internal lateinit var graphics: Graphics
    // 声音
    internal lateinit var audio: Audio
    // 输入
    internal lateinit var input: Input
    // 文件流
    internal lateinit var fileIO: FileIO
    // 游戏界面
    internal lateinit var screen: Screen
    // 电源管理，主要是为了防止手机锁屏
    internal lateinit var wakeLock: PowerManager.WakeLock

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 设置为全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        val frameBufferWidth = if (isLandscape) 480 else 320
        val frameBufferHeight = if (isLandscape) 320 else 480
        val frameBuffer = Bitmap.createBitmap(frameBufferWidth, frameBufferHeight, Bitmap.Config.RGB_565)

        val scaleX = frameBufferWidth.toFloat() / windowManager.defaultDisplay.width
        val scaleY = frameBufferHeight.toFloat() / windowManager.defaultDisplay.height

        // 初始化
        renderView = AndroidFastRenderView(this, frameBuffer)
        graphics = AndroidGraphics(assets, frameBuffer)
        // 文件处理
        fileIO = AndroidFileIO(this)
        audio = AndroidAudio(this)
        // 安卓的输入事件，触摸或者是按键事件
        input = AndroidInput(this, renderView, scaleX, scaleY)
        screen = getStartScreen()

        setContentView(renderView)

        // 禁止屏幕自动熄屏
        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GLGame")
    }

    public override fun onResume() {
        super.onResume()
        // 保持亮屏10分钟
        wakeLock.acquire(10 * 60 * 1000L)
        screen.resume()
        renderView.resume()
    }

    public override fun onPause() {
        super.onPause()
        wakeLock.release()
        renderView.pause()
        screen.pause()

        if (isFinishing) screen.dispose()
    }

    override fun getInput(): Input {
        return input
    }

    override fun getFileIO(): FileIO {
        return fileIO
    }

    override fun getGraphics(): Graphics {
        return graphics
    }

    override fun getAudio(): Audio {
        return audio
    }

    override fun setScreen(screen: Screen) {
        this.screen.pause()
        this.screen.dispose()
        screen.resume()
        screen.update(0.toFloat())
        this.screen = screen
    }

    override fun getCurrentScreen(): Screen {
        return screen
    }
}