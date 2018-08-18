package br.com.gabrielmarcos.git_consult.views.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.gabrielmarcos.git_consult.R
import br.com.gabrielmarcos.git_consult.models.GitDetailModel
import br.com.gabrielmarcos.git_consult.utils.PicassoService
import kotlinx.android.synthetic.main.adapter_cards_repo.view.*

/**
 * Created by Gabriel Marcos on 18/08/2018
 */
class RepoCardsAdapter(private val context: Context,
                       private val items: ArrayList<GitDetailModel>,
                       private val listener: RepoCardsAdapterListener): RecyclerView.Adapter<RepoCardsAdapter.ViewHolder>() {

    interface RepoCardsAdapterListener {
        fun onRepoClicked(repo: String, user: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_cards_repo, parent, false)
        return ViewHolder(view, listener, context)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    class ViewHolder(itemView: View, val listener: RepoCardsAdapterListener, val context: Context): RecyclerView.ViewHolder(itemView){
        fun bindView(item: GitDetailModel) {

            val picassoService = PicassoService(context)

            itemView.repoCardTitle.text = item.name
            itemView.repoCardDescription.text = item.description
            itemView.repoCardForkCount.text = item.forks_count.toString()
            itemView.repoCardStarCount.text = item.stargazers_count.toString()
            itemView.repoCardName.text = item.owner.login
            itemView.repoCardFullName.text = item.owner.login
            picassoService.loadImage(item.owner.avatar_url, itemView.repoCardPhoto)

            itemView.repoCard.setOnClickListener {
                listener.onRepoClicked(item.name, item.owner.login)
            }
        }
    }
}