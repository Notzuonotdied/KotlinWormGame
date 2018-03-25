package com.linkworld.wormgame.framework.interfaces

import android.content.SharedPreferences
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

/**
 * @author wangyu
 * @date 18-3-24
 * @describe 文件读取接口
 */
interface FileIO {
    // 抛出IO异常
    @Throws(IOException::class)
    fun readAsset(fileName: String): InputStream

    // 抛出IO异常
    @Throws(IOException::class)
    fun readFile(fileName: String): InputStream

    // 抛出IO异常
    @Throws(IOException::class)
    fun writeFile(fileName: String): OutputStream

    fun getPreferences(): SharedPreferences
}