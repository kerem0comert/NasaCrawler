package org.keremcomert.nasacrawler.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import org.keremcomert.nasacrawler.R
import org.keremcomert.nasacrawler.controller.PhotoAdapter
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
                it)
        }

        b?.rvPhotos?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = photoAdapter
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
                    photo.id.toString()))
    }

}