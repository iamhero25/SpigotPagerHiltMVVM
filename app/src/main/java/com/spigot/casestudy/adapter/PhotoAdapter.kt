package com.spigot.casestudy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.spigot.casestudy.R
import com.spigot.casestudy.databinding.ItemPhotoBinding
import com.spigot.casestudy.model.PhotoEntity

class PhotoAdapter(): PagingDataAdapter<PhotoEntity, PhotoAdapter.PhotoViewHolder>(DIFF_CALLBACK)  {

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<PhotoEntity> =
            object : DiffUtil.ItemCallback<PhotoEntity>() {
                override fun areItemsTheSame(oldItem: PhotoEntity, newItem: PhotoEntity) =
                    oldItem == newItem

                override fun areContentsTheSame(oldItem: PhotoEntity, newItem: PhotoEntity) =
                    oldItem.id == newItem.id
            }
    }

    class PhotoViewHolder(private val binding: ItemPhotoBinding): RecyclerView.ViewHolder(binding.root)  {


        fun bind(photoEntity: PhotoEntity){
            binding.apply {
                tvPhotoId.text = photoEntity.id.toString()
                tvPhotoTitle.text = photoEntity.title
                //ivPhotoThumbnail.load(photoEntity.url)
                ivPhotoImage.load(photoEntity.thumbnailUrl){
                    placeholder(R.drawable.ic_placeholder)
                    error(R.drawable.ic_error)
                }


                if ( ( photoEntity.id % 2 ) == 0 ) {
                    tvPhotoId.setSolidColor("#FF018786")
                    tvPhotoId.setStrokeColor("#FF6200EE")
                } else {
                    tvPhotoId.setSolidColor("#FF6200EE")
                    tvPhotoId.setStrokeColor("#FF018786")
                }
            }
        }

        companion object{
            fun from(parent: ViewGroup): PhotoViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemPhotoBinding.inflate(layoutInflater, parent, false)
                return PhotoViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentPhoto = getItem(position)!!
        holder.bind(currentPhoto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder.from(parent)
    }
}