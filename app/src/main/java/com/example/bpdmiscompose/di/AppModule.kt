package com.example.bpdmiscompose.di

import com.example.bpdmiscompose.repositories.AuthRepository
import com.example.bpdmiscompose.repositories.AuthRepositoryImpl
import com.example.bpdmiscompose.repositories.SetoranModalRepository
import com.example.bpdmiscompose.repositories.SetoranModalRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Provides
    fun provideFirebaseAuth() : FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideAuthRepository(impl : AuthRepositoryImpl) : AuthRepository = impl

    @Provides
    fun provideSetoranModalRepository(impl : SetoranModalRepositoryImpl) : SetoranModalRepository = impl

}