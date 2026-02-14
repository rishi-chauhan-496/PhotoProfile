package com.example.photoprofile

import com.example.photoprofile.domain.usecase.SaveUserUseCase
import com.example.photoprofile.domain.usecase.UpdateUserUseCase
import com.example.photoprofile.ui.viewmodel.UserViewModel
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UserViewModelValidationTest {

    private val saveUserUseCase: SaveUserUseCase = mockk()
    private val updateUserUseCase: UpdateUserUseCase = mockk()
    private lateinit var sut: UserViewModel

    @Before
    fun setup() {
        sut = UserViewModel(
            saveUserUseCase = saveUserUseCase,
            updateUserUseCase = updateUserUseCase,
        )
    }

    @Test
    fun `check validation with all valid fields should produce no error`() {

        sut.onCredentialsChanges(
            name = "Rishi",
            email = "rishi@gmail.com",
            hobbies = "Coding",
            country = "India"
        )

        val state = sut.uiState.value

        assertEquals(null, state.nameError)
        assertEquals(null, state.emailError)
        assertEquals(null, state.hobbiesError)
        assertEquals(null, state.countryError)
        assertEquals(true, state.isSavingEnabled)
    }

    @Test
    fun `check validation with null name should produce name error`() {

        sut.onCredentialsChanges(
            name = null,
            email = "rishi@gmail.com",
            hobbies = "Coding",
            country = "India"
        )

        val state = sut.uiState.value

        assertEquals("Name required", state.nameError)
        assertEquals(null, state.emailError)
        assertEquals(null, state.hobbiesError)
        assertEquals(null, state.countryError)
        assertEquals(false, state.isSavingEnabled)
    }

    @Test
    fun `check validation with null email should produce email error`() {

        sut.onCredentialsChanges(
            name = "Rishi",
            email = null,
            hobbies = "Coding",
            country = "India"
        )

        val state = sut.uiState.value

        assertEquals(null, state.nameError)
        assertEquals("Email required", state.emailError)
        assertEquals(null, state.hobbiesError)
        assertEquals(null, state.countryError)
        assertEquals(false, state.isSavingEnabled)
    }

    @Test
    fun `check validation with null hobbies should produce hobbies error`() {

        sut.onCredentialsChanges(
            name = "Rishi",
            email = "rishi@gmail.com",
            hobbies = null,
            country = "India"
        )

        val state = sut.uiState.value

        assertEquals(null,state.nameError)
        assertEquals(null,state.emailError)
        assertEquals("Hobbies required",state.hobbiesError)
        assertEquals(null,state.countryError)
        assertEquals(false,state.isSavingEnabled)
    }

    @Test
    fun `check validation with null country should produce country error`() {

        sut.onCredentialsChanges(
            name = "Rishi",
            email = "rishi@gmail.com",
            hobbies = "Coding",
            country = null
        )

        val state = sut.uiState.value

        assertEquals(null,state.nameError)
        assertEquals(null,state.emailError)
        assertEquals(null,state.hobbiesError)
        assertEquals("Country required",state.countryError)
        assertEquals(false,state.isSavingEnabled)
    }

    @Test
    fun `check validation with all null fields should produce all errors`() {

        sut.onCredentialsChanges(
            name = null,
            email = null,
            hobbies = null,
            country = null
        )

        val state = sut.uiState.value

        assertEquals("Name required", state.nameError)
        assertEquals("Email required", state.emailError)
        assertEquals("Hobbies required", state.hobbiesError)
        assertEquals("Country required", state.countryError)
        assertEquals(false, state.isSavingEnabled)
    }
}