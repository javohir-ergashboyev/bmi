package com.example.oliymahadkurslar.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.oliymahadkurslar.MainActivity
import com.example.oliymahadkurslar.adapter.SavedAdapter
import com.example.oliymahadkurslar.databinding.FragmentSavedBinding
import com.example.oliymahadkurslar.model.SavedModel
import com.example.oliymahadkurslar.ui.vm.SavedVm

class SavedFragment : Fragment() {
    private var _binding: FragmentSavedBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SavedVm
    private lateinit var adapter: SavedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentSavedBinding.inflate(LayoutInflater.from(requireContext()), container, false)
        viewModel = (activity as MainActivity).viewModel
        adapter = SavedAdapter(requireContext())
        binding.savedRv.adapter = adapter

        viewModel.getAllSaved().observe(viewLifecycleOwner) {
            it?.let {
                if (it.isEmpty()) {
                    adapter.submitList(it)
                    binding.savedRv.visibility = View.GONE
                    binding.emptyPage.visibility = View.VISIBLE
                } else {
                    adapter.submitList(it)
                    binding.emptyPage.visibility = View.GONE
                    binding.savedRv.visibility = View.VISIBLE
                }
            }


        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}