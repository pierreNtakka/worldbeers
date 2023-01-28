package com.ditta.worldbeers.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ditta.worldbeers.R
import com.ditta.worldbeers.databinding.ItemBeerListBinding
import com.ditta.worldbeers.model.Beer

class BeerAdapter(
    private val onItemClicked: (beer: Beer) -> Unit
) : RecyclerView.Adapter<BeerAdapter.BeerViewHolder>(), Filterable {

    private lateinit var beers: List<Beer>
    private var beersFiltered = mutableListOf<Beer>()

    fun updateBeers(beers: List<Beer>) {
        this.beers = beers
        this.beersFiltered = beers.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        val binding =
            ItemBeerListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BeerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        holder.bind(beersFiltered[position], onItemClicked)
    }

    override fun getItemCount(): Int = beersFiltered.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()

                beersFiltered = if (charSearch.isEmpty()) {
                    beers.toMutableList()
                } else {
                    val filteredList = ArrayList<Beer>()
                    beers
                        .filter {
                            it.name.lowercase()
                                .contains(charSearch.lowercase()) or it.description.lowercase()
                                .contains(charSearch.lowercase())
                        }
                        .forEach { filteredList.add(it) }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = beersFiltered
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                beersFiltered = if (results?.values == null)
                    beers as ArrayList<Beer>
                else
                    results.values as ArrayList<Beer>

                notifyDataSetChanged()
            }
        }
    }


    inner class BeerViewHolder(
        private val binding: ItemBeerListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(beer: Beer, onItemClicked: (beer: Beer) -> Unit) {
            Glide.with(itemView.context).load(beer.imageUrl).skipMemoryCache(true)
                .placeholder(R.drawable.loading_animation).error(R.drawable.ic_connection_error)
                .into(binding.imageViewBeer)

            binding.apply {
                cardviewBeer.setOnClickListener {
                    onItemClicked(beer)
                }
                textviewBeerName.text = beer.name
                textviewBeerDesc.text = beer.description
                textviewBeerAlcoholContent.text = HtmlCompat.fromHtml(
                    itemView.context.getString(R.string.alcohol_content_label, beer.abv.toString()),
                    HtmlCompat.FROM_HTML_MODE_COMPACT
                )

                val ibu = if (beer.ibu == null) "N/A" else beer.ibu.toString()

                textviewBeerIbu.text = HtmlCompat.fromHtml(
                    itemView.context.getString(R.string.ibu_label, ibu),
                    HtmlCompat.FROM_HTML_MODE_COMPACT
                )
            }
        }

    }
}
