package com.geeks.mybankapp.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geeks.mybankapp.data.model.Account
import com.geeks.mybankapp.data.model.AccountState
import com.geeks.mybankapp.data.network.AccountsApi
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(private val accountsApi: AccountsApi): ViewModel() {

    private val _accounts = MutableLiveData<List<Account>>()
    val accounts: LiveData<List<Account>> = _accounts

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun loadAccounts() {
        accountsApi.getAccounts()
            .handleAccountResponse(
                onSuccess = { _accounts.value = it },
                onError = { _errorMessage.value = it }
            )
    }

    fun addAccount(account: Account) {
        accountsApi.addAccount(account)
            .handleAccountResponse(onError = { _errorMessage.value = it })
    }

    fun updateAccountFully(updatedAccount: Account) {
        updatedAccount.id?.let {
            accountsApi.updateAccountFully(it, updatedAccount)
                .handleAccountResponse(onError = { _errorMessage.value = it })
        }
    }

    fun updateAccountPartially(id: String, isChecked: Boolean) {
        accountsApi
            .updateAccountPartially(id, AccountState(isChecked))
            .handleAccountResponse(onError = { _errorMessage.value = it })
    }

    fun deleteAccount(id: String) {
        accountsApi.deleteAccount(id)
            .handleAccountResponse(onError = { _errorMessage.value = it })
    }

    private fun <T> Call<T>?.handleAccountResponse(
        onSuccess: (T) -> Unit = { loadAccounts() },
        onError: (String) -> Unit = {}
    ) {
        this?.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val result = response.body()
                if (result != null && response.isSuccessful) {
                    onSuccess(result)
                } else {
                    onError("Ошибка: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                onError("Ошибка сети: ${t.message}")
            }
        })
    }
}
