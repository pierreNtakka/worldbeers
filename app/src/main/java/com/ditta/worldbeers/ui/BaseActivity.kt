package com.ditta.worldbeers.ui

import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chuckerteam.chucker.api.Chucker
import com.ditta.worldbeers.BuildConfig
import com.squareup.seismic.ShakeDetector

open class BaseActivity : AppCompatActivity(), ShakeDetector.Listener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (BuildConfig.DEBUG) {
            val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
            val shakeDetector = ShakeDetector(this)
            shakeDetector.start(sensorManager, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    override fun hearShake() {
        startActivity(Chucker.getLaunchIntent(this))
    }

}
