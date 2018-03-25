package com.linkworld.wormgame.framework.interfaces

/**
 * @author wangyu
 * @date 18-3-24
 * @describe TODO
 */
interface Audio {
    fun newMusic(filename: String): Music

    fun newSound(filename: String): Sound
}