package com.example.santepriceindex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.santepriceindex.databinding.FragmentTrendsBinding
import com.google.firebase.database.*
import kotlin.random.Random

class TrendsFragment : Fragment() {

    private var _binding: FragmentTrendsBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TrendsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrendsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        database = FirebaseDatabase.getInstance().reference.child("prices")

        recyclerView = binding.recyclerViewTrends
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = TrendsAdapter()
        recyclerView.adapter = adapter

        loadTrends()

        return root
    }

    private fun loadTrends() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val trends = mutableListOf<TrendItem>()
                for (data in snapshot.children) {
                    val priceItem = data.getValue(PriceItem::class.java)
                    priceItem?.let {
                        val trend = when (Random.nextInt(3)) {
                            0 -> "Rising"
                            1 -> "Falling"
                            else -> "Stable"
                        }
                        trends.add(TrendItem(it.name, trend))
                    }
                }
                adapter.setTrends(trends)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}