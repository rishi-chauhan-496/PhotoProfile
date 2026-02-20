package com.example.photoprofile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class PhotoDownloadFragment : Fragment() {

    lateinit var imageUrl: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_photo_download, container, false)

        view.findViewById<Button>(R.id.buttonDownload).setOnClickListener {
            FileUtils.writeToFile(requireContext(),imageUrl)
            Log.d("main","Write to File")

            val url = FileUtils.readToFile(requireContext())
            Log.d("main",url)
        }


        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args =
            PhotoDownloadFragmentArgs
                .fromBundle(requireArguments())

        imageUrl = args.imageUrl

        Log.d("main",imageUrl)

        Glide.with(this)
            .load(imageUrl)
            .into(view.findViewById(R.id.imageViewDownload))
    }
}
