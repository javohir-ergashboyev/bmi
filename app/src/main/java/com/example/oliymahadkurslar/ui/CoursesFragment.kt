package com.example.oliymahadkurslar.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.oliymahadkurslar.adapter.CourseAdapter
import com.example.oliymahadkurslar.databinding.FragmentCoursesBinding
import com.example.oliymahadkurslar.ui.vm.CourseVm

class CoursesFragment : Fragment() {
    private var _binding: FragmentCoursesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CourseVm by viewModels()
    private lateinit var adapter: CourseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentCoursesBinding.inflate(inflater, container, false)
        //setting adapter
        adapter = CourseAdapter(requireContext())
        binding.courseRv.adapter = adapter
        //get data from viewModel
        viewModel.getAllData()

        viewModel.courseData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                adapter.submitList(it)
                binding.courseRv.visibility = View.VISIBLE
                binding.coursePv.visibility = View.GONE
            }
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}