package org.keremcomert.nasacrawler.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.keremcomert.nasacrawler.R
import org.keremcomert.nasacrawler.databinding.FragmentPhotosBinding
import org.keremcomert.nasacrawler.viewmodel.PhotoViewModel

@AndroidEntryPoint
class FragmentPhotos: Fragment(R.layout.fragment_photos) {
    private val viewModel by viewModels<PhotoViewModel>()
    private var b: FragmentPhotosBinding? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        b = FragmentPhotosBinding.bind(view)
        initRecyclerView()
    }

    private fun initRecyclerView(){

    }

    override fun onDestroyView() {
        super.onDestroyView()
        b = null
    }

}