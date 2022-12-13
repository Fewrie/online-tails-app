package com.wekomodo.onlinetails.Adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.wekomodo.onlinetails.R
import com.wekomodo.onlinetails.models.Alert


class AlertsItemAdapter(
    private var list: MutableList<Alert>,
    private val context: Context
) : RecyclerView.Adapter<AlertsItemAdapter.ViewHolder>()  {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
             val name : TextView = itemView.findViewById(R.id.alert_name_txtview)
        val task : TextView = itemView.findViewById(R.id.alert_type_txtview)
        val time : TextView = itemView.findViewById(R.id.alert_time_txtview)
        val date : TextView = itemView.findViewById(R.id.alert_date_txtview)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(
            R.layout.alert_layout,
            parent,
            false
        )
        val vh = ViewHolder(inflatedView)
        return vh
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = list[position].name
        holder.task.text = list[position].task
        holder.time.text = list[position].time
        holder.date.text = list[position].date
    }

    override fun getItemCount(): Int {
        return list.size
    }
}