package org.keremcomert.nasacrawler.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import org.keremcomert.nasacrawler.databinding.ViewPhotoLoadStateBinding

/**
* LoadStateAdapter is a neat tool that the paging3 library provides, to handle different states
* of the pagination.
*/
class PhotoLoadStateAdapter(private val reload: () -> Unit) : LoadStateAdapter<PhotoLoadStateAdapter.LoadStateViewHolder>(){


    inner class LoadStateViewHolder(private val b: ViewPhotoLoadStateBinding): RecyclerView.ViewHolder(b.root){

        /**
         * Attach the onCLickListener in the init blocks to avoid repetition. The user can invoke
         * the reload() lambda function that I passed as an argument into the PhotoLoadStateAdapter,
         * which will retry fetching the data for the pagination. This may prove useful in problematic
         * cases regarding the network connection.
         */

        init {
            b.bReload.setOnClickListener { reload.invoke() }
        }

        /**
         * An item can be on several load states, namely Loading, NotLoading and Error:
         * given as constants by the paging3's LoadState class. Here, the crucial state to check
         * is whether new data is being loaded, and the user must be informed about this state
         * with visual clues.
         */
        internal fun bind(loadState: LoadState){
            b.apply {
                pbLoadingContent.isVisible = loadState is LoadState.Loading
                tvErrorWithResults.isVisible = loadState !is LoadState.Loading
                bReload.isVisible = loadState !is LoadState.Loading
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder =
        LoadStateViewHolder(ViewPhotoLoadStateBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

}