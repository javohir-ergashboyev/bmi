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
import com.example.oliymahadkurslar.model.CourseModel
import com.example.oliymahadkurslar.ui.CoursesActivity

class CourseAdapter(private val context: Context) :
    ListAdapter<CourseModel, CourseAdapter.Holder>(DiffUtilCourse()) {
    class Holder(private val binding: CourseItemBinding, private val context: Context) :
        ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(model: CourseModel) {
            binding.coursePrice.text = model.price + " so'm"
            if (model.status) {
                binding.courseStatus.text = "Purchased"
            } else {
                binding.courseStatus.text = "Not Purchased"
                binding.lockedCover.visibility = View.VISIBLE
            }
            Glide.with(context).load(model.img.toUri()).into(binding.courseImage)
            binding.courseTitle.text = model.title
            binding.courseDescription.text = model.desc
            binding.clickLayout.setOnClickListener {
                val intent = Intent(context, CoursesActivity::class.java)
                intent.putExtra("COURSE_ID", model.id)
                intent.putExtra("COURSE_NAME", model.title)
                intent.putExtra("COURSE_DESC", model.desc)
                intent.putExtra("COURSE_IMAGE", model.img)
                intent.putExtra("COURSE_PRICE", model.price)
                context.startActivity(intent)
            }
            binding.lockedCover.setOnClickListener { }
            binding.root.setOnLongClickListener {
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = CourseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding, context)

    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffUtilCourse : DiffUtil.ItemCallback<CourseModel>() {
        override fun areItemsTheSame(
            oldItem: CourseModel, newItem: CourseModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CourseModel, newItem: CourseModel
        ): Boolean {
            return oldItem == newItem
        }

    }

}