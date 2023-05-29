package com.example.oliymahadkurslar.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.oliymahadkurslar.databinding.SavedItemBinding
import com.example.oliymahadkurslar.model.SavedModel
import com.example.oliymahadkurslar.ui.CoursesActivity

class SavedAdapter(private val context: Context) :
    ListAdapter<SavedModel, SavedAdapter.Holder>(DiffUtilCourse()) {
    class Holder(private val binding: SavedItemBinding, private val context: Context) :
        ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(model: SavedModel) {
            binding.coursePrice.text = model.price + " so'm"
            binding.courseTitle.text = model.title
            Glide.with(context).load(model.img.toUri()).into(binding.courseImage)
            binding.courseDescription.text = model.desc
            binding.clickLayout.setOnClickListener {
                val intent = Intent(context, CoursesActivity::class.java)
                intent.putExtra("COURSE_ID", model.course_id)
                intent.putExtra("COURSE_NAME", model.title)
                intent.putExtra("COURSE_DESC", model.desc)
                intent.putExtra("COURSE_IMAGE", model.img)
                intent.putExtra("COURSE_PRICE", model.price)
                context.startActivity(intent)
            }
            binding.lockedCover.setOnClickListener { }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = SavedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding, context)

    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffUtilCourse : DiffUtil.ItemCallback<SavedModel>() {
        override fun areItemsTheSame(
            oldItem: SavedModel, newItem: SavedModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SavedModel, newItem: SavedModel
        ): Boolean {
            return oldItem == newItem
        }

    }

}