package com.example.santepriceindex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.santepriceindex.databinding.FragmentPriceWatchBinding
import com.google.firebase.database.*

class PriceWatchFragment : Fragment() {

    private var _binding: FragmentPriceWatchBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PriceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPriceWatchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        database = FirebaseDatabase.getInstance().reference.child("prices")

        recyclerView = binding.recyclerViewPrices
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = PriceAdapter()
        recyclerView.adapter = adapter

        loadPrices()

        return root
    }

    private fun loadPrices() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val prices = mutableListOf<PriceItem>()
                for (data in snapshot.children) {
                    val priceItem = data.getValue(PriceItem::class.java)
                    priceItem?.let { prices.add(it) }
                }
                adapter.setPrices(prices)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}