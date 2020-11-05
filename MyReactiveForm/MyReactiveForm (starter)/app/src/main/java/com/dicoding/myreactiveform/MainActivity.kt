package com.dicoding.myreactiveform

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.dicoding.myreactiveform.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var emailValid = false
    private var passwordValid = false
    private var passwordConfirmationValid = false

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        validateButton()

        binding.edEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateEmail()
            }
        })

        binding.edPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validatePassword()
            }
        })

        binding.edConfirmPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validatePasswordConfirmation()
            }
        })
    }

    fun validateEmail() {
        // jika password tidak valid tampilkan peringatan
        val input = binding.edEmail.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(input).matches()) {
            emailValid = false
            showEmailExistAlert(true)
        } else {
            emailValid = true
            showEmailExistAlert(false)
        }
        validateButton()
    }

    fun validatePassword() {
        // jika password < 6 karakter tampilkan peringatan
        val input = binding.edPassword.text.toString()
        if (input.length < 6) {
            passwordValid = false
            showPasswordMinimalAlert(true)
        } else {
            passwordValid = true
            showPasswordMinimalAlert(false)
        }
        validateButton()
    }

    fun validatePasswordConfirmation() {
        // jika konfirmasi password tidak sesuai tampilkan peringatan
        val input = binding.edConfirmPassword.text.toString()
        if (input != binding.edPassword.text.toString()) {
            passwordConfirmationValid = false
            showPasswordConfirmationAlert(true)
        } else {
            passwordConfirmationValid = true
            showPasswordConfirmationAlert(false)
        }
        validateButton()
    }

    private fun validateButton() {
        // jika semua field sudah terisi, enable button submit
        if (emailValid && passwordValid && passwordConfirmationValid) {
            binding.btnRegister.isEnabled = true
            binding.btnRegister.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_500))
        } else {
            binding.btnRegister.isEnabled = false
            binding.btnRegister.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray))
        }
    }

    private fun showEmailExistAlert(isNotValid: Boolean) {
        binding.edEmail.error = if (isNotValid) getString(R.string.email_not_valid) else null
    }

    private fun showPasswordMinimalAlert(isNotValid: Boolean) {
        binding.edPassword.error = if (isNotValid) getString(R.string.password_not_valid) else null
    }

    private fun showPasswordConfirmationAlert(isNotValid: Boolean) {
        binding.edConfirmPassword.error = if (isNotValid) getString(R.string.password_not_same) else null
    }
}
