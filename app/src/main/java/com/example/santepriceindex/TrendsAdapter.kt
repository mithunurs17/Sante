package com.example.santepriceindex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TrendsAdapter : RecyclerView.Adapter<TrendsAdapter.TrendsViewHolder>() {

    private var trends: List<TrendItem> = listOf()

    fun setTrends(trends: List<TrendItem>) {
        this.trends = trends
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_trend, parent, false)
        return TrendsViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrendsViewHolder, position: Int) {
        val trendItem = trends[position]
        holder.bind(trendItem)
    }

    override fun getItemCount(): Int = trends.size

    class TrendsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewItemName: TextView = itemView.findViewById(R.id.textViewItemName)
        private val textViewTrend: TextView = itemView.findViewById(R.id.textViewTrend)

        fun bind(trendItem: TrendItem) {
            textViewItemName.text = trendItem.name
            textViewTrend.text = trendItem.trend
        }
    }
}