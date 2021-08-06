package com.harbinger.spider.toefl

import android.Manifest
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.harbinger.spider.R
import com.harbinger.spider.base.BaseActivity
import com.harbinger.spider.crawler.KMFCrawler
import kotlinx.android.synthetic.main.activity_toefl.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions


/**
 * Created by acorn on 2021/2/8.
 */
class TOEFLActivity : BaseActivity(), EasyPermissions.PermissionCallbacks {
    private val kmfCrawler = KMFCrawler()
    private var urls: MutableList<String>? = null
    private var currentPage = 1
    private var subPosition = 0//仅用于文件名称

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toefl)
    }

    private fun getArticleListByPage() {
        kmfCrawler.getReadList(currentPage) {
            urls = it
            downloadArticle()
        }
    }

    private fun downloadArticle() {
        runOnUiThread {
            if (urls.isNullOrEmpty()) {
                currentPage++
                if (currentPage > 11) {
                    //全部完成
                    start_btn.setBackgroundColor(resources.getColor(R.color.complete_success))
                    return@runOnUiThread
                }
                getArticleListByPage()
                return@runOnUiThread
            }
            val url = urls!![0]
            urls?.removeAt(0)
            start_btn.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            kmfCrawler.getReadContent(url) {
                if (it) {
                    start_btn.setBackgroundColor(resources.getColor(R.color.little_success))
                    downloadArticle()
                } else {
                    start_btn.setBackgroundColor(resources.getColor(R.color.error))
                }
            }
        }
    }

    private fun getListenListByPage() {
        kmfCrawler.getListenList(currentPage) {
            urls = it
            downloadListen()
        }
    }

    private fun downloadListen() {
        runOnUiThread {
            if (urls.isNullOrEmpty()) {
                currentPage++
                if (currentPage > 11) {
                    //全部完成
                    listen_btn.setBackgroundColor(resources.getColor(R.color.complete_success))
                    return@runOnUiThread
                }
                subPosition = 0
                getListenListByPage()
                return@runOnUiThread
            }
            subPosition++
            val url = urls!![0]
            urls?.removeAt(0)
            listen_btn.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            kmfCrawler.getListenContent("$currentPage-$subPosition", url) {
                if (it) {
                    listen_btn.setBackgroundColor(resources.getColor(R.color.little_success))
                    downloadListen()
                } else {
                    listen_btn.setBackgroundColor(resources.getColor(R.color.error))
                }
            }
        }
    }

    @AfterPermissionGranted(0)
    fun onClick(v: View) {
        val perms =
            arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (EasyPermissions.hasPermissions(this, *perms)) {
            // Already have permission, do the thing
            // ...
            when (v.tag) {
                "start" -> {
                    currentPage = 7
                    getArticleListByPage()
                }
                "listen" -> {
                    currentPage = 1
                    getListenListByPage()
                }
            }
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(
                this@TOEFLActivity, "需要文件读写权限",
                0, *perms
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show()
    }
}