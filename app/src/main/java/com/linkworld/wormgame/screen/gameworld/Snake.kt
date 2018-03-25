package com.linkworld.wormgame.screen.gameworld

import java.util.*

/**
 * @author wangyu
 * @date 18-3-24
 * @describe TODO
 */
class Snake {

    var parts: MutableList<SnakePart> = ArrayList<SnakePart>()
    var direction: Int = 0

    init {
        direction = UP
        parts.add(SnakePart(5, 6))
        parts.add(SnakePart(5, 7))
        parts.add(SnakePart(5, 8))
    }

    fun turnLeft() {
        direction += 1
        if (direction > RIGHT) direction = UP
    }

    fun turnRight() {
        direction -= 1
        if (direction < UP) direction = RIGHT
    }

    fun eat() {
        val end = parts[parts.size - 1]
        parts.add(SnakePart(end.x, end.y))
    }

    //Removes a snake part if possible.
    fun slimDown(numberOfPartsToRemove: Int) {
        var numberOfPartsToRemove = numberOfPartsToRemove
        if (numberOfPartsToRemove >= parts.size) {
            numberOfPartsToRemove = parts.size - 1
        }

        for (i in 0 until numberOfPartsToRemove) {
            parts.remove(parts[parts.size - 1])
        }
    }

    fun advance() {
        val head = parts[0]

        val len = parts.size - 1
        for (i in len downTo 1) {
            val before = parts[i - 1]
            val part = parts[i]
            part.x = before.x
            part.y = before.y
        }

        when (direction) {
            UP -> head.y -= 1
            LEFT -> head.x -= 1
            DOWN -> head.y += 1
            RIGHT -> head.x += 1
        }

        if (head.x < 0) head.x = 9
        if (head.x > 9) head.x = 0
        if (head.y < 0) head.y = 12
        if (head.y > 12) head.y = 0
    }

    fun checkBitten(): Boolean {
        val len = parts.size
        val head = parts[0]
        for (i in 1 until len) {
            val part = parts[i]
            if (part.x == head.x && part.y == head.y) return true
        }
        return false
    }

    companion object {
        const val UP = 0
        const val LEFT = 1
        const val DOWN = 2
        const val RIGHT = 3
    }
}
