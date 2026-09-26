package com.example.photoprofile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.photoprofile.ui.adapter.ImageAdapter
import com.example.photoprofile.ui.viewmodel.PhotosViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue

class PhotosFragment : Fragment() {

    private val viewModel: PhotosViewModel by viewModel()
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    private lateinit var adapter: ImageAdapter
    private lateinit var layoutManager: StaggeredGridLayoutManager
    private var isLoading = false
    private var isLastPage = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_photos, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        progressBar = view.findViewById(R.id.progressBar)

        layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        recyclerView.layoutManager = layoutManager

        setupRecyclerView()
        observeUiState()

        viewModel.loadPhotos()

        return view
    }

    private fun setupRecyclerView() {

        adapter = ImageAdapter { imageSrc,imageId ->

            val action =
                PhotosFragmentDirections
                    .actionPhotosToPhotoDownload(imageSrc,imageId)

            findNavController().navigate(action)
        }
        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(
                recyclerView: RecyclerView,
                dx: Int,
                dy: Int
            ) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy <= 0) return // ignore scroll up

                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItems =
                    layoutManager.findFirstVisibleItemPositions(null)

                val firstVisibleItem =
                    firstVisibleItems.minOrNull() ?: 0

                val shouldLoadMore =
                    !isLoading &&
                            !isLastPage &&
                            (visibleItemCount + firstVisibleItem >= totalItemCount - 5)

                if (shouldLoadMore) {
                    isLoading = true
                    viewModel.loadPhotos()
                }

            }
        })
    }

    private fun observeUiState() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.uiState.collect { state ->

                    progressBar.visibility =
                        if (state.isLoading) View.VISIBLE else View.GONE

                    Log.d("main","${state.page}")
                    Log.d("main","${state.perPage}")
                    Log.d("main","${state.nextPage}")
                    Log.d("main","${state.photos}")
                    adapter.addPhotos(state.photos)
                    isLoading = state.isLoading
                    isLastPage = state.nextPage == null

                }
            }
        }
    }
}
