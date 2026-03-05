package com.example.photoprofile

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.photoprofile.ui.adapter.LoadingAdapter

class StorageFragment : Fragment(R.layout.fragment_storage) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: LoadingAdapter

    private val repository = loadImagesRepository()

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

        progressBar.visibility = View.VISIBLE

        // Load images from repository
        val imageList = repository.loadImages()

        adapter.submitList(imageList)

        progressBar.visibility = View.GONE
    }
}