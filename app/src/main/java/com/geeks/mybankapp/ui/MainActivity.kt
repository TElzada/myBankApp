package com.geeks.mybankapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.geeks.mybankapp.data.model.Account
import com.geeks.mybankapp.databinding.ActivityMainBinding
import com.geeks.mybankapp.databinding.DialogAddBinding
import com.geeks.mybankapp.ui.account.AccountDetailsActivity
import com.geeks.mybankapp.ui.adapter.AccountsAdapter
import com.geeks.mybankapp.ui.viewModel.AccountViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: AccountsAdapter
    private val viewModel: AccountViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
        subscribeToLiveData()

        binding.btnAdd.setOnClickListener { showAddDialog() }
    }

    private fun subscribeToLiveData() {
        viewModel.accounts.observe(this) { adapter.submitList(it) }
        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }

    private fun showAddDialog() {
        val dialogBinding = DialogAddBinding.inflate(LayoutInflater.from(this))
        AlertDialog.Builder(this)
            .setTitle("Добавление нового счёта")
            .setView(dialogBinding.root)
            .setPositiveButton("Добавить") { _, _ ->
                val account = Account(
                    name = dialogBinding.etName.text.toString(),
                    balance = dialogBinding.etBalance.text.toString().toIntOrNull() ?: 0,
                    currency = dialogBinding.etCurrency.text.toString()
                )
                viewModel.addAccount(account)
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    private fun initAdapter() {
        adapter = AccountsAdapter(
            onItemClick = { id ->
                id?.let {
                    val intent = Intent(this@MainActivity, AccountDetailsActivity::class.java)
                    intent.putExtra("account_id", it)
                    startActivity(intent)
                }
            }
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadAccounts()
    }
}
