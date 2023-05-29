package com.example.oliymahadkurslar.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.oliymahadkurslar.databinding.CourseItemBinding
import com.example.oliymahadkurslar.databinding.NewsItemBinding
import com.example.oliymahadkurslar.databinding.VideoItemBinding
import com.example.oliymahadkurslar.model.CourseModel
import com.example.oliymahadkurslar.model.NewsModel
import com.example.oliymahadkurslar.model.VideoModel
import com.example.oliymahadkurslar.ui.CoursesActivity
import com.example.oliymahadkurslar.ui.VideoPlayerActivity

class NewsAdapter(private val context: Context) :
    ListAdapter<NewsModel, NewsAdapter.Holder>(DiffUtilCourse()) {
    class Holder(private val binding: NewsItemBinding, private val context: Context) :
        ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(model: NewsModel) {
            binding.newsMessage.text = model.message
            Log.d("IMAGEIS", model.image)
            Glide.with(context).load(model.image.toUri()).into(binding.newsImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding, context)

    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffUtilCourse : DiffUtil.ItemCallback<NewsModel>() {
        override fun areItemsTheSame(
            oldItem: NewsModel, newItem: NewsModel
        ): Boolean {
            return oldItem.message == newItem.message
        }

        override fun areContentsTheSame(
            oldItem: NewsModel, newItem: NewsModel
        ): Boolean {
            return oldItem == newItem
        }

    }

}