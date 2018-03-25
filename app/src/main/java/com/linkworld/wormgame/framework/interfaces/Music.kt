package com.linkworld.wormgame.framework.interfaces

/**
 * @author wangyu
 * @date 18-3-24
 * @describe TODO
 */
interface Music {
    fun play()

    fun stop()

    fun pause()

    fun setLooping(looping: Boolean)

    fun setVolume(volume: Float)

    fun isPlaying(): Boolean

    fun isStopped(): Boolean

    fun isLooping(): Boolean

    fun dispose()
}