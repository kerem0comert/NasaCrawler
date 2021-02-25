package org.keremcomert.nasacrawler.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint
import org.keremcomert.nasacrawler.R
import org.keremcomert.nasacrawler.databinding.FragmentPhotoDetailsBinding
import java.util.*


@AndroidEntryPoint
class FragmentPhotoDetails: Fragment(R.layout.fragment_photo_details) {

    private var b: FragmentPhotoDetailsBinding? = null
    private val args: FragmentPhotoDetailsArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        b = FragmentPhotoDetailsBinding.bind(view)
        b?.apply {
            Glide.with(requireContext())
            .load(args.photo.imgSrc)
            .dontAnimate()
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(R.drawable.ic_nasa)
            .into(ivDetailPhoto)

            tvCameraName.text = args.photo.camera.fullName
            tvRoverName.text = args.photo.rover.name
            tvRoverDate.text = getString(R.string.rover_dates,
                args.photo.rover.launchDate, args.photo.rover.landingDate)
            tvRoverStatus.text = (args.photo.rover.status).capitalize(Locale.ROOT)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        b = null
    }
}