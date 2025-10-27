package com.geeks.mybankapp.ui.account

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.geeks.mybankapp.R

class AccountDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_details)

        val accountId = intent.getStringExtra("account_id")
        val tvInfo = findViewById<TextView>(R.id.tvAccountInfo)
        tvInfo.text = "Account Info\nID: $accountId"
    }
}
