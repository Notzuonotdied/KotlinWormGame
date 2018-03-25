package com.linkworld.wormgame.screen

import android.graphics.Color
import com.linkworld.wormgame.constants.Assets
import com.linkworld.wormgame.framework.abstracts.Screen
import com.linkworld.wormgame.framework.interfaces.Game
import com.linkworld.wormgame.framework.interfaces.Graphics
import com.linkworld.wormgame.framework.interfaces.Input.TouchEvent
import com.linkworld.wormgame.framework.interfaces.Pixmap
import com.linkworld.wormgame.screen.gameworld.Snake
import com.linkworld.wormgame.screen.gameworld.Stain
import com.linkworld.wormgame.screen.gameworld.World
import com.linkworld.wormgame.screen.settings.Settings

/**
 * @author wangyu
 * @date 18-3-24
 * @describe 游戏菜单界面
 */
class GameScreen(game: Game) : Screen(game) {
    private var score = "0"
    private var state = GameState.Ready
    private val world: World = World()
    private var oldScore = 0

    override fun update(deltaTime: Float) {
        val touchEvents = game.getInput().getTouchEvents()

        // 判断游戏的状态
        when (state) {
            GameState.Ready -> updateReady(touchEvents)
            GameState.Running -> updateRunning(touchEvents, deltaTime)
            GameState.Paused -> updatePaused(touchEvents)
            GameState.GameOver -> updateGameOver(touchEvents)
        }
    }

    private fun updateReady(touchEvents: List<TouchEvent>) {
        if (touchEvents.isNotEmpty()) state = GameState.Running
    }

    private fun updateRunning(touchEvents: List<TouchEvent>, deltaTime: Float) {
        val len = touchEvents.size
        for (i in 0 until len) {
            val event = touchEvents[i]
            // 更新界面
            when (event.type) {
                TouchEvent.TOUCH_UP -> {
                    if (event.x < 64 && event.y < 64) {
                        if (Settings.soundEnabled) {
                            Assets.click.play(1.toFloat())
                        }
                        state = GameState.Paused
                        return
                    }
                }
                TouchEvent.TOUCH_DOWN -> {
                    if (event.x < 64 && event.y > 416) {
                        world.snake.turnLeft()
                    }
                    if (event.x > 256 && event.y > 416) {
                        world.snake.turnRight()
                    }
                }
            }
        }

        world.update(deltaTime)
        if (world.gameOver) {
            if (Settings.soundEnabled) Assets.bitten.play(1.toFloat())
            state = GameState.GameOver
        }
        if (oldScore != world.score) {
            oldScore = world.score
            score = "" + oldScore
            if (Settings.soundEnabled) Assets.eat.play(1.toFloat())
        }
    }

    private fun updatePaused(touchEvents: List<TouchEvent>) {
        val len = touchEvents.size
        for (i in 0 until len) {
            val event = touchEvents[i]
            if (!(event.type == TouchEvent.TOUCH_UP && event.x in 81..240)) continue
            when (event.y) {
                in 101..148 -> {
                    if (Settings.soundEnabled) Assets.click.play(1.toFloat())
                    state = GameState.Running
                    return
                }
                in 149..195 -> {
                    if (Settings.soundEnabled) Assets.click.play(1.toFloat())
                    game.setScreen(MainMenuScreen(game))
                    return
                }
            }
        }
    }

    /**
     * 更新游戏结束界面
     * */
    private fun updateGameOver(touchEvents: List<TouchEvent>) {
        val len = touchEvents.size
        for (i in 0 until len) {
            val event = touchEvents[i]
            if (event.type != TouchEvent.TOUCH_UP) continue
            if (!(event.x !in 128..192 || event.y < 200 || event.y > 264)) {
                if (Settings.soundEnabled) Assets.click.play(1.toFloat())
                game.setScreen(MainMenuScreen(game))
                return
            }
        }
    }

    override fun present(deltaTime: Float) {
        val g = game.getGraphics()

        g.drawPixmap(Assets.background, 0, 0)
        drawWorld(world)

        when (state) {
            GameState.Ready -> drawReadyUI()
            GameState.Running -> drawRunningUI()
            GameState.Paused -> drawPausedUI()
            GameState.GameOver -> drawGameOverUI()
        }

        // 绘制当前游戏的分数
        drawText(g, score, g.getWidth() / 2 - score.length * 20 / 2, g.getHeight() - 42)
    }

