package com.dicoding.mysimplelogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dicoding.mysimplelogin.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var userRepository: UserRepository
    @Inject
    lateinit var userRepository2: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        hapus kode berikut
//        val sesi = SessionManager(this)
//        userRepository = UserRepository.getInstance(sesi)

        userRepository.checkInstance()
        userRepository2.checkInstance()

        if (userRepository.isUserLogin()) {
            Log.d("TAG", "onCreate: "+ userRepository.isUserLogin())
            moveToHomeActivity()
        }

        binding.btnLogin.setOnClickListener {
            saveSession()
        }
    }

    private fun saveSession() {
        userRepository.loginUser(binding.edUsername.text.toString())
        moveToHomeActivity()
    }

    private fun moveToHomeActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}
