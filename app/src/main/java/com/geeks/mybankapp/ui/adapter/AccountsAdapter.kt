package com.geeks.mybankapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geeks.mybankapp.data.model.Account
import com.geeks.mybankapp.databinding.ItemAccountBinding

class AccountsAdapter(
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<AccountsAdapter.AccountViewHolder>() {

    private val items = arrayListOf<Account>()

    fun submitList(data: List<Account>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val binding = ItemAccountBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AccountViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class AccountViewHolder(private val binding: ItemAccountBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(account: Account) = with(binding) {
            tvName.text = account.name
            tvBalance.text = "${account.balance} ${account.currency}"

            root.setOnClickListener {
                account.id?.let { onItemClick(it) }
            }
        }
    }
}
