package com.ditta.worldbeers.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ditta.worldbeers.R
import com.ditta.worldbeers.databinding.FragmentListBeerBinding
import com.ditta.worldbeers.model.Beer
import com.ditta.worldbeers.ui.adapter.BeerLoadStateAdapter
import com.ditta.worldbeers.ui.adapter.BeerPagingAdapter
import com.ditta.worldbeers.ui.viewmodel.BeerListViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListBeerFragment : Fragment(), MenuProvider {

    private var _binding: FragmentListBeerBinding? = null
    private val binding get() = _binding!!

    private lateinit var beerAdapter: BeerPagingAdapter

    private val viewModel by viewModel<BeerListViewModel>()

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


    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) =
        menuInflater.inflate(R.menu.menu_item, menu)


    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_search -> {
                val searchView: SearchView = menuItem.actionView as SearchView

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        query?.let { viewModel.findByBeerName(query) }
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        return false
                    }
                })
                true
            }
            R.id.action_sync -> {
                viewModel.sync()
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