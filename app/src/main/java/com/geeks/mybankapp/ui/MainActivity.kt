package com.geeks.mybankapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.geeks.mybankapp.data.model.Account
import com.geeks.mybankapp.databinding.ActivityMainBinding
import com.geeks.mybankapp.databinding.DialogAddBinding
import com.geeks.mybankapp.domain.presenter.AccountContracts
import com.geeks.mybankapp.domain.presenter.AccountPresenter
import com.geeks.mybankapp.ui.adapter.AccountsAdapter

class MainActivity : AppCompatActivity() ,  AccountContracts.View{
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: AccountsAdapter
    private lateinit var presenter: AccountPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAdapter()
        presenter = AccountPresenter( this)

        binding.btnAdd.setOnClickListener {
            showAddDialog()
        }
    }

    private fun showAddDialog(){
        val binding = DialogAddBinding.inflate(LayoutInflater.from(this))
        with(binding){
            AlertDialog.Builder(this@MainActivity)
                .setTitle("Добавление нового счёта")
                .setView(binding.root)
                .setPositiveButton("Добавить"){_,_->
                    val account = Account(
                        name = etName.text.toString(),
                        balance = etBalance.text.toString().toInt(),
                        currency = etCurrency.text.toString()
                    )
                    presenter.addAccount(account)

                }
                .show()

        }
    }

    override fun onResume() {
        super.onResume()
        presenter.loadAccounts()
    }
    private fun initAdapter()= with(binding){
        adapter = AccountsAdapter()
        recyclerView.layoutManager= LinearLayoutManager(this@MainActivity)
        recyclerView.adapter = adapter
    }

    override fun showAccounts(accountList:List<Account>) {
        adapter.submitList(accountList)
    }


}