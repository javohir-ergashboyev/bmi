package com.example.oliymahadkurslar.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.oliymahadkurslar.R
import com.example.oliymahadkurslar.adapter.VideoAdapter
import com.example.oliymahadkurslar.databinding.ActivityCoursesBinding
import com.example.oliymahadkurslar.db.SavedDatabase
import com.example.oliymahadkurslar.db.SavedRep
import com.example.oliymahadkurslar.model.SavedModel
import com.example.oliymahadkurslar.ui.vm.SavedVm
import com.example.oliymahadkurslar.ui.vm.SavedVmf
import com.example.oliymahadkurslar.ui.vm.VideosVm

class CoursesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCoursesBinding
    private lateinit var adapter: VideoAdapter
    private val viewModel: VideosVm by viewModels()
    private lateinit var savedVm: SavedVm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoursesBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        val id = intent.getIntExtra("COURSE_ID", 0)
        val title = intent.getStringExtra("COURSE_NAME").toString()

        binding.courseName.text = title
        adapter = VideoAdapter(this)
        binding.videosRv.adapter = adapter

        viewModel.getAllVideos(id)
        viewModel.videosList.observe(this) {
            if (it.isNotEmpty()) {
                binding.videosRv.visibility = View.VISIBLE
                binding.videosPv.visibility = View.GONE
                adapter.submitList(it)
            }
        }

        val m = SavedModel(
            id = id,
            course_id = id,
            desc = intent.getStringExtra("COURSE_DESC")!!,
            title = title,
            img = intent.getStringExtra("COURSE_IMAGE")!!,
            price = intent.getStringExtra("COURSE_PRICE")!!,
        )

        val db = SavedDatabase(this)
        val repo = SavedRep(db)
        val vmf = SavedVmf(repo)
        savedVm = ViewModelProvider(this, vmf)[SavedVm::class.java]
        savedVm.getAllSaved().observe(this) { list ->
            list.forEach {
                if (it.title == title) {
                    binding.saveBtn.visibility = View.GONE
                    binding.removeBtn.visibility = View.VISIBLE
                } else {
                    binding.saveBtn.visibility = View.VISIBLE
                    binding.removeBtn.visibility = View.GONE
                }
            }
        }

        binding.removeBtn.setOnClickListener {
            savedVm.delete(m)
            Toast.makeText(this, "Deleted from saved list", Toast.LENGTH_SHORT).show()
            binding.saveBtn.visibility = View.VISIBLE
            binding.removeBtn.visibility = View.GONE
        }
        binding.saveBtn.setOnClickListener {
            savedVm.save(m)
            Toast.makeText(this, "Added to saved list", Toast.LENGTH_SHORT).show()
        }
    }
}



