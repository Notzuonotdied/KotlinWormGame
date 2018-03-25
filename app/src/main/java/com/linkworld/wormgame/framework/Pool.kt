package com.linkworld.wormgame.framework

import java.util.*

/**
 * @author wangyu
 * @date 18-3-24
 * @describe TODO
 */
class Pool<T>(private val factory: PoolObjectFactory<T>, private val maxSize: Int) {
    private val freeObjects: MutableList<T>

    init {
        this.freeObjects = ArrayList(maxSize)
    }

    fun newObject(): T {
        return if (freeObjects.size == 0) {
            factory.createObject()
        } else freeObjects.removeAt(freeObjects.size - 1)
    }

    fun free(Objects: T) {
        if (freeObjects.size < maxSize) freeObjects.add(Objects)
    }

    interface PoolObjectFactory<T> {
        fun createObject(): T
    }
}