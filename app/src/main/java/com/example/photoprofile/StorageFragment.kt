package com.example.photoprofile

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.photoprofile.ui.adapter.LoadingAdapter
import com.example.photoprofile.ui.viewmodel.PhotoLoadViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue

class StorageFragment : Fragment(R.layout.fragment_storage) {
    private val viewModel: PhotoLoadViewModel by viewModel()

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: LoadingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewStr)
        progressBar = view.findViewById(R.id.progressBarStr)

        setupRecyclerView()
        loadImages()
    }

    private fun setupRecyclerView() {
        adapter = LoadingAdapter()

        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        recyclerView.adapter = adapter
    }

    private fun loadImages() {

        lifecycleScope.launch {

            viewModel.loadPhotos()

            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->

                    if(state.isLoading) {
                        progressBar.visibility = View.VISIBLE
                    }

                    // Load images from repository
                    val imageList = state.photo

                    if (state.photo != null)
                        adapter.submitList(imageList)
                    Log.d("main","$imageList")

                    if(!state.isLoading) {
                        progressBar.visibility = View.GONE
                    }
                }
            }
        }
    }
}