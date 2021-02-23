package org.keremcomert.nasacrawler.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.keremcomert.nasacrawler.R
import org.keremcomert.nasacrawler.controller.PhotoAdapter
import org.keremcomert.nasacrawler.databinding.FragmentPhotosBinding
import org.keremcomert.nasacrawler.model.Photo
import org.keremcomert.nasacrawler.viewmodel.PhotoViewModel

@AndroidEntryPoint
class FragmentPhotos: Fragment(R.layout.fragment_photos), PhotoAdapter.OnPhotoSelectedListener {
    private val viewModel by viewModels<PhotoViewModel>()
    private var b: FragmentPhotosBinding? = null
    private lateinit var photoAdapter: PhotoAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        b = FragmentPhotosBinding.bind(view)
        initRecyclerView()
    }

    private fun initRecyclerView(){
        photoAdapter = PhotoAdapter(this)

        /**
         * The adapter is now tightly bound to any changes in the viewModel.
         */
        viewModel.photos.observe(viewLifecycleOwner) { photoAdapter.submitData(viewLifecycleOwner.lifecycle, it) }

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
        findNavController().navigate(FragmentPhotosDirections.actionFragmentPhotosToFragmentPhotoDetails(photo))
    }

}