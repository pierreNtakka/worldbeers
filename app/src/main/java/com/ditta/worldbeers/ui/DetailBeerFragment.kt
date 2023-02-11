package com.ditta.worldbeers.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.ditta.worldbeers.R
import com.ditta.worldbeers.databinding.FragmentDetailBeerBinding
import com.ditta.worldbeers.ui.adapter.FoodPairingAdapter

class DetailBeerFragment : Fragment() {

    private val args: DetailBeerFragmentArgs by navArgs()

    private var _binding: FragmentDetailBeerBinding? = null
    private val binding get() = _binding!!

    private lateinit var firstBrewed: String
    private lateinit var foodPairing: List<String>
    private lateinit var brewersTips: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firstBrewed = args.firstBrewed
        brewersTips = args.brewersTips
        foodPairing = args.foodPairing.toList()

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