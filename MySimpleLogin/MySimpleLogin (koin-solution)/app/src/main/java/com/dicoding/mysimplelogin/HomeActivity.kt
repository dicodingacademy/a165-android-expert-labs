package com.dicoding.mysimplelogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.ext.android.inject

class HomeActivity : AppCompatActivity() {

    val userRepository: UserRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

//        hapus kode berikut
//        val sesi = SessionManager(this)
//        userRepository = UserRepository.getInstance(sesi)

        tv_welcome.text = "Welcome ${userRepository.getUser()}"

        btn_logout.setOnClickListener {
            userRepository.logoutUser()
            moveToMainActivity()
        }
    }

    private fun moveToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
