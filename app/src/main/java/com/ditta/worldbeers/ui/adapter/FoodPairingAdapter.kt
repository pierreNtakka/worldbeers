package com.ditta.worldbeers.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.ditta.worldbeers.databinding.ItemFoodPairBinding
import com.ditta.worldbeers.model.Beer

class FoodPairingAdapter(private val foodPairing: List<String>) :
    RecyclerView.Adapter<FoodPairingAdapter.FoodPairViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodPairViewHolder {
        val binding =
            ItemFoodPairBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodPairViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodPairViewHolder, position: Int) {
        holder.bind(foodPairing[position])
    }

    override fun getItemCount(): Int = foodPairing.size

    inner class FoodPairViewHolder(
        private val binding: ItemFoodPairBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(foodPair: String) {
            binding.foodName = foodPair
            binding.executePendingBindings()
        }
    }
}

