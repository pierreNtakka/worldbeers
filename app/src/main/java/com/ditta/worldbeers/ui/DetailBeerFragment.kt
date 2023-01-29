package com.ditta.worldbeers.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.ditta.worldbeers.R
import com.ditta.worldbeers.databinding.FragmentDetailBeerBinding
import com.ditta.worldbeers.ui.adapter.FoodPairingAdapter

class DetailBeerFragment : Fragment() {

    private var _binding: FragmentDetailBeerBinding? = null
    private val binding get() = _binding!!

    private lateinit var firstBrewed: String
    private lateinit var foodPairing: List<String>
    private lateinit var brewersTips: String

    companion object {
        const val FIRST_BREWED = "firstBrewed"
        const val FOOD_PAIRING = "foodPairing"
        const val BREWERS_TIPS = "brewersTips"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            firstBrewed = it.getString(FIRST_BREWED).toString()
            brewersTips = it.getString(BREWERS_TIPS).toString()
            foodPairing = it.getStringArray(FOOD_PAIRING)?.toList() ?: emptyList()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBeerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            textviewFirstBrewed.text = HtmlCompat.fromHtml(
                getString(R.string.first_brewed_label, firstBrewed),
                HtmlCompat.FROM_HTML_MODE_COMPACT
            )

            recyclerViewFood.adapter = FoodPairingAdapter(foodPairing = foodPairing.toList())

            textviewBrewedTipsValue.text = brewersTips
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}