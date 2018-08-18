package br.com.gabrielmarcos.git_consult.services

import android.content.Context
import br.com.gabrielmarcos.git_consult.models.GitUserModel
import br.com.gabrielmarcos.git_consult.services.helpers.RetrofitHelper
import br.com.gabrielmarcos.git_consult.services.interfaces.GitUserInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Gabriel Marcos on 18/08/2018
 */
class GitUserService(context: Context): BaseService(context) {

    private val gitRepoService by lazy {
        RetrofitHelper.getRetrofit(context).create(GitUserInterface::class.java)
    }

    fun getUser(user: String,
                   successCallback: (response: GitUserModel) -> Unit,
                   errorCallback: () -> Unit){

        observable = gitRepoService.getUserName(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { response ->
                            successCallback(response)
                        },
                        { _ ->
                            errorCallback() }
                )
    }
}