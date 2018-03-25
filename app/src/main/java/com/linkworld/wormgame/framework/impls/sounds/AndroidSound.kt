package com.linkworld.wormgame.framework.impls.sounds

import android.media.SoundPool
import com.linkworld.wormgame.framework.interfaces.Sound

/**
 * @author wangyu
 * @date 18-3-24
 * @describe TODO
 */
class AndroidSound(private var soundPool: SoundPool, private var soundId: Int) : Sound {

    override fun play(volume: Float) {
        soundPool.play(soundId, volume, volume, 0, 0, 1f)
    }

    override fun dispose() {
        soundPool.unload(soundId)
    }

}