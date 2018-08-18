package br.com.gabrielmarcos.git_consult.services

import android.content.Context
import br.com.gabrielmarcos.git_consult.models.GitPullModel
import br.com.gabrielmarcos.git_consult.services.helpers.Constants
import br.com.gabrielmarcos.git_consult.services.helpers.RetrofitHelper
import br.com.gabrielmarcos.git_consult.services.interfaces.GitPulInterface
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by Gabriel Marcos on 18/08/2018
 */
class GitPullService(context: Context): BaseService(context) {

    private val gitRepoService by lazy {
        RetrofitHelper.getRetrofit(context).create(GitPulInterface::class.java)
    }

    fun subscrible(owner: String,
                   repo: String,
                   successCallback: (response: ArrayList<GitPullModel>) -> Unit,
                   errorCallback: () -> Unit){
        observable = Observable.interval(Constants.RX_TIME_INTERVAL_IN_SECONDS, TimeUnit.MINUTES)
                .startWith(0)
                .subscribe {
                    gitRepoService.getPullRequest(owner, repo)
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