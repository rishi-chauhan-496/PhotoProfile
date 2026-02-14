package com.example.photoprofile.di

import com.example.photoprofile.MainApplication
import com.example.photoprofile.data.local.ProfileDataBase
import com.example.photoprofile.data.repository.PexelsRepositoryImpl
import com.example.photoprofile.data.repository.UserRepositoryImpl
import com.example.photoprofile.domain.repository.PexelsRepository
import com.example.photoprofile.domain.repository.UserRepository
import com.example.photoprofile.domain.usecase.GetCuratedPhotosUseCase
import com.example.photoprofile.domain.usecase.GetUserUseCase
import com.example.photoprofile.domain.usecase.SaveUserUseCase
import com.example.photoprofile.domain.usecase.UpdateUserUseCase
//import com.example.photoprofile.ui.viewmodel.PhotoDetailViewModel
import com.example.photoprofile.ui.viewmodel.PhotosViewModel
import com.example.photoprofile.ui.viewmodel.UserInfoViewModel
import com.example.photoprofile.ui.viewmodel.UserViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

object DIContainer {

    fun main(application: MainApplication) {
        startKoin {
            androidContext(application)
            modules(appModule)
        }
    }

    val appModule = module {
//        single<PexelsRepositoryImpl>() bind PexelsRepository::class
        single<PexelsRepository> {
            PexelsRepositoryImpl()
        }
        single<GetCuratedPhotosUseCase> {
            GetCuratedPhotosUseCase(
                repository = get()
            )
        }
        viewModel<PhotosViewModel> {
            PhotosViewModel(
                getCuratedPhotosUseCase = get()
            )
        }
        //
//        viewModel<PhotoDetailViewModel>()
        //
        single<ProfileDataBase> {
            ProfileDataBase(
                context = androidContext()
            )
        }
        single<UserRepository> {
            UserRepositoryImpl(
                db = get()
            )
        }
        single<GetUserUseCase> {
            GetUserUseCase(
                repository = get()
            )
        }
        viewModel<UserInfoViewModel> {
            UserInfoViewModel(
                getUserUseCase = get()
            )
        }
        //
        single<SaveUserUseCase> {
            SaveUserUseCase(
                repository = get()
            )
        }
        single<UpdateUserUseCase> {
            UpdateUserUseCase(
                repository = get()
            )
        }
        viewModel<UserViewModel> {
            UserViewModel(
                saveUserUseCase = get(),
                updateUserUseCase = get()
            )
        }
    }
}