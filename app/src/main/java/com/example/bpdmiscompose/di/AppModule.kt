package com.example.bpdmiscompose.di

import com.example.bpdmiscompose.data.AuthRepository
import com.example.bpdmiscompose.data.AuthRepositoryImpl
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

}