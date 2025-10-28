package com.geeks.mybankapp.ui.account

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.geeks.mybankapp.data.model.Account
import com.geeks.mybankapp.databinding.ActivityAccountDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import com.geeks.mybankapp.ui.viewModel.AccountDetailsViewModel

@AndroidEntryPoint
class AccountDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccountDetailsBinding
    private val viewModel: AccountDetailsViewModel by viewModels()
    private var accountId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        accountId = intent.getStringExtra("account_id")
        if (accountId == null) {
            Toast.makeText(this, "Не передан ID счета", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        viewModel.account.observe(this) { account ->
            account?.let { displayAccount(it) }
        }

        viewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }

        accountId?.let { viewModel.loadAccountDetails(it) }

        binding.btnEdit.setOnClickListener {
            val account = viewModel.account.value
            if (account != null) {
                val updatedAccount = Account(
                    id = account.id,
                    name = binding.etName.text.toString(),
                    balance = binding.etBalance.text.toString().toIntOrNull() ?: 0,
                    currency = binding.etCurrency.text.toString(),
                    isActive = account.isActive
                )
                viewModel.updateAccountFully(updatedAccount)
            }
        }

        binding.btnDelete.setOnClickListener {
            accountId?.let { id ->
                viewModel.deleteAccount(id)
                finish()
            }
        }
    }

    private fun displayAccount(account: Account) = with(binding) {
        etName.setText(account.name)
        etBalance.setText(account.balance?.toString() ?: "0")
        etCurrency.setText(account.currency)
        tvAccountId.text = "ID: ${account.id}"
        tvAccountActive.text = if (account.isActive == true) "Active" else "Inactive"
    }
}
