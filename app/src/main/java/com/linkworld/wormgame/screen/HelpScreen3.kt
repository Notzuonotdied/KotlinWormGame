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
class HelpScreen3(game: Game) : Screen(game) {

    override fun update(deltaTime: Float) {
        val touchEvents = game.getInput().getTouchEvents()

        val len = touchEvents.size
        for (i in 0 until len) {
            val event = touchEvents[i]
            if (event.type != Input.TouchEvent.TOUCH_UP) continue
            // 判断是否点击了下一页的按钮
            if (event.x > 256 && event.y > 416) {
                // 跳转到主页面
                game.setScreen(MainMenuScreen(game))
                if (Settings.soundEnabled) Assets.click.play(1.toFloat())
                return
            }
        }
    }

    override fun present(deltaTime: Float) {
        val g = game.getGraphics()
        g.drawPixmap(Assets.background, 0, 0)
        g.drawPixmap(Assets.help3, 64, 100)
        g.drawPixmap(Assets.buttons, 256, 416, 0, 128, 64, 64)
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun dispose() {

    }

}
