package com.linkworld.wormgame.screen

import com.linkworld.wormgame.constants.Assets
import com.linkworld.wormgame.framework.abstracts.Screen
import com.linkworld.wormgame.framework.interfaces.Game
import com.linkworld.wormgame.framework.interfaces.Graphics.PixmapFormat
import com.linkworld.wormgame.screen.settings.Settings

/**
 * @author wangyu
 * @date 18-3-24
 * @describe 加载游戏需要的资源数据
 */
class LoadingScreen(game: Game) : Screen(game) {
    override fun update(deltaTime: Float) {
        val g = game.getGraphics()

        // 加载界面资源
        Assets.background = g.newPixmap("background.png", PixmapFormat.RGB565)
        Assets.logo = g.newPixmap("logo.png", PixmapFormat.ARGB4444)
        Assets.mainMenu = g.newPixmap("mainmenu.png", PixmapFormat.ARGB4444)
        Assets.buttons = g.newPixmap("buttons.png", PixmapFormat.ARGB4444)
        Assets.help1 = g.newPixmap("help1.png", PixmapFormat.ARGB4444)
        Assets.help2 = g.newPixmap("help2.png", PixmapFormat.ARGB4444)
        Assets.help3 = g.newPixmap("help3.png", PixmapFormat.ARGB4444)
        Assets.numbers = g.newPixmap("numbers.png", PixmapFormat.ARGB4444)
        Assets.ready = g.newPixmap("ready.png", PixmapFormat.ARGB4444)
        Assets.pause = g.newPixmap("pausemenu.png", PixmapFormat.ARGB4444)
        Assets.gameOver = g.newPixmap("gameover.png", PixmapFormat.ARGB4444)
        Assets.headUp = g.newPixmap("headup.png", PixmapFormat.ARGB4444)
        Assets.headLeft = g.newPixmap("headleft.png", PixmapFormat.ARGB4444)
        Assets.headDown = g.newPixmap("headdown.png", PixmapFormat.ARGB4444)
        Assets.headRight = g.newPixmap("headright.png", PixmapFormat.ARGB4444)
        Assets.tail = g.newPixmap("tail.png", PixmapFormat.ARGB4444)
        Assets.stain1 = g.newPixmap("stain1.png", PixmapFormat.ARGB4444)
        Assets.stain2 = g.newPixmap("stain2.png", PixmapFormat.ARGB4444)
        Assets.stain3 = g.newPixmap("stain3.png", PixmapFormat.ARGB4444)

        // 加载世界地图
        Assets.wall = g.newPixmap("wall.png", PixmapFormat.ARGB4444)
        Assets.splat = g.newPixmap("splat.png", PixmapFormat.ARGB4444)
        Assets.pill = g.newPixmap("pill.png", PixmapFormat.ARGB4444)

        // 加载音效
        Assets.click = game.getAudio().newSound("click.ogg")
        Assets.eat = game.getAudio().newSound("eat.ogg")
        Assets.bitten = game.getAudio().newSound("bitten.ogg")

        // 加载游戏设置
        Settings.load(game.getFileIO())

        // 设置游戏的页面
        game.setScreen(MainMenuScreen(game))
    }

    override fun present(deltaTime: Float) {

    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun dispose() {

    }

}