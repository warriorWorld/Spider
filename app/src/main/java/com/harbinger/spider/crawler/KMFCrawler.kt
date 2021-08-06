package com.harbinger.spider.crawler

import com.harbinger.spider.utils.FileUtil
import org.jsoup.Jsoup
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.lang.StringBuilder

/**
 * Created by acorn on 2021/8/5.
 */
class KMFCrawler {
    fun getReadList(page: Int, resultCallback: (urls: MutableList<String>) -> Unit) {
        Thread {
            val doc =
                Jsoup.connect("https://toefl.kmf.com/read/ets/new-order/$page/0").timeout(30000)
                    .get()
            if (null != doc) {
                val urls = doc.getElementsByClass("online-exam-link button-style js-online-link")
                    .map { "https://toefl.kmf.com" + it.attr("href") }
                print("urls:$urls")
                resultCallback.invoke(urls.toMutableList())
            }
        }.start()
    }

    fun getReadContent(url: String, resultCallback: (success: Boolean) -> Unit) {
        Thread {
            val doc = Jsoup.connect(url).timeout(30000).get()
            if (null != doc) {
                val title = doc.getElementsByClass("read-main-title").first().text()
                val content = doc.getElementsByClass("section-container").first().html().replace(
                    "<br>",
                    "\n"
                )

                try {
                    val fw: FileWriter = FileWriter(
                        FileUtil.getTOEFLReadDirectory().path + File.separator + title + ".txt",
                        true
                    )
                    fw.write(title + "\n\n" + content)
                    fw.close()

                    resultCallback.invoke(true)
                } catch (e: IOException) {
                    e.printStackTrace()
                    resultCallback.invoke(false)
                }
            }
        }.start()
    }

    fun getListenList(page: Int, resultCallback: (urls: MutableList<String>) -> Unit) {
        Thread {
            val doc =
                Jsoup.connect("https://toefl.kmf.com/listen/ets/new-order/$page/0").timeout(30000)
                    .get()
            if (null != doc) {
                val urls = doc.getElementsByClass("listen-exam-link button-style js-listen-link")
                    .map { "https://toefl.kmf.com" + it.attr("href") }
                print("urls:$urls")
                resultCallback.invoke(urls.toMutableList())
            }
        }.start()
    }

    fun getListenContent(name: String, url: String, resultCallback: (success: Boolean) -> Unit) {
        Thread {
            val doc = Jsoup.connect(url).timeout(30000).get()
            if (null != doc) {
                val content = doc.getElementsByClass("sentence-content")
                    .map { it.html().replace("<br>", "\n") }
                val contentSb = StringBuilder()
                content.forEach {
                    contentSb.append(it)
                    contentSb.append("\n")
                }
                try {
                    val fw: FileWriter = FileWriter(
                        FileUtil.getTOEFLListenDirectory().path + File.separator + name + ".txt",
                        true
                    )
                    fw.write(contentSb.toString())
                    fw.close()

                    resultCallback.invoke(true)
                } catch (e: IOException) {
                    e.printStackTrace()
                    resultCallback.invoke(false)
                }
            }
        }.start()
    }
}