package br.com.gabrielmarcos.git_consult.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import br.com.gabrielmarcos.git_consult.R
import br.com.gabrielmarcos.git_consult.models.GitPullModel
import br.com.gabrielmarcos.git_consult.services.GitPullService
import br.com.gabrielmarcos.git_consult.views.adapters.PullCardsAdapter
import kotlinx.android.synthetic.main.activity_pull_request.*
import kotlinx.android.synthetic.main.layout_view_load.*

class PullRequestActivity : AppCompatActivity(), PullCardsAdapter.PullCardsAdapterListener {

    companion object {
        val REPO_NAME = "REPO_NAME"
        val USER_NAME = "USER_NAME"
    }

    private var user = ""
    private var repo = ""

    private lateinit var gitPullService: GitPullService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pull_request)

        gitPullService = GitPullService(this)

        repo = intent.extras.getString(REPO_NAME)
        user = intent.extras.getString(USER_NAME)

        setupView()
        requestPullRequest()
    }

    override fun onStop() {
        super.onStop()
        gitPullService.unsubscribe()
    }

    override fun onRestart() {
        super.onRestart()
        requestPullRequest()
    }

    private fun setupView() {
        iconBack.setOnClickListener { finish() }
        pullSwipeRefresh.setOnRefreshListener{ requestPullRequest() }
    }

    private fun setupPullCards(gitPullModel: ArrayList<GitPullModel>) {
        pullRequestList.adapter = PullCardsAdapter(this,  this, gitPullModel)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        pullRequestList.layoutManager = layoutManager
    }

    private fun requestPullRequest() {
        gitPullService.unsubscribe()

        gitPullService.subscrible(user, repo, {response ->
            setupPullCards(response)
            controllerProgress(false)
            pullSwipeRefresh.isRefreshing = false
        },{
            Toast.makeText(this, getString(R.string.errorRequest), Toast.LENGTH_LONG).show()
        })
    }

    private  fun controllerProgress(show: Boolean) {
        if (show) {
            repoProgress.visibility = View.VISIBLE
        } else {
            repoProgress.visibility = View.GONE
        }
    }

    override fun onPullRequestClicked(url: String) {
        val intent = Intent(this, WebViewActvitiy::class.java)
        intent.putExtra(WebViewActvitiy.URL_REPO, url)
        startActivity(intent)
    }
}
