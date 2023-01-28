package com.ditta.worldbeers.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ditta.worldbeers.R
import com.ditta.worldbeers.databinding.ItemBeerListBinding
import com.ditta.worldbeers.model.Beer
import com.ditta.worldbeers.ui.util.loadImage

class BeerPagingAdapter(
    private val onItemClicked: (beer: Beer) -> Unit
) : PagingDataAdapter<Beer, BeerPagingAdapter.BeerViewHolder>(diffCallback) {


    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Beer>() {
            override fun areItemsTheSame(oldItem: Beer, newItem: Beer): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Beer, newItem: Beer): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BeerPagingAdapter.BeerViewHolder {
        val binding =
            ItemBeerListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BeerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BeerPagingAdapter.BeerViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(item, onItemClicked) }

    }


    inner class BeerViewHolder(
        private val binding: ItemBeerListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(beer: Beer, onItemClicked: (beer: Beer) -> Unit) {

            binding.apply {
                cardviewBeer.setOnClickListener {
                    onItemClicked(beer)
                }
                imageViewBeer.loadImage(
                    beer.imageUrl,
                    R.drawable.loading_animation,
                    R.drawable.ic_connection_error
                )
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