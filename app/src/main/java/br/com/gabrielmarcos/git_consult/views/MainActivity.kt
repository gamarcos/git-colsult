package br.com.gabrielmarcos.git_consult.views

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import br.com.gabrielmarcos.git_consult.R
import br.com.gabrielmarcos.git_consult.models.GitDetailModel
import br.com.gabrielmarcos.git_consult.models.GitModel
import br.com.gabrielmarcos.git_consult.models.GitUserModel
import br.com.gabrielmarcos.git_consult.services.GitRepoService
import br.com.gabrielmarcos.git_consult.services.GitUserService
import br.com.gabrielmarcos.git_consult.views.adapters.RepoCardsAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_pull_request.*
import kotlinx.android.synthetic.main.layout_view_load.*

class MainActivity : AppCompatActivity(), RepoCardsAdapter.RepoCardsAdapterListener {

    private lateinit var gitRepoService: GitRepoService
    private lateinit var gitUserService: GitUserService
    private lateinit var layoutManager: LinearLayoutManager

    private var reposList = ArrayList<GitDetailModel>()
    private var usersList = ArrayList<GitUserModel>()

    private var countPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gitRepoService = GitRepoService(this)
        gitUserService = GitUserService(this)

        setupView()
        requestRepositories()
        setupInfiniteScroll()
    }

    override fun onStop() {
        super.onStop()
        gitRepoService.unsubscribe()
    }

    override fun onRestart() {
        super.onRestart()
        requestRepositories()
    }

    private fun setupView() {
        setSupportActionBar(repotoolbar)
        repotoolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.toolbar_color))
        supportActionBar?.title = getString(R.string.repositories)

        repoSwipeRefresh.setOnRefreshListener {
            requestRepositories()
        }
    }

    private fun setupInfiniteScroll() {
        repositoriesList.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalItems = layoutManager.itemCount
                val currentItems = layoutManager.childCount
                val scrollOutItems = layoutManager.findFirstVisibleItemPosition()

                if ((currentItems + scrollOutItems) >= totalItems) {
                    countPage++
                    controllerProgress(true)
                    requestRepositories()
                }
            }
        })
    }

    private fun setupRepoCards() {
        repositoriesList.adapter = RepoCardsAdapter(this, reposList, this)
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        repositoriesList.layoutManager = layoutManager
    }

    private fun notifyAdapter(gitModel: GitModel) {
        gitModel.items.map { reposList.add(it) }

        if (repositoriesList.adapter != null) {
            repositoriesList.adapter.notifyDataSetChanged()
        } else {
            setupRepoCards()
        }
    }

    private fun requestRepositories() {
        gitRepoService.unsubscribe()

        gitRepoService.subscrible(countPage,{response ->
            notifyAdapter(response)
            repoSwipeRefresh.isRefreshing = false
            controllerProgress(false)
        },{
            Toast.makeText(this, getString(R.string.errorRequest), Toast.LENGTH_LONG).show()
        })
    }

   //This function can not be used by the number of requests it demands
    private fun requestUserName(gitModel: GitModel) {
        usersList.clear()

        for (user in gitModel.items) {
            gitUserService.getUser(user.owner.login, {response ->
                usersList.add(response)
            },{})
        }
        print(usersList)
    }

    private  fun controllerProgress(show: Boolean) {
        if (show) {
            repoProgress.visibility = View.VISIBLE
        } else {
            repoProgress.visibility = View.GONE
        }
    }

    override fun onRepoClicked(repos: String, user: String) {
        val intent = Intent(this, PullRequestActivity::class.java)
        intent.putExtra(PullRequestActivity.REPO_NAME, repos)
        intent.putExtra(PullRequestActivity.USER_NAME, user)
        startActivity(intent)
    }
}
