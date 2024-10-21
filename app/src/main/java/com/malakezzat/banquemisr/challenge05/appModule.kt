package com.malakezzat.banquemisr.challenge05

import androidx.room.Room
import com.malakezzat.banquemisr.challenge05.data.local.AppDatabase
import com.malakezzat.banquemisr.challenge05.data.local.MoviesLocalDataSource
import com.malakezzat.banquemisr.challenge05.data.local.MoviesLocalDataSourceImpl
import com.malakezzat.banquemisr.challenge05.data.remote.MovieApiService
import com.malakezzat.banquemisr.challenge05.data.remote.MoviesRemoteDataSource
import com.malakezzat.banquemisr.challenge05.data.remote.MoviesRemoteDataSourceImpl
import com.malakezzat.banquemisr.challenge05.data.remote.RetrofitHelper
import com.malakezzat.banquemisr.challenge05.data.repo.MoviesRepository
import com.malakezzat.banquemisr.challenge05.data.repo.MoviesRepositoryImpl
import com.malakezzat.banquemisr.challenge05.ui.details.viewmodel.DetailsScreenViewModel
import com.malakezzat.banquemisr.challenge05.ui.lists.nowplaying.viewmodel.NowPlayingScreenViewModel
import com.malakezzat.banquemisr.challenge05.ui.lists.popular.viewmodel.PopularScreenViewModel
import com.malakezzat.banquemisr.challenge05.ui.lists.upcoming.viewmodel.UpcomingScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

import retrofit2.Retrofit

val appModule = module {
    single { RetrofitHelper.getInstance() }

    single { get<Retrofit>().create(MovieApiService::class.java) }

    single { Room.databaseBuilder(get(), AppDatabase::class.java, "movie_database").build() }

    single<MoviesRemoteDataSource> { MoviesRemoteDataSourceImpl(get()) }
    single<MoviesLocalDataSource> { MoviesLocalDataSourceImpl(get<AppDatabase>()) }
    single<MoviesRepository> { MoviesRepositoryImpl(get(), get()) }

    viewModel { NowPlayingScreenViewModel(get()) }
    viewModel { PopularScreenViewModel(get()) }
    viewModel { UpcomingScreenViewModel(get()) }
    viewModel { DetailsScreenViewModel(get()) }
}