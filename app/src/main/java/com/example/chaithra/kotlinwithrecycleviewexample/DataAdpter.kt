package com.example.chaithra.kotlinwithrecycleviewexample

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.data_list.view.*

class DataAdpter(private val items : ArrayList<String>, val context: Context) : RecyclerView.Adapter<DataAdpter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.data_list, p0, false))
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

        p0?.items?.text = items.get(p1)
    }


    override fun getItemCount(): Int {
        return items.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val items = view.item
    }
}