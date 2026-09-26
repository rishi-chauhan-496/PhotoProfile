package com.example.photoprofile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.photoprofile.ui.viewmodel.UserInfoViewModel
import com.example.photoprofile.ui.viewmodel.UserViewModel
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue


class ProfileEditFragment : Fragment() {

    private val viewModel: UserViewModel by viewModel()
    private val viewModel2: UserInfoViewModel by viewModel()
    private lateinit var buttonInsert: Button
    private lateinit var buttonUpdate: Button
    lateinit var textNameError: TextView
    lateinit var textCountryError: TextView
    lateinit var textEmailError: TextView
    lateinit var textHobbiesError: TextView
    lateinit var imageViewError: TextView
    lateinit var editTextName: EditText
    lateinit var editTextCountry: MaterialAutoCompleteTextView
    lateinit var editTextEmail: EditText

    private lateinit var checkBoxReading: CheckBox
    private lateinit var checkBoxCoding: CheckBox
    private lateinit var checkBoxGaming: CheckBox
    private lateinit var checkBoxMusic: CheckBox
    private lateinit var checkBoxPhotography: CheckBox
    private lateinit var checkBoxCooking: CheckBox
    private lateinit var checkBoxTraveling: CheckBox
    private lateinit var checkBoxSports: CheckBox
    private lateinit var checkBoxFitness: CheckBox
    private lateinit var checkBoxMovies: CheckBox
    private lateinit var checkBoxDrawing: CheckBox
    private lateinit var buttonPickImage: ImageButton

