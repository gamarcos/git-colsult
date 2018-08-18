package br.com.gabrielmarcos.git_consult.services

import android.content.Context
import br.com.gabrielmarcos.git_consult.models.GitModel
import br.com.gabrielmarcos.git_consult.services.helpers.Constants
import br.com.gabrielmarcos.git_consult.services.helpers.RetrofitHelper
import br.com.gabrielmarcos.git_consult.services.interfaces.GitRepoInterface
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by Gabriel Marcos on 17/08/2018
 */
class GitRepoService(context: Context): BaseService(context) {

    private val gitRepoService by lazy {
        RetrofitHelper.getRetrofit(context).create(GitRepoInterface::class.java)
    }

    fun subscrible(page: Int,
                   successCallback: (response: GitModel) -> Unit,
                   errorCallback: () -> Unit){
        observable = Observable.interval(Constants.RX_TIME_INTERVAL_IN_SECONDS, TimeUnit.MINUTES)
                .startWith(0)
                .subscribe {
                    gitRepoService.getGitRepo("language:Java", "stars", page)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    { response ->
                                        successCallback(response) },
                                    { _ ->
                                        errorCallback() }
                            )
                }
    }
}