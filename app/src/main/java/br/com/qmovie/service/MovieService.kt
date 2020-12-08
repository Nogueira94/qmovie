package br.com.qmovie.service

import br.com.qmovie.BuildConfig
import br.com.qmovie.domain.CreditResult
import br.com.qmovie.domain.Filme
import br.com.qmovie.domain.SearchResult
import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/{id}")
    suspend fun getMovie(
        @Path("id") id: Int,
        @Query("api_key") api_key: String = BuildConfig.API_KEY
    ) : Filme

    @GET("search/movie")
    suspend fun getMovies(
        @Query("query") query: String,
        @Query("api_key") api_key: String = BuildConfig.API_KEY,
        @Query("language") language: String = "pt-BR",
        @Query("include_adult") include_adult: Boolean = false,
        @Query("page") page: Int = 1
    ) : SearchResult<Filme>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") api_key: String = BuildConfig.API_KEY,
        @Query("language") language: String = "pt-BR",
        @Query("page") page: Int = 1
    ) : SearchResult<Filme>

    @GET("movie/{id}/credits")
    suspend fun getCredits(
        @Path("id") id: Int,
        @Query("api_key") api_key: String = BuildConfig.API_KEY
    ) : CreditResult

    @GET("movie/upcoming")
    suspend fun getUpcoming(
        @Query("api_key") api_key: String = BuildConfig.API_KEY,
        @Query("language") language: String = "pt-BR",
        @Query("region") region: String = "br"
    ) : SearchResult<Filme>
}