package com.example.locationcontrol.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.locationcontrol.R
import com.example.locationcontrol.data.LoginDataSource
import com.example.locationcontrol.data.LoginRepository
import com.example.locationcontrol.data.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

   lateinit private var loginViewModel : LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginViewModel  = LoginViewModel(loginRepository = LoginRepository(dataSource = LoginDataSource(),context = this))

        setContentView(R.layout.activity_login)

        val user = LoginRepository(dataSource = LoginDataSource(), context = this).user

        if (user != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        loginViewModel.loginResult.observe(this, Observer {
            if (it){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

        loginViewModel.errorMessage.observe(this, Observer {
            Toast.makeText(this,it,Toast.LENGTH_LONG).show()
        })

        val watcher = object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                login_button.isEnabled = !login_et.text.isNullOrEmpty() && !password_et.text.isNullOrEmpty()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        }

        login_et.addTextChangedListener(watcher)
        password_et.addTextChangedListener(watcher)

    }

    fun onLoginClick(view: View){
        loginViewModel.login(login = login_et.text.toString(), password = password_et.text.toString())

    }


}