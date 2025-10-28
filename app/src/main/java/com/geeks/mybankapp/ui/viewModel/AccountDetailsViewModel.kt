package com.geeks.mybankapp.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geeks.mybankapp.data.model.Account
import com.geeks.mybankapp.data.model.AccountState
import com.geeks.mybankapp.data.network.AccountDetailsApi
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AccountDetailsViewModel @Inject constructor(
    private val api: AccountDetailsApi
) : ViewModel() {

    private val _account = MutableLiveData<Account>()
    val account: LiveData<Account> = _account

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun loadAccountDetails(id: String) {
        api.getAccountById(id).enqueue(object : Callback<Account> {
            override fun onResponse(call: Call<Account>, response: Response<Account>) {
                if (response.isSuccessful) {
                    _account.value = response.body()
                } else {
                    _errorMessage.value = "Ошибка загрузки: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<Account>, t: Throwable) {
                _errorMessage.value = "Ошибка сети: ${t.message}"
            }
        })
    }

    fun updateAccountFully(account: Account) {
        account.id?.let { id ->
            api.updateAccountFully(id, account).enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful) loadAccountDetails(id)
                    else _errorMessage.value = "Ошибка обновления: ${response.code()}"
                }
                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    _errorMessage.value = "Ошибка сети: ${t.message}"
                }
            })
        }
    }
    fun deleteAccount(id: String) {
        api.deleteAccount(id).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) _account.value = null
                else _errorMessage.value = "Ошибка удаления: ${response.code()}"
            }
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                _errorMessage.value = "Ошибка сети: ${t.message}"
            }
        })
    }


}