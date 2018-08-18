package br.com.gabrielmarcos.git_consult.services.interfaces

import br.com.gabrielmarcos.git_consult.BuildConfig
import br.com.gabrielmarcos.git_consult.models.GitPullModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Gabriel Marcos on 18/08/2018
 */
interface GitPulInterface {
    @GET(BuildConfig.URL_PR)
    fun getPullRequest(@Path("owner") owner: String,
                       @Path("repo") repo: String ): Observable<ArrayList<GitPullModel>>
}