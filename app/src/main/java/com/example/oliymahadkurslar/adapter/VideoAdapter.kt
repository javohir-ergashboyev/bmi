package com.example.oliymahadkurslar.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.oliymahadkurslar.databinding.CourseItemBinding
import com.example.oliymahadkurslar.databinding.VideoItemBinding
import com.example.oliymahadkurslar.model.CourseModel
import com.example.oliymahadkurslar.model.VideoModel
import com.example.oliymahadkurslar.ui.CoursesActivity
import com.example.oliymahadkurslar.ui.VideoPlayerActivity

class VideoAdapter(private val context: Context) :
    ListAdapter<VideoModel, VideoAdapter.Holder>(DiffUtilCourse()) {
    class Holder(private val binding: VideoItemBinding, private val context: Context) :
        ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(model: VideoModel) {
            binding.videoName.text = model.name
            binding.root.setOnClickListener {
                val intent = Intent(context, VideoPlayerActivity::class.java)
                intent.putExtra("VIDEO_URL", model.url)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = VideoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding, context)

    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffUtilCourse : DiffUtil.ItemCallback<VideoModel>() {
        override fun areItemsTheSame(
            oldItem: VideoModel, newItem: VideoModel
        ): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(
            oldItem: VideoModel, newItem: VideoModel
        ): Boolean {
            return oldItem == newItem
        }

    }

}