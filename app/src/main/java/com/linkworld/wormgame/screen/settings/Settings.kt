package com.linkworld.wormgame.screen.settings

import com.linkworld.wormgame.framework.interfaces.FileIO
import java.io.*

/**
 * @author wangyu
 * @date 18-3-24
 * @describe TODO
 */
class Settings {

    companion object {
        var soundEnabled = true
        var highscores = intArrayOf(100, 80, 50, 30, 10)

        fun load(files: FileIO) {
            var `in`: BufferedReader? = null
            try {
                `in` = BufferedReader(InputStreamReader(
                        files.readFile(".mrnom")))
                soundEnabled = java.lang.Boolean.parseBoolean(`in`.readLine())
                for (i in 0..4) {
                    highscores[i] = Integer.parseInt(`in`.readLine())
                }
            } catch (e: IOException) {
                // 翻了也没事，我们有默认的设置
            } catch (e: NumberFormatException) {
                // 没事的，反正这简直就是在浪费我们的时间
            } finally {
                try {
                    if (`in` != null)
                        `in`.close()
                } catch (e: IOException) {
                }
            }
        }

        fun save(files: FileIO) {
            var out: BufferedWriter? = null
            try {
                out = BufferedWriter(OutputStreamWriter(files.writeFile(".mrnom")))
                out.write(java.lang.Boolean.toString(soundEnabled))
                out.write("\n")
                for (i in 0..4) {
                    out.write(Integer.toString(highscores[i]))
                    out.write("\n")
                }

            } catch (e: IOException) {
            } finally {
                try {
                    if (out != null) {
                        out.close()
                    }
                } catch (e: IOException) {

                }
            }
        }

        fun addScore(score: Int) {
            for (i in 0..4) {
                if (highscores[i] < score) {
                    System.arraycopy(highscores, i, highscores, i + 1, 4 - i)
                    highscores[i] = score
                    break
                }
            }
        }
    }
}