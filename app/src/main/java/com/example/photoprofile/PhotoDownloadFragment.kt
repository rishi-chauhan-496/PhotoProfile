package com.example.photoprofile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.photoprofile.ui.dataclass.SrcUi
import com.example.photoprofile.ui.viewmodel.PhotoDetailViewModel
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue

class PhotoDownloadFragment : Fragment() {

    private val viewModel: PhotoDetailViewModel by viewModel()
    lateinit var imageSrc: SrcUi
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
                    .actionPhotoDownloadToFullScreen(imageSrc.portrait)

            findNavController().navigate(action)
        }

        val downloadBtn =
            view.findViewById<ExtendedFloatingActionButton>(R.id.buttonDownload)

        downloadBtn.setOnClickListener { view ->

            val popup = PopupMenu(requireContext(), view)
            popup.menuInflater.inflate(R.menu.download_menu, popup.menu)

            popup.setOnMenuItemClickListener {

                when (it.itemId) {

                    R.id.download_original -> {
                        viewModel.downloadPhoto(requireContext(), imageSrc.original,imageId,"original")
                        true
                    }

                    R.id.download_large -> {
                        viewModel.downloadPhoto(requireContext(), imageSrc.large,imageId,"large")
                        true
                    }

                    R.id.download_large2X -> {
                        viewModel.downloadPhoto(requireContext(), imageSrc.large2x,imageId,"large2x")
                        true
                    }

                    R.id.download_medium -> {
                        viewModel.downloadPhoto(requireContext(), imageSrc.medium,imageId,"medium")
                        true
                    }

                    R.id.download_small -> {
                        viewModel.downloadPhoto(requireContext(), imageSrc.small,imageId,"small")
                        true
                    }
                    R.id.download_portrait -> {
                        viewModel.downloadPhoto(requireContext(), imageSrc.portrait,imageId,"portrait")
                        true
                    }

                    R.id.download_landscape -> {
                        viewModel.downloadPhoto(requireContext(), imageSrc.landscape,imageId,"landscape")
                        true
                    }

                    R.id.download_tiny -> {
                        viewModel.downloadPhoto(requireContext(), imageSrc.tiny,imageId,"tiny")
                        true
                    }

                    else -> false
                }
            }

            popup.show()
        }

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

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args =
            PhotoDownloadFragmentArgs
                .fromBundle(requireArguments())

        imageSrc = args.imageSrc
        imageId = args.imageId

        Glide.with(this)
            .load(imageSrc.portrait)
            .into(view.findViewById(R.id.imageViewDownload))
    }
}
