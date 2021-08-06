package com.harbinger.spider

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.harbinger.spider.adapter.MainAdapter
import com.harbinger.spider.base.BaseActivity
import com.harbinger.spider.listener.OnRecycleItemClickListener
import com.harbinger.spider.toefl.TOEFLActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    private val list = arrayOf("TOEFL Spider")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initList()
    }

    private fun initList() {
        val adapter = MainAdapter()
        adapter.list = list
        adapter.onRecycleItemClickListener = object : OnRecycleItemClickListener {
            override fun onItemClick(position: Int) {
                var intent: Intent? = null
                when (position) {
                    0 -> {
                        intent = Intent(this@MainActivity, TOEFLActivity::class.java)
                    }
                }
                intent?.let { startActivity(it) }
            }
        }
        main_rcv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        main_rcv.isFocusable = false
        main_rcv.setHasFixedSize(true)
        val controller =
            LayoutAnimationController(AnimationUtils.loadAnimation(this, R.anim.recycler_load))
        main_rcv.layoutAnimation = controller
        main_rcv.adapter = adapter
    }
}