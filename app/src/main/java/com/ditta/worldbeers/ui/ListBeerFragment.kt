package com.ditta.worldbeers.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.ditta.worldbeers.R
import com.ditta.worldbeers.databinding.FragmentListBeerBinding
import com.ditta.worldbeers.model.Beer
import com.ditta.worldbeers.network.PunkRepository
import com.ditta.worldbeers.ui.adapter.BeerAdapter
import com.ditta.worldbeers.ui.viewmodel.BeerListViewModel
import com.ditta.worldbeers.ui.viewmodel.BeerListViewModelFactory
import com.ditta.worldbeers.ui.viewmodel.PunkApiStatus

class ListBeerFragment : Fragment(), MenuProvider {

    private var _binding: FragmentListBeerBinding? = null
    private val binding get() = _binding!!

    private lateinit var beerAdapter: BeerAdapter

    private val viewModel: BeerListViewModel by viewModels {
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
        beerAdapter = BeerAdapter(::onItemClick)
        binding.recyclerViewBeer.adapter = beerAdapter

        viewModel.status.observe(viewLifecycleOwner) {
            when (it) {
                PunkApiStatus.LOADING -> {
                    binding.statusImage?.visibility = View.VISIBLE
                    binding.recyclerViewBeer?.visibility = View.GONE
                    binding.statusImage?.setImageResource(R.drawable.loading_animation)
                }
                PunkApiStatus.ERROR -> {
                    binding.statusImage?.visibility = View.VISIBLE
                    binding.recyclerViewBeer?.visibility = View.GONE
                    binding.statusImage?.setImageResource(R.drawable.ic_connection_error)
                }
                PunkApiStatus.DONE -> {
                    binding?.statusImage?.visibility = View.GONE
                    binding?.recyclerViewBeer?.visibility = View.VISIBLE
                }
            }
        }


        viewModel.beers.observe(viewLifecycleOwner) {
            (binding?.recyclerViewBeer?.adapter as BeerAdapter).updateBeers(it)
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
                        beerAdapter?.filter?.filter(query)
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        beerAdapter?.filter?.filter(newText)
                        return false
                    }
                })
                true
            }
            R.id.action_sync -> {
                viewModel.getBeers()
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