    /**
     * 绘制整一个地图
     *
     * @param world 地图
     * */
    private fun drawWorld(world: World) {
        val g = game.getGraphics()
        val snake = world.snake
        val head = snake.parts.get(0)

        val stain = world.stain ?: return

        val stainPixmap: Pixmap

        when (stain.type) {
            Stain.TYPE_1 -> stainPixmap = Assets.stain1
            Stain.TYPE_2 -> stainPixmap = Assets.stain2
            Stain.TYPE_3 -> stainPixmap = Assets.stain3
            else -> stainPixmap = Assets.stain1
        }

        var x = stain.x * 32
        var y = stain.y * 32
        g.drawPixmap(stainPixmap, x, y)

        val len = snake.parts.size
        for (i in 1 until len) {
            val part = snake.parts.get(i)
            x = part.x * 32
            y = part.y * 32
            g.drawPixmap(Assets.tail, x, y)
        }

        val headPixmap: Pixmap
        headPixmap = when (snake.direction) {
            Snake.UP -> Assets.headUp
            Snake.LEFT -> Assets.headLeft
            Snake.DOWN -> Assets.headDown
            Snake.RIGHT -> Assets.headRight
            else -> Assets.headUp // 默认向上
        }
        x = head.x * 32 + 16
        y = head.y * 32 + 16
        g.drawPixmap(headPixmap, x - headPixmap.getWidth() / 2, y - headPixmap.getHeight() / 2)

        if (world.pill != null) {
            x = world.pill!!.x * 32
            y = world.pill!!.y * 32
            g.drawPixmap(Assets.pill, x, y)
        }
        if (world.splat != null) {
            x = world.splat!!.x * 32
            y = world.splat!!.y * 32
            g.drawPixmap(Assets.splat, x, y)
        }
        for (i in 0 until world.walls.size) {
            x = world.walls[i]!!.x * 32
            y = world.walls[i]!!.y * 32
            g.drawPixmap(Assets.wall, x, y)
        }
    }

    /**
     * 绘制准备的UI
     * */
    private fun drawReadyUI() {
        val g = game.getGraphics()

        g.drawPixmap(Assets.ready, 47, 100)
        g.drawLine(0, 416, 480, 416, Color.BLACK)
    }

    /**
     * 绘制正在运行的UI
     * */
    private fun drawRunningUI() {
        val g = game.getGraphics()

        g.drawPixmap(Assets.buttons, 0, 0, 64, 128, 64, 64)
        g.drawLine(0, 416, 480, 416, Color.BLACK)
        g.drawPixmap(Assets.buttons, 0, 416, 64, 64, 64, 64)
        g.drawPixmap(Assets.buttons, 256, 416, 0, 64, 64, 64)
    }

    /**
     * 绘制暂停的UI
     * */
    private fun drawPausedUI() {
        val g = game.getGraphics()

        g.drawPixmap(Assets.pause, 80, 100)
        g.drawLine(0, 416, 480, 416, Color.BLACK)
    }

    /**
     * 绘制游戏就结束的UI
     * */
    private fun drawGameOverUI() {
        val g = game.getGraphics()

        g.drawPixmap(Assets.gameOver, 62, 100)
        g.drawPixmap(Assets.buttons, 128, 200, 0, 128, 64, 64)
        g.drawLine(0, 416, 480, 416, Color.BLACK)
    }

    /**
     * 绘制文字
     *
     * @param g     画笔
     * @param line  当前行的内容
     * @param x     绘制的起始点
     * @param y     绘制的起始点
     * */
    private fun drawText(g: Graphics, line: String, x: Int, y: Int) {
        var x = x
        val len = line.length
        for (i in 0 until len) {
            val character = line[i]

            if (character == ' ') {
                x += 20
                continue
            }

            var srcX: Int
            var srcWidth: Int

            when (character) {
                '.' -> {
                    srcX = 200
                    srcWidth = 10
                }
                else -> {
                    srcX = (character - '0') * 20
                    srcWidth = 20
                }
            }

            g.drawPixmap(Assets.numbers, x, y, srcX, 0, srcWidth, 32)
            x += srcWidth
        }
    }

    override fun pause() {
        // 暂停游戏
        if (state == GameState.Running) {
            state = GameState.Paused
        }

        // 是否游戏结束
        if (world.gameOver) {
            Settings.addScore(world.score)
            Settings.save(game.getFileIO())
        }
    }

    override fun resume() {

    }

    override fun dispose() {

    }

    internal enum class GameState {
        Ready,
        Running,
        Paused,
        GameOver
    }
}