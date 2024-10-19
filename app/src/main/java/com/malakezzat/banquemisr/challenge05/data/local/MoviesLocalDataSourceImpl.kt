package com.malakezzat.banquemisr.challenge05.data.local

class MoviesLocalDataSourceImpl(
    private val appDatabase: AppDatabase
) : MoviesLocalDataSource
    //, IMoviesLocalDataSource testing
{

    private var movieDao : MovieDao = appDatabase.movieDao
    private var movieDetailsDao : MovieDetailsDao = appDatabase.movieDetailsDao
    override suspend fun insertMovie(movieDB: MovieDB) {
        movieDao.insertMovie(movieDB)
    }

    override suspend fun insertMovies(movieDBList: List<MovieDB>) {
        movieDao.insertMovies(movieDBList)
    }

    override suspend fun getNowPlayingMovies(): List<MovieDB>? {
        return movieDao.getNowPlayingMovies()
    }

    override suspend fun getPopularMovies(): List<MovieDB>? {
        return movieDao.getNowPlayingMovies()
    }

    override suspend fun getUpcomingMovies(): List<MovieDB>? {
        return movieDao.getNowPlayingMovies()
    }

    override suspend fun insertMovieDetails(movieDetailsDB: MovieDetailsDB) {
        return movieDetailsDao.insertMovieDetails(movieDetailsDB)
    }

    override suspend fun getMovieDetailsById(id: Int): MovieDetailsDB? {
        return movieDetailsDao.getMovieDetailsById(id)
    }

}