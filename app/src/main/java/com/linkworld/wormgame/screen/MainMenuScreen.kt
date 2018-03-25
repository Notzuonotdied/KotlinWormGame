package com.linkworld.wormgame.screen

import com.linkworld.wormgame.constants.Assets
import com.linkworld.wormgame.framework.abstracts.Screen
import com.linkworld.wormgame.framework.interfaces.Game
import com.linkworld.wormgame.framework.interfaces.Input
import com.linkworld.wormgame.screen.settings.Settings

/**
 * @author wangyu
 * @date 18-3-24
 * @describe TODO
 */
class MainMenuScreen internal constructor(game: Game) : Screen(game) {

    override fun update(deltaTime: Float) {

        val g = game.getGraphics()
        // 获取触摸事件
        val touchEvents = game.getInput().getTouchEvents()

        val len = touchEvents.size
        for (i in 0 until len) {
            val event = touchEvents[i]
            if (event.type != Input.TouchEvent.TOUCH_UP) continue
            when {// 设置声音
                inBounds(event, 0, g.getHeight() - 64, 64, 64) -> {
                    Settings.soundEnabled = !Settings.soundEnabled
                    if (Settings.soundEnabled) {
                        Assets.click.play(1.toFloat())
                    }
                }// 开始游戏
                inBounds(event, 64, 220, 192, 42) -> {
                    game.setScreen(GameScreen(game))
                    if (Settings.soundEnabled) {
                        Assets.click.play(1.toFloat())
                    }
                    return
                }// 分数面板
                inBounds(event, 64, 220 + 42, 192, 42) -> {
                    game.setScreen(HighScoreScreen(game))
                    if (Settings.soundEnabled) {
                        Assets.click.play(1.toFloat())
                    }
                    return
                }// 帮助面板
                inBounds(event, 64, 220 + 84, 192, 42) -> {
                    game.setScreen(HelpScreen(game))
                    if (Settings.soundEnabled) {
                        Assets.click.play(1.toFloat())
                    }
                    return
                }
            }
        }
    }

    /**
     * 判断触摸的位置是否在位图的区域内
     *
     * @param event  触摸事件
     * @param x      触摸位置的X坐标
     * @param y      触摸位置的Y坐标
     * @param width  宽度
     * @param height 高度
     * */
    private fun inBounds(event: Input.TouchEvent, x: Int, y: Int, width: Int, height: Int): Boolean {
        return event.x > x && event.x < x + width - 1 && event.y > y && event.y < y + height - 1
    }

    /**
     * 贴图
     *
     * @param deltaTime 延迟绘制的时间
     * */
    override fun present(deltaTime: Float) {
        val g = game.getGraphics()

        g.drawPixmap(Assets.background, 0, 0)
        g.drawPixmap(Assets.logo, 32, 20)
        g.drawPixmap(Assets.mainMenu, 64, 220)
        when (Settings.soundEnabled) {
            true -> g.drawPixmap(Assets.buttons, 0, 416, 0, 0, 64, 64)
            else -> g.drawPixmap(Assets.buttons, 0, 416, 64, 0, 64, 64)
        }
    }

    /**
     * 暂停的时候，保存游戏的状态
     * */
    override fun pause() {
        Settings.save(game.getFileIO())
    }

    override fun resume() {

    }

    override fun dispose() {

    }
}