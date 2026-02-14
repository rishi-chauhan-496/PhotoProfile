package com.example.photoprofile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.photoprofile.ui.viewmodel.UserViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue

class ProfileEditFragment : Fragment() {

    private val viewModel: UserViewModel by viewModel()
    private lateinit var buttonInsert: Button
    lateinit var textNameError: TextView
    lateinit var textCountryError: TextView
    lateinit var textEmailError: TextView
    lateinit var textHobbiesError: TextView
    lateinit var imageViewError: TextView
    lateinit var editTextName: EditText
    lateinit var editTextCountry: EditText
    lateinit var editTextEmail: EditText

    private lateinit var checkBoxReading: CheckBox
    private lateinit var checkBoxCoding: CheckBox
    private lateinit var checkBoxGaming: CheckBox

    private lateinit var buttonPickImage: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_profile_edit, container, false)

        editTextName = view.findViewById(R.id.editTextName)
        editTextCountry = view.findViewById(R.id.editTextCountry)
        editTextEmail = view.findViewById(R.id.editTextEmail)

        checkBoxReading = view.findViewById(R.id.checkBoxReading)
        checkBoxCoding = view.findViewById(R.id.checkBoxCoding)
        checkBoxGaming = view.findViewById(R.id.checkBoxGaming)

        buttonPickImage = view.findViewById(R.id.buttonPickImage)
        buttonInsert = view.findViewById(R.id.buttonInsert)

        textNameError = view.findViewById(R.id.textErrorName)
        textCountryError = view.findViewById(R.id.textErrorCountry)
        textEmailError = view.findViewById(R.id.textErrorEmail)
        textHobbiesError = view.findViewById(R.id.textErrorHobbies)
        imageViewError = view.findViewById(R.id.textErrorImage)

        setupUI()
        setUpSaveUser()

        return view
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

        lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.STARTED) {
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

                    buttonInsert.isEnabled = state.isSavingEnabled
                }
            }
        }
    }

    private fun showToast(messageId: Int) {
        Toast.makeText(context, messageId, Toast.LENGTH_SHORT).show()
    }

}