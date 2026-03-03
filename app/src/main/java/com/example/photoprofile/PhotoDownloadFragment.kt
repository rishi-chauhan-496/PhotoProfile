package com.example.photoprofile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.photoprofile.ui.viewmodel.PhotoDetailViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue

class PhotoDownloadFragment : Fragment() {

    private val viewModel: PhotoDetailViewModel by viewModel()
    lateinit var imageUrl: String
    var imageId: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_photo_download, container, false)

        val imageView = view.findViewById<ImageView>(R.id.imageViewDownload)

        imageView.setOnClickListener {

            Log.d("main", "Image clicked")

            val action =
                PhotoDownloadFragmentDirections
                    .actionPhotoDownloadToFullScreen(imageUrl)

            findNavController().navigate(action)
        }

        view.findViewById<Button>(R.id.buttonDownload).setOnClickListener {

            viewModel.downloadPhoto(requireContext(), imageUrl,imageId)

            lifecycleScope.launch {
                viewModel.uiState.collect { state ->

                    if (state.downloadSuccess) {
                        Toast.makeText(context, "Downloaded Photo", Toast.LENGTH_SHORT).show()
                    }

                    state.error?.let {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args =
            PhotoDownloadFragmentArgs
                .fromBundle(requireArguments())

        imageUrl = args.imageUrl
        imageId = args.imageId

        Log.d("main",imageUrl)
        Log.d("main","$imageId")

        Glide.with(this)
            .load(imageUrl)
            .into(view.findViewById(R.id.imageViewDownload))
    }
}
