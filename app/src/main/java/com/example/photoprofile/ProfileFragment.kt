package com.example.photoprofile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.photoprofile.ui.viewmodel.UserInfoViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue

class ProfileFragment : Fragment() {

    private val viewModel: UserInfoViewModel by viewModel()
    lateinit var textHeader: TextView
    lateinit var textName: TextView
    lateinit var textCountry: TextView
    lateinit var textEmail: TextView
    lateinit var textHobbies: TextView
    lateinit var imageView: ImageView
    lateinit var textName1: TextView
    lateinit var textCountry1: TextView
    lateinit var textEmail1: TextView
    lateinit var textHobbies1: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        view.findViewById<Button>(R.id.buttonEdit).setOnClickListener {
            findNavController().navigate(
                R.id.action_profile_to_profileEdit
            )
        }

        textHeader = view.findViewById(R.id.textHeader)
        textName = view.findViewById(R.id.textViewName)
        textCountry = view.findViewById(R.id.textViewCountry)
        textEmail = view.findViewById(R.id.textViewEmail)
        textHobbies = view.findViewById(R.id.textViewHobbies)
        imageView = view.findViewById(R.id.imageView)
        textName1 = view.findViewById(R.id.textViewName1)
        textCountry1 = view.findViewById(R.id.textViewCountry1)
        textEmail1 = view.findViewById(R.id.textViewEmail1)
        textHobbies1 = view.findViewById(R.id.textViewHobbies1)

        dataSetUp()

        return view
    }

    fun dataSetUp() {

        lifecycleScope.launch {

            viewModel.loadUser()

            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->

                    Log.d("PROFILE_DEBUG", "User is: ${state.user}")

                    if (state.user == null) {

                        textHeader.text = getString(R.string.edit_profile_header)

                        textName1.visibility = View.GONE
                        textCountry1.visibility = View.GONE
                        textEmail1.visibility = View.GONE
                        textHobbies1.visibility = View.GONE
                        imageView.visibility = View.GONE

                    } else {

                        textHeader.text = getString(R.string.profile_header)

                        textName1.visibility = View.VISIBLE
                        textCountry1.visibility = View.VISIBLE
                        textEmail1.visibility = View.VISIBLE
                        textHobbies1.visibility = View.VISIBLE
                        imageView.visibility = View.VISIBLE


                        textName.text = state.user.name
                        textCountry.text = state.user.country
                        textEmail.text = state.user.email
                        textHobbies.text = state.user.hobbies
                        imageView.setImageResource(R.drawable.profile)
                    }
                }

            }
        }

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost:  MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_profile, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_special -> {
                        findNavController().navigate(
                            R.id.action_profile_to_profileEdit
                        )
                        true
                    }
                    else -> false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}