package com.wanderlab.wanderlustcompanion.ui.login


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.wanderlab.wanderlustcompanion.DBHelper
import com.wanderlab.wanderlustcompanion.MainActivity
import com.wanderlab.wanderlustcompanion.ProfileActivity
import com.wanderlab.wanderlustcompanion.R
import com.wanderlab.wanderlustcompanion.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = binding.username
        val password = binding.password
        val login = binding.btSignin
        val loading = binding.loading
        val dbHelper = DBHelper(this, "wanderlust.db", null, 1)

        // Handle Signup first
        val signup = findViewById<View>(R.id.bt_signup) as Button
        signup.setOnClickListener {
            // code to be executed when button is clicked
            val etUserName = findViewById<View>(R.id.username) as EditText
            val strUserName = etUserName.text.toString()
            if(TextUtils.isEmpty(strUserName)) {
                etUserName.error = "Username Cannot be Empty";
                return@setOnClickListener;
            }
            val etpassword = findViewById<View>(R.id.password) as EditText
            val stpassword = etpassword.text.toString()
            if(TextUtils.isEmpty(stpassword)) {
                etpassword.error = "Password Cannot be Empty";
                return@setOnClickListener;
            }

            if (dbHelper.user_IsUsernameFree(strUserName)) {
                dbHelper.user_Insert(strUserName, stpassword)
                val intent = Intent(this, ProfileActivity::class.java)
                intent.putExtra("login_name", strUserName);
                startActivity(intent)
                finish()
            }
            else {
                Toast.makeText(applicationContext, "Username is not Free", Toast.LENGTH_SHORT).show()
            }

        }


        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            if (loading != null) {
                loading.visibility = View.GONE
            }
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            finish()
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            dbHelper,
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

            login.setOnClickListener {
                if (loading != null) {
                    loading.visibility = View.VISIBLE
                }
                loginViewModel.login(dbHelper,username.text.toString(), password.text.toString())
            }
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val displayName = model.displayName
        // TODO : initiate successful logged in experience
        /*
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()*/
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("login_name", displayName);
        startActivity(intent)
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}