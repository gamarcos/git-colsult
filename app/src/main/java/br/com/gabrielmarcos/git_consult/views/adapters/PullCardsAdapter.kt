package br.com.gabrielmarcos.git_consult.views.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.gabrielmarcos.git_consult.R
import br.com.gabrielmarcos.git_consult.models.GitPullModel
import br.com.gabrielmarcos.git_consult.utils.PicassoService
import kotlinx.android.synthetic.main.adapter_card_pr.view.*

/**
 * Created by Gabriel Marcos on 18/08/2018
 */
class PullCardsAdapter(val context: Context,
                       val listener: PullCardsAdapterListener,
                       val pullRequests: ArrayList<GitPullModel>): RecyclerView.Adapter<PullCardsAdapter.ViewHolder>() {

    interface PullCardsAdapterListener {
        fun onPullRequestClicked(url: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_card_pr, parent, false)
        return PullCardsAdapter.ViewHolder(view, listener, context)
    }

    override fun getItemCount(): Int {
        return pullRequests.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(pullRequests[position])
    }

    class ViewHolder(itemView: View, val listener: PullCardsAdapterListener, val context: Context): RecyclerView.ViewHolder(itemView){

        fun bindView(gitPullModel: GitPullModel) {

            val picassoService = PicassoService(context)

            itemView.pullCardTitle.text = gitPullModel.title
            itemView.pullCardDescription.text = gitPullModel.body
            itemView.pullCardName.text = gitPullModel.user.login
            itemView.pullCardFullName.text = gitPullModel.user.login

            picassoService.loadImage(gitPullModel.user.avatar_url, itemView.pullCardPhoto)

            itemView.pullCard.setOnClickListener {
                listener.onPullRequestClicked(gitPullModel.html_url)
            }
        }
    }
}