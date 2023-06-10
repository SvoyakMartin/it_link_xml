package com.example.it_link_xml

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.it_link_xml.databinding.ActivityMainBinding
import com.example.it_link_xml.ui.ImageViewModel
import com.example.it_link_xml.ui.ListAdapter
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: ImageViewModel by viewModels()
    private val adapter = ListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        init()
    }

    private fun init() {
        binding.apply {
            recyclerView.apply {
                adapter = this@MainActivity.adapter
                layoutManager = GridLayoutManager(this@MainActivity, calculateColumnCount())
            }
        }

        lifecycleScope.launch {
            viewModel.state.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { stringArray ->
                    adapter.submitList(stringArray)

                    val loadingVisibility = if (stringArray.isEmpty()) View.VISIBLE else View.GONE

                    binding.apply {
                        loadingImage.visibility = loadingVisibility
                        loadingText.visibility = loadingVisibility
                    }
                }
        }
    }

    private fun calculateColumnCount(): Int {
        val displayMetrics = resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        val scalingFactor = 128
        val columnCount = (dpWidth / scalingFactor).roundToInt()
        return if (columnCount >= 1) columnCount else 1
    }
}