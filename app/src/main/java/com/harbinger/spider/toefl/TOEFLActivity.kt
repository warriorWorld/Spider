package com.harbinger.spider.toefl

import android.os.Bundle
import android.os.PersistableBundle
import com.harbinger.spider.R
import com.harbinger.spider.base.BaseActivity

/**
 * Created by acorn on 2021/2/8.
 */
class TOEFLActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_toefl)
    }
}