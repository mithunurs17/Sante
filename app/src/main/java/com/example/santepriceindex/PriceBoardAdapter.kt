package com.example.santepriceindex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PriceBoardAdapter : RecyclerView.Adapter<PriceBoardAdapter.PriceBoardViewHolder>() {

    private var prices: List<PriceItem> = listOf()

    fun setPrices(prices: List<PriceItem>) {
        this.prices = prices
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceBoardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_price_board, parent, false)
        return PriceBoardViewHolder(view)
    }

    override fun onBindViewHolder(holder: PriceBoardViewHolder, position: Int) {
        val priceItem = prices[position]
        holder.bind(priceItem)
    }

    override fun getItemCount(): Int = prices.size

    class PriceBoardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewItemName: TextView = itemView.findViewById(R.id.textViewItemName)
        private val textViewPrice: TextView = itemView.findViewById(R.id.textViewPrice)

        fun bind(priceItem: PriceItem) {
            textViewItemName.text = priceItem.name.uppercase()
            textViewPrice.text = "₹${priceItem.mandiPrice}/${priceItem.unit}"
        }
    }
}