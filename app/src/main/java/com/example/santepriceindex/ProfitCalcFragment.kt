package com.example.santepriceindex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.santepriceindex.databinding.FragmentProfitCalcBinding
import com.google.firebase.database.*

class ProfitCalcFragment : Fragment() {

    private var _binding: FragmentProfitCalcBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: DatabaseReference
    private var prices: List<PriceItem> = listOf()
    private var selectedItem: PriceItem? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfitCalcBinding.inflate(inflater, container, false)
        val root: View = binding.root

        database = FirebaseDatabase.getInstance().reference.child("prices")

        loadPrices()

        binding.buttonCalculate.setOnClickListener {
            calculateRecommendedPrice()
        }

        return root
    }

    private fun loadPrices() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                prices = mutableListOf()
                val itemNames = mutableListOf<String>()
                for (data in snapshot.children) {
                    val priceItem = data.getValue(PriceItem::class.java)
                    priceItem?.let {
                        (prices as MutableList).add(it)
                        itemNames.add(it.name)
                    }
                }

                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, itemNames)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerItems.adapter = adapter

                binding.spinnerItems.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        selectedItem = prices[position]
                        binding.textViewMandiPrice.text = "${selectedItem?.mandiPrice ?: 0.0}"
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun calculateRecommendedPrice() {
        val transportCost = binding.editTextTransportCost.text.toString().toDoubleOrNull() ?: 0.0
        val mandiPrice = selectedItem?.mandiPrice ?: 0.0

        // Cost-plus pricing: Mandi price + transport + 20% profit margin
        val profitMargin = 0.20
        val recommendedPrice = mandiPrice + transportCost + (mandiPrice * profitMargin)

        binding.textViewRecommendedPrice.text = "₹${String.format("%.2f", recommendedPrice)}"
        binding.textViewRecommendedPrice.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}