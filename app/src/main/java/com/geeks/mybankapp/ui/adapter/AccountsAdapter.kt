package com.geeks.mybankapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geeks.mybankapp.data.model.Account
import com.geeks.mybankapp.databinding.ItemAccountBinding
import kotlin.text.clear

class AccountsAdapter: RecyclerView.Adapter<AccountsAdapter.AccountViewHolder>() {
    private val items = arrayListOf<Account>()
    fun submitList(data: List<Account>){
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()

    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AccountsAdapter.AccountViewHolder {
        val binding = ItemAccountBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AccountViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AccountsAdapter.AccountViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
    inner class AccountViewHolder(private val binding: ItemAccountBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(account: Account) = with(binding){
            tvName.text = account.name
            val text = "${account.balance} ${account.currency}"
            tvBalance.text = text

        }
    }

}