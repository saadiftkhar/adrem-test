package com.adremtest.android.nytimes.di

import android.content.Context
import android.content.res.Resources
import com.adremtest.android.nytimes.data.remote.NyApiInterface
import com.adremtest.android.nytimes.data.repository.NyRepositoryImpl
import com.adremtest.android.nytimes.domain.repository.NyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class NyTimesModule {
    @Provides
    @ViewModelScoped
    fun provideAuthApi(retrofit: Retrofit): NyApiInterface =
        retrofit.create(NyApiInterface::class.java)

    @Provides
    @ViewModelScoped
    fun provideAuthRepo(
        nyApiInterface: NyApiInterface,
        resources: Resources,
        @ApplicationContext context: Context,
    ): NyRepository =
        NyRepositoryImpl(nyApiInterface, resources, context)

}