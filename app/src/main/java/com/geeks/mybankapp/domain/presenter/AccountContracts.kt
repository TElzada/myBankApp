package com.geeks.mybankapp.domain.presenter

import com.geeks.mybankapp.data.model.Account

interface AccountContracts {
    interface View{
        fun showAccounts(accountList:List<Account>)
    }
    interface Presenter{
        fun loadAccounts()
    }
}