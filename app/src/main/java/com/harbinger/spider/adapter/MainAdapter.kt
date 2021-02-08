package com.harbinger.spider.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.harbinger.spider.R
import com.harbinger.spider.listener.OnRecycleItemClickListener

/**
 * Created by acorn on 2021/2/7.
 */
class MainAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var list: Array<String>
    var onRecycleItemClickListener: OnRecycleItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_main, parent, false)
        return NormalViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        (holder as NormalViewHolder).demoTv.text = item
        (holder as NormalViewHolder).mainRl.setOnClickListener(
            View.OnClickListener {
                onRecycleItemClickListener?.onItemClick(position)
            })
    }

    override fun getItemCount(): Int {
        return list.size
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    class NormalViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mainRl: RelativeLayout = view.findViewById<View>(R.id.main_rl) as RelativeLayout
        var demoTv: TextView = view.findViewById<View>(R.id.demo_tv) as TextView
    }
}