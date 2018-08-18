package br.com.gabrielmarcos.git_consult.services.interfaces

import br.com.gabrielmarcos.git_consult.BuildConfig
import br.com.gabrielmarcos.git_consult.models.GitModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Gabriel Marcos on 17/08/2018
 */
interface GitRepoInterface {
    @GET(BuildConfig.URL_SEARCH)
    fun getGitRepo(@Query("q") language: String,
                   @Query("sort") sort: String,
                   @Query("page") page: Int): Observable<GitModel>
}