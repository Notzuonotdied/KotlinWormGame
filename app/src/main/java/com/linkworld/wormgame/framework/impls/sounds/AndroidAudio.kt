package com.linkworld.wormgame.framework.impls.sounds

import android.app.Activity
import android.content.res.AssetManager
import android.media.AudioManager
import android.media.SoundPool
import com.linkworld.wormgame.framework.interfaces.Audio
import com.linkworld.wormgame.framework.interfaces.Music
import com.linkworld.wormgame.framework.interfaces.Sound

/**
 * @author wangyu
 * @date 18-3-24
 * @describe TODO
 */
class AndroidAudio(activity: Activity) : Audio {
    private val assets: AssetManager = activity.assets
    private val soundPool: SoundPool = SoundPool(20, AudioManager.STREAM_MUSIC, 0)

    init {
        // 音乐回放即媒体音量
        activity.volumeControlStream = AudioManager.STREAM_MUSIC
    }

    override fun newMusic(filename: String): Music {
        return AndroidMusic(assets.openFd(filename))
    }

    override fun newSound(filename: String): Sound {
        return AndroidSound(soundPool, soundPool.load(assets.openFd(filename), 0))
    }

}