package com.ditta.worldbeers.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ditta.worldbeers.R
import com.ditta.worldbeers.databinding.FragmentListBeerBinding
import com.ditta.worldbeers.model.Beer
import com.ditta.worldbeers.network.PunkRepository
import com.ditta.worldbeers.ui.adapter.BeerLoadStateAdapter
import com.ditta.worldbeers.ui.adapter.BeerPagingAdapter
import com.ditta.worldbeers.ui.viewmodel.BeerListViewModelFactory
import com.ditta.worldbeers.ui.viewmodel.BeerListViewModelTest
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ListBeerFragment : Fragment(), MenuProvider {

    private var _binding: FragmentListBeerBinding? = null
    private val binding get() = _binding!!

    private lateinit var beerAdapter: BeerPagingAdapter

    private val viewModel: BeerListViewModelTest by viewModels {
        BeerListViewModelFactory(punkRepository = PunkRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBeerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        beerAdapter = BeerPagingAdapter(::onItemClick)

        binding.recyclerViewBeer.apply {
            adapter = beerAdapter.withLoadStateHeaderAndFooter(
                header = BeerLoadStateAdapter { beerAdapter.retry() },
                footer = BeerLoadStateAdapter { beerAdapter.retry() }
            )
        }

        lifecycleScope.launch {
            viewModel.beer.collectLatest { beers ->
                beerAdapter.submitData(beers)
            }
        }
    }


    private fun onItemClick(beer: Beer) {
        val action =
            ListBeerFragmentDirections.actionListBeerFragmentToDetailBeerFragment(
                beer.name,
                beer.firstBrewed,
                beer.foodPairing.toTypedArray(),
                beer.brewersTips
            )

        findNavController().navigate(action)
    }


    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_item, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_search -> {
                val searchView: SearchView = menuItem.actionView as SearchView

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        //beerAdapter?.filter?.filter(query)
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        //beerAdapter?.filter?.filter(newText)
                        return false
                    }
                })
                true
            }
            R.id.action_sync -> {
                beerAdapter.retry()
                true
            }

            else -> false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}