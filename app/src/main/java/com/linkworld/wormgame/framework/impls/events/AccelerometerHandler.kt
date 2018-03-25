package com.linkworld.wormgame.framework.impls.events

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

/**
 * @author wangyu
 * @date 18-3-24
 * @describe 加速度传感器的数据获取
 */
class AccelerometerHandler(context: Context) : SensorEventListener {
    // 设置set方法为私有方法
    var accelX: Float = 0.toFloat()
        private set
    var accelY: Float = 0.toFloat()
        private set
    var accelZ: Float = 0.toFloat()
        private set

    // 注册并获取传感器的数据
    init {
        val manager = (context.getSystemService(Context.SENSOR_SERVICE) as SensorManager)
        if (manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size != 0) {
            val accelerometer = manager.getSensorList(Sensor.TYPE_ACCELEROMETER)[0]
            manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // nothing to do here
    }

    override fun onSensorChanged(event: SensorEvent) {
        accelX = event.values[0]
        accelY = event.values[1]
        accelZ = event.values[2]
    }
}