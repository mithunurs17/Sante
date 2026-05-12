package com.example.santepriceindex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PriceAdapter : RecyclerView.Adapter<PriceAdapter.PriceViewHolder>() {

    private var prices: List<PriceItem> = listOf()

    fun setPrices(prices: List<PriceItem>) {
        this.prices = prices
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_price, parent, false)
        return PriceViewHolder(view)
    }

    override fun onBindViewHolder(holder: PriceViewHolder, position: Int) {
        val priceItem = prices[position]
        holder.bind(priceItem)
    }

    override fun getItemCount(): Int = prices.size

    class PriceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewItemName: TextView = itemView.findViewById(R.id.textViewItemName)
        private val textViewMandiPrice: TextView = itemView.findViewById(R.id.textViewMandiPrice)

        fun bind(priceItem: PriceItem) {
            textViewItemName.text = priceItem.name
            textViewMandiPrice.text = "₹${priceItem.mandiPrice}/${priceItem.unit}"
        }
    }
}