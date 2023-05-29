package com.example.oliymahadkurslar.ui

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.oliymahadkurslar.adapter.NewsAdapter
import com.example.oliymahadkurslar.adapter.SlideAdapter
import com.example.oliymahadkurslar.databinding.FragmentHomeBinding
import com.example.oliymahadkurslar.ui.vm.HomeVm
import java.util.*

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var currentPage = 0
    private var timer: Timer? = null
    private val DELAY_MS: Long = 3000 // Delay in milliseconds before auto-sliding
    private val PERIOD_MS: Long = 5000

    private lateinit var newsAdapter: NewsAdapter
    private val viewModel: HomeVm by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentHomeBinding.inflate(LayoutInflater.from(requireContext()), container, false)

        val list = ArrayList<String>()
        list.add("https://oliymahad.uz/wp-content/uploads/2020/06/IMG_20200611_090005.png")
        list.add("https://oliymahad.uz/wp-content/uploads/2020/03/photo_2020-03-15_17-55-19-800x436.jpg")
        list.add("https://static.norma.uz/images/141266_2413aef7491cb35c817b8d78540d.jpg")

        val adapter = SlideAdapter(list)
        binding.viewPager.adapter = adapter
        val handler = Handler()
        val update = Runnable {
            if (currentPage == adapter.itemCount) {
                currentPage = 0
            }
            binding.viewPager.setCurrentItem(currentPage++, true)
        }

        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, DELAY_MS, PERIOD_MS)


        //retrieving news from firestore
        newsAdapter = NewsAdapter(requireContext())
        binding.newsRv.adapter = newsAdapter
        viewModel.getAllNews()
        viewModel.courseData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.newsRv.visibility = View.VISIBLE
                binding.newsPb.visibility = View.GONE
                newsAdapter.submitList(it)
            }
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        timer?.cancel()
    }
}