package com.linkworld.wormgame.screen

import com.linkworld.wormgame.constants.Assets
import com.linkworld.wormgame.framework.abstracts.Screen
import com.linkworld.wormgame.framework.interfaces.Game
import com.linkworld.wormgame.framework.interfaces.Graphics
import com.linkworld.wormgame.framework.interfaces.Input
import com.linkworld.wormgame.screen.settings.Settings

/**
 * @author wangyu
 * @date 18-3-24
 * @describe TODO 游戏加分函数，尚未实现加分功能。数据为假数据。
 */
class HighScoreScreen(game: Game) : Screen(game) {
    // 保存五行的文字数据
    private val lines = arrayOfNulls<String>(5)

    init {
        for (i in 0..4) lines[i] = "" + (i + 1) + ". " + Settings.highscores[i]
    }

    override fun update(deltaTime: Float) {
        val touchEvents = game.getInput().getTouchEvents()

        val len = touchEvents.size
        for (i in 0 until len) {
            val event = touchEvents.get(i)
            when (event.type) {
                Input.TouchEvent.TOUCH_UP -> {
                    if (event.x < 64 && event.y > 416) {
                        if (Settings.soundEnabled) Assets.click.play(1.toFloat())
                        game.setScreen(MainMenuScreen(game))
                        return
                    }
                }
            }
        }
    }

    /**
     * 贴图
     *
     * @param deltaTime 延迟绘制的时间
     * */
    override fun present(deltaTime: Float) {
        val g = game.getGraphics()

        g.drawPixmap(Assets.background, 0, 0)
        g.drawPixmap(Assets.mainMenu, 64, 20, 0, 42, 196, 42)

        var y = 100
        for (i in 0..4) {
            drawText(g, lines[i], 20, y)
            y += 50
        }

        g.drawPixmap(Assets.buttons, 0, 416, 64, 64, 64, 64)
    }

    /**
     * 绘制文字
     * */
    private fun drawText(g: Graphics, line: String?, x: Int, y: Int) {
        if (line == null) return

        var x = x
        val len = line.length
        for (i in 0 until len) {
            val character = line[i]

            if (character == ' ') {
                x += 20 // 累加分数
                continue
            }

            var srcX: Int
            var srcWidth: Int
            when (character) {
                '.' -> {
                    srcX = 200
                    srcWidth = 10
                }
                else -> {
                    srcX = (character - '0') * 20
                    srcWidth = 20
                }
            }

            // 绘制文字
            g.drawPixmap(Assets.numbers, x, y, srcX, 0, srcWidth, 32)
            x += srcWidth
        }
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun dispose() {

    }
}
