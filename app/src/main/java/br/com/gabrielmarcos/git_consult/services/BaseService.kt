package br.com.gabrielmarcos.git_consult.services

import android.content.Context
import io.reactivex.disposables.Disposable

/**
 * Created by Gabriel Marcos on 17/08/2018
 */
open class BaseService(val context: Context?) {

    var observable: Disposable? = null

    fun unsubscribe() {
        observable?.dispose()
    }
}