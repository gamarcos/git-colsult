package br.com.gabrielmarcos.git_consult.services.interfaces

import br.com.gabrielmarcos.git_consult.BuildConfig
import br.com.gabrielmarcos.git_consult.models.GitUserModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Gabriel Marcos on 18/08/2018
 */
interface GitUserInterface {
    @GET(BuildConfig.URL_USER)
    fun getUserName(@Path("user") user:String): Observable<GitUserModel>
}