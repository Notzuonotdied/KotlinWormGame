package com.linkworld.wormgame.framework.impls.sounds

import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import com.linkworld.wormgame.framework.interfaces.Music
import java.io.IOException

/**
 * @author wangyu
 * @date 18-3-24
 * @describe 音乐播放的类
 */
class AndroidMusic(assetDescriptor: AssetFileDescriptor) : Music, MediaPlayer.OnCompletionListener {
    private val mediaPlayer: MediaPlayer = MediaPlayer()
    private var isPrepared = false

    init {
        mediaPlayer.setDataSource(assetDescriptor.fileDescriptor,
                assetDescriptor.startOffset,
                assetDescriptor.length)
        mediaPlayer.prepare()
        isPrepared = true
        mediaPlayer.setOnCompletionListener(this)
    }

    override fun setLooping(looping: Boolean) {
        mediaPlayer.isLooping = looping
    }

    override fun isPlaying(): Boolean {
        return mediaPlayer.isPlaying
    }

    override fun isStopped(): Boolean {
        return !isPrepared
    }

    override fun isLooping(): Boolean {
        return mediaPlayer.isLooping
    }

    override fun dispose() {
        if (mediaPlayer.isPlaying) mediaPlayer.stop()
        mediaPlayer.release()
    }

    override fun pause() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        }
    }

    override fun play() {
        if (mediaPlayer.isPlaying) {
            return
        }

        try {
            synchronized(this) {
                if (!isPrepared) mediaPlayer.prepare()
                mediaPlayer.start()
            }
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    override fun setVolume(volume: Float) {
        mediaPlayer.setVolume(volume, volume)
    }

    override fun stop() {
        mediaPlayer.stop()
        synchronized(this) {
            isPrepared = false
        }
    }

    override fun onCompletion(player: MediaPlayer) {
        synchronized(this) {
            isPrepared = false
        }
    }
}
