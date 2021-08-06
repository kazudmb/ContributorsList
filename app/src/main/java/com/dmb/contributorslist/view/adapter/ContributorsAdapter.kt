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

class ContributorsAdapter(private val contributorsClickCallback: ContributorsClickCallback?) :
    RecyclerView.Adapter<ContributorsAdapter.contributorsViewHolder>() {

    private var contributorsList: List<Contributors>? = null

    fun setContributorsList(contributorsList: List<Contributors>) {

        if (this.contributorsList == null) {
            this.contributorsList = contributorsList
            notifyItemRangeInserted(0, contributorsList.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return requireNotNull(this@ContributorsAdapter.contributorsList).size
                }

                override fun getNewListSize(): Int {
                    return contributorsList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val oldList = this@ContributorsAdapter.contributorsList
                    return oldList?.get(oldItemPosition)?.id == contributorsList[newItemPosition].id
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val contributors = contributorsList[newItemPosition]
                    val old = contributorsList[oldItemPosition]
                    return contributors.id == old.id
                }
            })
            this.contributorsList = contributorsList
            result.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewtype: Int): contributorsViewHolder {
        val binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.contributors_list_item, parent,
            false) as ContributorsListItemBinding
        binding.callback = contributorsClickCallback
        return contributorsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: contributorsViewHolder, position: Int) {
        holder.binding.contributors = contributorsList?.get(position)
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return contributorsList?.size ?: 0
    }

    open class contributorsViewHolder(val binding: ContributorsListItemBinding) : RecyclerView.ViewHolder(binding.root)
}