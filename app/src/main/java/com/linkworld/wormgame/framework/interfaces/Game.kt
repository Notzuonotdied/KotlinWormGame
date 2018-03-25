package com.linkworld.wormgame.framework.interfaces

import com.linkworld.wormgame.framework.abstracts.Screen

/**
 * @author wangyu
 * @date 18-3-24
 * @describe TODO
 */
interface Game {
    fun getInput(): Input

    fun getFileIO(): FileIO

    fun getGraphics(): Graphics

    fun getAudio(): Audio

    fun setScreen(screen: Screen)

    fun getCurrentScreen(): Screen

    fun getStartScreen(): Screen
}