package com.harbinger.spider.base

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by acorn on 2021/2/8.
 */
open class BaseActivity : AppCompatActivity() {
    val mH = Handler(Looper.getMainLooper())

    fun showToast(text: String, duration: Int) {
        runOnUiThread {
            Toast.makeText(this, text, duration).show()
        }
    }
}