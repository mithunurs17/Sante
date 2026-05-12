package com.example.santepriceindex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.santepriceindex.databinding.FragmentPriceBoardBinding

class PriceBoardFragment : Fragment() {

    private var _binding: FragmentPriceBoardBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PriceBoardAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPriceBoardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        recyclerView = binding.recyclerViewPriceBoard
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = PriceBoardAdapter()
        recyclerView.adapter = adapter

        loadPrices()

        return root
    }

    private fun loadPrices() {
        // Mock data for demonstration
        val mockPrices = listOf(
            PriceItem("Onion", 25.0, "kg"),
            PriceItem("Tomato", 35.0, "kg"),
            PriceItem("Potato", 20.0, "kg"),
            PriceItem("Rice", 45.0, "kg"),
            PriceItem("Wheat", 30.0, "kg")
        )
        adapter.setPrices(mockPrices)

        // Uncomment below for Firebase integration
        /*
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val prices = mutableListOf<PriceItem>()
                for (data in snapshot.children) {
                    val priceItem = data.getValue(PriceItem::class.java)
                    priceItem?.let { prices.add(it) }
                }
                adapter.setPrices(prices)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
        */
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}