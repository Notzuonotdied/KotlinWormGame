package com.linkworld.wormgame.screen.gameworld

import java.util.*

/**
 * @author wangyu
 * @date 18-3-24
 * @describe TODO
 */
class World {

    var snake: Snake
    var stain: Stain? = null

    // New features!
    var splat: Position? = null
    var pill: Position? = null
    lateinit var walls: Array<Position?>

    var gameOver = false
    var score = 0

    internal var fields = Array(WORLD_WIDTH) { BooleanArray(WORLD_HEIGHT) }
    internal var random = Random()
    internal var tickTime = 0f
    internal var stainCounter = 0

    //Gets a new Position in an empty grid spot.
    private val newPosition: Position
        get() {
            checkFieldForFreeSpots()

            var newX = random.nextInt(WORLD_WIDTH)
            var newY = random.nextInt(WORLD_HEIGHT)
            while (true) {
                if (fields[newX][newY] == false)
                    break
                newX += 1
                if (newX >= WORLD_WIDTH) {
                    newX = 0
                    newY += 1
                    if (newY >= WORLD_HEIGHT) {
                        newY = 0
                    }
                }
            }
            return Position(newX, newY)
        }

    init {
        snake = Snake()
        createWalls()
        placeStain()
    }

    private fun createWalls() {
        walls = arrayOfNulls<Position>(12)
        walls[0] = Position(1, 1)
        walls[1] = Position(2, 1)
        walls[2] = Position(1, 2)
        walls[3] = Position(WORLD_WIDTH - 3, 1)
        walls[4] = Position(WORLD_WIDTH - 2, 1)
        walls[5] = Position(WORLD_WIDTH - 2, 2)
        walls[6] = Position(1, WORLD_HEIGHT - 3)
        walls[7] = Position(2, WORLD_HEIGHT - 2)
        walls[8] = Position(1, WORLD_HEIGHT - 2)
        walls[9] = Position(WORLD_WIDTH - 2, WORLD_HEIGHT - 2)
        walls[10] = Position(WORLD_WIDTH - 2, WORLD_HEIGHT - 3)
        walls[11] = Position(WORLD_WIDTH - 3, WORLD_HEIGHT - 2)
    }

    private fun placeSplat() {
        splat = newPosition
    }

    private fun placePill() {
        pill = newPosition
    }

    private fun placeStain() {
        val p = newPosition
        stain = Stain(p.x, p.y, random.nextInt(3))
    }

    //Updates the value of fields for free/occupied spots.
    private fun checkFieldForFreeSpots() {
        for (x in 0 until WORLD_WIDTH) {
            for (y in 0 until WORLD_HEIGHT) fields[x][y] = false
        }

        val len = snake.parts.size
        for (i in 0 until len) {
            val part = snake.parts.get(i)
            fields[part.x][part.y] = true
        }

        if (splat != null) fields[splat!!.x][splat!!.y] = true
        if (stain != null) fields[stain!!.x][stain!!.y] = true

        for (i in walls.indices) fields[walls[i]!!.x][walls[i]!!.y] = true
    }

    private fun placeNextSetOfItems() {
        placeStain()
        if (stainCounter % 5 == 0) placeSplat()
        if (stainCounter % 10 == 0) placePill()
    }

    fun update(deltaTime: Float) {
        if (gameOver) return

        tickTime += deltaTime

        while (tickTime > tick) {
            tickTime -= tick
            snake.advance()
            if (snake.checkBitten()) {
                gameOver = true
                return
            }

            val head = snake.parts.get(0)
            if (head.x == stain!!.x && head.y == stain!!.y) {
                score += SCORE_INCREMENT
                snake.eat()
                if (snake.parts.size == WORLD_WIDTH * WORLD_HEIGHT) {
                    gameOver = true
                    return
                } else {
                    stainCounter++
                    placeNextSetOfItems()
                }

                if (score % 100 == 0 && tick - TICK_DECREMENT > 0) {
                    tick -= TICK_DECREMENT
                }
            }

            if (pill != null && head.x == pill!!.x && head.y == pill!!.y) {
                snake.slimDown(5)
                pill = null
            }

            if (splat != null && head.x == splat!!.x && head.y == splat!!.y) {
                snake.eat()
                splat = null
                score -= SCORE_INCREMENT * 3
            }

            for (i in walls.indices) {
                if (head.x == walls[i]!!.x && head.y == walls[i]!!.y) {
                    gameOver = true
                    return
                }
            }
        }
    }

    companion object {
        internal const val WORLD_WIDTH = 10
        internal const val WORLD_HEIGHT = 13
        internal const val SCORE_INCREMENT = 10
        internal const val TICK_INITIAL = 0.5f
        internal const val TICK_DECREMENT = 0.05f
        internal var tick = TICK_INITIAL
    }
}
