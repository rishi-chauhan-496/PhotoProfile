package com.example.photoprofile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class FullScreenImageFragment :
    Fragment(R.layout.fragment_full_screen_image) {

    private val args: FullScreenImageFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val image = view.findViewById<ImageView>(R.id.fullImageView)

        Glide.with(requireContext())
            .load(args.imageUrl)
            .into(image)

        // Tap image to go back
        image.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onResume() {
        super.onResume()

        val window = requireActivity().window

        WindowCompat.setDecorFitsSystemWindows(window, false)

        view?.post {

            val controller =
                WindowInsetsControllerCompat(window, window.decorView)

            // THIS hides BOTH status + navigation bar
            controller.hide(WindowInsetsCompat.Type.systemBars())

            controller.systemBarsBehavior =
                WindowInsetsControllerCompat
                    .BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        (requireActivity() as? AppCompatActivity)
            ?.supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()

        val window = requireActivity().window

        WindowCompat.setDecorFitsSystemWindows(window, true)

        WindowInsetsControllerCompat(window, window.decorView)
            .show(WindowInsetsCompat.Type.systemBars())

        (requireActivity() as? AppCompatActivity)
            ?.supportActionBar?.show()
    }
}