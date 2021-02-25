package org.keremcomert.nasacrawler.controller


import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import org.keremcomert.nasacrawler.R
import org.keremcomert.nasacrawler.databinding.ItemPhotoBinding
import org.keremcomert.nasacrawler.model.Photo

class PhotoAdapter(private val listener: OnPhotoSelectedListener) :
    PagingDataAdapter<Photo, PhotoAdapter.PhotoViewHolder>(PHOTO_COMPARATOR) {

    /**
     * RecyclerView's DiffUtil class provides an easy way to compare two items, based on their
     * instances. Since PagingDataAdapter needs a callback for any data changes to achieve
     * pagination, these methods provide this functionality. Crucially, the id for each photo
     * acts as a primary key in this context, and can be used to determine whether two items
     * are the same.
     */
    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean =
                oldItem == newItem
        }
    }

    interface OnPhotoSelectedListener {
        fun onPhotoSelected(photo: Photo)
    }

    class PhotoViewHolder(
        private val b: ItemPhotoBinding,
        private val listener: OnPhotoSelectedListener
    ) : RecyclerView.ViewHolder(b.root) {
        internal fun bind(photo: Photo) {
            b.apply {
                Glide.with(itemView)
                    .load(photo.imgSrc)//.load("https://openthread.google.cn/images/ot-contrib-google.png")//
                    .dontAnimate()
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_nasa)
                    .into(ivPhoto)
                llPhoto.setOnClickListener { listener.onPhotoSelected(photo) }
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder =
        PhotoViewHolder(
            ItemPhotoBinding
                .inflate(LayoutInflater.from(parent.context), parent, false), listener
        )

    /**
     * This syntax is concise and easy to understand, so that's what I preferred here for this
     * simple demonstration. However, if devices under build level 26 need to be supported,
     * a more traditional approach where the binding happens within the OnBindViewHolder(PhotoViewHolder, Int)
     * would also be possible.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }


}