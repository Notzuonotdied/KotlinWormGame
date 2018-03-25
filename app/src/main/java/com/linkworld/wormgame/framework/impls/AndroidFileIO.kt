package com.linkworld.wormgame.framework.impls

import android.content.Context
import android.content.SharedPreferences
import android.content.res.AssetManager
import android.os.Environment
import android.preference.PreferenceManager
import com.linkworld.wormgame.framework.interfaces.FileIO
import java.io.*

/**
 * @author wangyu
 * @date 18-3-24
 * @describe TODO
 */
class AndroidFileIO(private val context: Context) : FileIO {
    private val assets: AssetManager = context.assets
    private val externalStoragePath: String = Environment.getExternalStorageDirectory().absolutePath + File.separator

    override fun readAsset(fileName: String): InputStream {
        return assets.open(fileName)
    }

    override fun readFile(fileName: String): InputStream {
        return FileInputStream(externalStoragePath + fileName)
    }

    override fun writeFile(fileName: String): OutputStream {
        return FileOutputStream(externalStoragePath + fileName)
    }

    override fun getPreferences(): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }
}