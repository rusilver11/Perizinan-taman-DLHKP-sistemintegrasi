package com.example.login

import android.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class Activity_login : AppCompatActivity() {

     lateinit var etName: EditText
    internal lateinit var etPassword: EditText
    internal lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        if (SharedPrefManager.getInstance(this).isLoggedIn) {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }

        progressBar = findViewById(R.id.progressBar)
        etName = findViewById(R.id.etUserName)
        etPassword = findViewById(R.id.etUserPassword)

        //calling the method userLogin() for login the user
        btnLogin.setOnClickListener(View.OnClickListener {
            userLogin()
        })

        //if user presses on textview it call the activity RegisterActivity
        tvRegister.setOnClickListener(View.OnClickListener {
            finish()
            startActivity(Intent(applicationContext, Activity_register::class.java))
        })
    }

    private fun userLogin() {
        //first getting the values
        val username = etName.text.toString()
        val password = etPassword.text.toString()
        //validating inputs
        if (TextUtils.isEmpty(username)) {
            etName.error = "Please enter your username"
            etName.requestFocus()
            return
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.error = "Please enter your password"
            etPassword.requestFocus()
            return
        }

        //if everything is fine
        val stringRequest = object : StringRequest(Request.Method.POST, URLs.URL_LOGIN,
            Response.Listener { response ->
                progressBar.visibility = View.GONE

                try {
                    //converting response to json object
                    val obj = JSONObject(response)

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_SHORT).show()

                        //getting the user from the response
                        val userJson = obj.getJSONObject("user")

                        //creating a new user object
                        val user = User(
                            userJson.getInt("id"),
                            userJson.getString("idusr"),
                            userJson.getString("username"),
                            userJson.getString("email"),
                            userJson.getString("gender")
                        )

                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(applicationContext).userLogin(user)
                        //starting the MainActivity
                        finish()
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                    } else {
                        Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error -> Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show() }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["username"] = username
                params["password"] = password
                return params
            }
        }

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest)
    }
}