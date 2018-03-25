package com.linkworld.wormgame

import com.linkworld.wormgame.framework.abstracts.Screen
import com.linkworld.wormgame.screen.LoadingScreen

/**
 * 游戏入口
 * */
class MainActivity : BaseAndroidGame() {

    override fun getStartScreen(): Screen {
        return LoadingScreen(this)
    }
}
