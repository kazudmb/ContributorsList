package com.dmb.contributorslist.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dmb.contributorslist.R
import com.dmb.contributorslist.databinding.ContributorsListItemBinding
import com.dmb.contributorslist.service.model.Contributors
import com.dmb.contributorslist.view.callback.ContributorsClickCallback

class ProjectAdapter(private val contributorsClickCallback: ContributorsClickCallback?) :
    RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>() {

    private var contributorsList: List<Contributors>? = null

    fun setProjectList(contributorsList: List<Contributors>) {

        if (this.contributorsList == null) {
            this.contributorsList = contributorsList
            notifyItemRangeInserted(0, contributorsList.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return requireNotNull(this@ProjectAdapter.contributorsList).size
                }

                override fun getNewListSize(): Int {
                    return contributorsList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val oldList = this@ProjectAdapter.contributorsList
                    return oldList?.get(oldItemPosition)?.id == contributorsList[newItemPosition].id
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val project = contributorsList[newItemPosition]
                    val old = contributorsList[oldItemPosition]
                    return project.id == old.id && project.gists_url == old.gists_url
                }
            })
            this.contributorsList = contributorsList
            result.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewtype: Int): ProjectViewHolder {
        val binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.contributors_list_item, parent,
            false) as ContributorsListItemBinding
        binding.callback = contributorsClickCallback
        return ProjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        holder.binding.contributors = contributorsList?.get(position)
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return contributorsList?.size ?: 0
    }

    open class ProjectViewHolder(val binding: ContributorsListItemBinding) : RecyclerView.ViewHolder(binding.root)
}