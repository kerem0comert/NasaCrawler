package org.keremcomert.nasacrawler.view

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import org.keremcomert.nasacrawler.R
import org.keremcomert.nasacrawler.controller.PhotoAdapter
import org.keremcomert.nasacrawler.controller.PhotoLoadStateAdapter
import org.keremcomert.nasacrawler.databinding.FragmentPhotosBinding
import org.keremcomert.nasacrawler.model.Photo
import org.keremcomert.nasacrawler.viewmodel.PhotoViewModel

@AndroidEntryPoint
class FragmentPhotos : Fragment(R.layout.fragment_photos), PhotoAdapter.OnPhotoSelectedListener {
    private val viewModel by viewModels<PhotoViewModel>()
    private var b: FragmentPhotosBinding? = null
    private lateinit var photoAdapter: PhotoAdapter
    private val args: FragmentPhotosArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        b = FragmentPhotosBinding.bind(view)
        viewModel.currentRover.value = args.selectedRover
        initRecyclerView()
        initTabLayout()
    }

    private fun initTabLayout() {
        b?.apply {
            tabLayoutRovers.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    viewModel.currentRover.value = tab?.text.toString()
                    photoAdapter.retry()
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }
    }

    private fun initRecyclerView() {
        photoAdapter = PhotoAdapter(this)


        /**
         * The adapter is now tightly bound to any changes in the viewModel.
         */
        viewModel.photos.observe(viewLifecycleOwner) {
            photoAdapter.submitData(
                viewLifecycleOwner.lifecycle,
                it
            )
        }

        b?.rvPhotos?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)

            /**
             * In both the header and the footer of the adapter, the LoadStates must be checked.
             * Anytime the reload function is invoked from the Adapter class (explained in the class
             * itself) the articleAdapter instance should try to retry and update its requests.
             */
            adapter = photoAdapter.withLoadStateHeaderAndFooter(
                header = PhotoLoadStateAdapter { photoAdapter.retry() },
                footer = PhotoLoadStateAdapter { photoAdapter.retry() }
            )
        }

        /**
         * To check for state changes whenever a search term is being entered this listener is added.
         *
         */
        photoAdapter.addLoadStateListener { loadState ->
            b?.apply {
                viewPhotoLoadState.bReload.isVisible = loadState.source.refresh is LoadState.Loading
                viewPhotoLoadState.tvErrorWithResults.isVisible = loadState.source.refresh is LoadState.Error
                viewPhotoLoadState.bReload.isVisible = loadState.source.refresh is LoadState.Error
                rvPhotos.isVisible = loadState.source.refresh is LoadState.NotLoading

                viewPhotoLoadState.bReload.setOnClickListener { photoAdapter.retry() }

                /**
                 * The cases where we can deduce that the search returned nothing is, when the
                 * end of the page is reached, when the load state is not in Loading state yet
                 * it did not throw any error, with not even one item returned.
                 */
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    photoAdapter.itemCount < 1) {
                    rvPhotos.visibility = View.GONE
                    tvNoQueryResults.visibility = View.VISIBLE
                    ivSadMonkey.visibility = View.VISIBLE
                } else if (photoAdapter.itemCount < 1) {
                    rvPhotos.visibility = View.GONE
                    viewPhotoLoadState.llViewArticleLoadState.visibility = View.VISIBLE
                    viewPhotoLoadState.pbLoadingContent.visibility = View.GONE
                } else {
                    tvNoQueryResults.visibility = View.GONE
                    ivSadMonkey.visibility = View.GONE
                }
            }
        }

        setHasOptionsMenu(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        b = null
    }

    override fun onPhotoSelected(photo: Photo) {
        findNavController().navigate(
            FragmentPhotosDirections
                .actionFragmentPhotosToFragmentPhotoDetails(
                    photo,
                    viewModel.currentRover.value!!,
                    photo.id.toString()
                )
        )
    }

}