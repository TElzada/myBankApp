package com.geeks.mybankapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.geeks.mybankapp.data.model.Account
import com.geeks.mybankapp.databinding.ActivityMainBinding
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