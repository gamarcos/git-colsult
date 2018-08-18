package br.com.gabrielmarcos.git_consult.utils

import android.content.Context
import android.widget.ImageView
import com.squareup.picasso.Picasso

/**
 * Created by Gabriel Marcos on 18/08/2018
 */
class PicassoService(val context: Context) {
    fun loadImage(url: String?, view: ImageView) {
        Picasso.with(context).load(url).fit().centerCrop().into(view)
    }
}