    private var userId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_profile_edit, container, false)

        val countries = listOf("India", "USA", "UK", "Canada", "Australia")

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            countries
        )
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

        editTextName = view.findViewById(R.id.editTextName)
        editTextCountry = view.findViewById(R.id.editTextCountry)
        editTextEmail = view.findViewById(R.id.editTextEmail)

        checkBoxReading = view.findViewById(R.id.checkBoxReading)
        checkBoxCoding = view.findViewById(R.id.checkBoxCoding)
        checkBoxGaming = view.findViewById(R.id.checkBoxGaming)
        checkBoxMusic = view.findViewById(R.id.checkBoxMusic)
        checkBoxPhotography = view.findViewById(R.id.checkBoxPhotography)
        checkBoxCooking = view.findViewById(R.id.checkBoxCooking)
        checkBoxTraveling = view.findViewById(R.id.checkBoxTraveling)
        checkBoxSports = view.findViewById(R.id.checkBoxSports)
        checkBoxFitness = view.findViewById(R.id.checkBoxFitness)
        checkBoxMovies = view.findViewById(R.id.checkBoxMovies)
        checkBoxDrawing = view.findViewById(R.id.checkBoxDrawing)

        buttonPickImage = view.findViewById(R.id.buttonPickImage)
        buttonInsert = view.findViewById(R.id.buttonInsert)
        buttonUpdate = view.findViewById(R.id.buttonUpdate)

        textNameError = view.findViewById(R.id.textErrorName)
        textCountryError = view.findViewById(R.id.textErrorCountry)
        textEmailError = view.findViewById(R.id.textErrorEmail)
        textHobbiesError = view.findViewById(R.id.textErrorHobbies)
        imageViewError = view.findViewById(R.id.textErrorImage)

        editTextCountry.setAdapter(adapter)

        setupUI()
        setUpSaveUser()

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setUpSaveUser()
    }

    private fun setupUI() {

        val watcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validateFields()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        editTextName.addTextChangedListener(watcher)
        editTextEmail.addTextChangedListener(watcher)
        editTextCountry.addTextChangedListener(watcher)

        checkBoxReading.setOnCheckedChangeListener { _, _ -> validateFields() }
        checkBoxCoding.setOnCheckedChangeListener { _, _ -> validateFields() }
        checkBoxGaming.setOnCheckedChangeListener { _, _ -> validateFields() }

        buttonInsert.setOnClickListener {
            viewModel.saveUser(
                name = editTextName.text.toString(),
                email = editTextEmail.text.toString(),
                country = editTextCountry.text.toString(),
                hobbies = getSelectedHobbies(),
                photo = R.drawable.ic_launcher_foreground.toString()
            )
            showToast(R.string.record_inserted)
            getClear()
            findNavController().navigate(
                R.id.action_profileEdit_to_profile
            )
        }
        buttonUpdate.setOnClickListener {
            viewModel.updateUser(
                name = editTextName.text.toString(),
                email = editTextEmail.text.toString(),
                country = editTextCountry.text.toString(),
                hobbies = getSelectedHobbies(),
                photo = R.drawable.ic_launcher_foreground.toString(),
                userId = userId
            )
            showToast(R.string.record_updated)
            getClear()
            findNavController().navigate(
                R.id.action_profileEdit_to_profile
            )
        }

    }

    private fun validateFields() {
        viewModel.onCredentialsChanges(
            name = editTextName.text.toString(),
            email = editTextEmail.text.toString(),
            hobbies = getSelectedHobbies(),
            country = editTextCountry.text.toString()
        )
    }

    private fun getSelectedHobbies(): String {

        val hobbies = mutableListOf<String>()

        if (checkBoxReading.isChecked) hobbies.add("Reading")
        if (checkBoxCoding.isChecked) hobbies.add("Coding")
        if (checkBoxGaming.isChecked) hobbies.add("Gaming")
        if (checkBoxMusic.isChecked) hobbies.add("Music")
        if (checkBoxPhotography.isChecked) hobbies.add("Photography")
        if (checkBoxCooking.isChecked) hobbies.add("Cooking")
        if (checkBoxTraveling.isChecked) hobbies.add("Traveling")
        if (checkBoxSports.isChecked) hobbies.add("Sports")
        if (checkBoxFitness.isChecked) hobbies.add("Fitness")
        if (checkBoxMovies.isChecked) hobbies.add("Movies")
        if (checkBoxDrawing.isChecked) hobbies.add("Drawing")

        return hobbies.joinToString(", ")
    }

    private fun getClear() {
        editTextName.text.clear()
        editTextCountry.text.clear()
        editTextEmail.text.clear()
        checkBoxReading.isChecked = false
        checkBoxCoding.isChecked = false
        checkBoxReading.isChecked = false
    }

    private fun setUpSaveUser() {

        viewModel2.loadUser()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel2.uiState.collect { state ->

                        state.user?.let { user ->

                            userId = user.id

                            editTextName.setText(user.name)
                            editTextCountry.setText(user.country,false)
                            editTextEmail.setText(user.email)

                            val hobbiesList = user.hobbies.split(",").map { it.trim() }

                            checkBoxReading.isChecked = "Reading" in hobbiesList
                            checkBoxCoding.isChecked = "Coding" in hobbiesList
                            checkBoxGaming.isChecked = "Gaming" in hobbiesList
                            checkBoxMusic.isChecked = "Music" in hobbiesList
                            checkBoxPhotography.isChecked = "Photography" in hobbiesList
                            checkBoxCooking.isChecked = "Cooking" in hobbiesList
                            checkBoxTraveling.isChecked = "Traveling" in hobbiesList
                            checkBoxSports.isChecked = "Sports" in hobbiesList
                            checkBoxFitness.isChecked = "Fitness" in hobbiesList
                            checkBoxMovies.isChecked = "Movies" in hobbiesList
                            checkBoxDrawing.isChecked = "Drawing" in hobbiesList
                        }
                    }
                }

                launch {
                    viewModel.uiState.collect { state ->

                        textNameError.apply {
                            text = state.nameError
                            visibility = if (state.nameError != null) View.VISIBLE else View.GONE
                        }

                        textEmailError.apply {
                            text = state.emailError
                            visibility = if (state.emailError != null) View.VISIBLE else View.GONE
                        }

                        textCountryError.apply {
                            text = state.countryError
                            visibility = if (state.countryError != null) View.VISIBLE else View.GONE
                        }

                        textHobbiesError.apply {
                            text = state.hobbiesError
                            visibility = if (state.hobbiesError != null) View.VISIBLE else View.GONE
                        }

                        if(userId == 0 && state.isSavingEnabled) {
                            buttonInsert.visibility = View.VISIBLE
                            buttonInsert.isEnabled = true
                        }
                        if(userId != 0 && state.isSavingEnabled) {
                            buttonUpdate.visibility = View.VISIBLE
                            buttonUpdate.isEnabled = true
                        }
                    }
                }
            }
        }
    }

    private fun showToast(messageId: Int) {
        Toast.makeText(context, messageId, Toast.LENGTH_SHORT).show()
    }

}