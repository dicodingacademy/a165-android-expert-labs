package com.dicoding.mysimplelogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    val userRepository: UserRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        hapus kode berikut
//        val sesi = SessionManager(this)
//        userRepository = UserRepository.getInstance(sesi)

        if (userRepository.isUserLogin()) {
            moveToHomeActivity()
        }

        btn_login.setOnClickListener {
            saveSession()
        }
    }

    private fun saveSession() {
        userRepository.loginUser(ed_username.text.toString())
        moveToHomeActivity()
    }

    private fun moveToHomeActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}
