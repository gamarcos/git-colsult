package br.com.gabrielmarcos.git_consult.views

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.webkit.WebViewClient
import br.com.gabrielmarcos.git_consult.R
import kotlinx.android.synthetic.main.activity_pull_request.*
import kotlinx.android.synthetic.main.activity_web_view.*

/**
 * Created by Gabriel Marcos on 18/08/2018
 */
class WebViewActvitiy: AppCompatActivity() {

    companion object {
        val URL_REPO = "URL_REPO"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val url = intent.extras.getString(URL_REPO)

        webView.webViewClient = WebViewClient()
        webView.settings.setAppCacheEnabled (true)
        webView.settings.javaScriptEnabled = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.allowFileAccess = true
        webView.settings.allowContentAccess = true

        webView.loadUrl(url)
        setupView()
    }

    private fun setupView() {
        iconBackWeb.setOnClickListener { finish() }
    }
}