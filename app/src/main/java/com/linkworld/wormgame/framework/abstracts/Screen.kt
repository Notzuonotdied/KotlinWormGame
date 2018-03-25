package com.linkworld.wormgame.framework.abstracts

import com.linkworld.wormgame.framework.interfaces.Game

/**
 * @author wangyu
 * @date 18-3-24
 * @describe TODO
 */
abstract class Screen(protected val game: Game) {

    /**
     * 更新界面
     *
     * @param deltaTime 更新延迟时间
     * */
    abstract fun update(deltaTime: Float)

    /**
     * 贴图
     *
     * @param deltaTime 延迟绘制的时间
     * */
    abstract fun present(deltaTime: Float)

    /**
     * 游戏暂停
     * */
    abstract fun pause()

    /**
     * 游戏重新开始
     * */
    abstract fun resume()

    abstract fun dispose()
}