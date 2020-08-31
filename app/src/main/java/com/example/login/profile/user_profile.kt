package com.example.login.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.login.MainActivity
import com.example.login.R
import com.example.login.SharedPrefManager
import com.example.login.permohonan.profileAdapter
import org.json.JSONArray

class user_profile : AppCompatActivity() {

    internal lateinit var id: TextView
    internal lateinit var userName: TextView
    internal lateinit var userEmail: TextView
    internal lateinit var gender: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
            id = findViewById(R.id.tvId)
            userName = findViewById(R.id.tvUsernama)
            userEmail = findViewById(R.id.tvEmail)
            gender = findViewById(R.id.tvGender)

            val user = SharedPrefManager.getInstance(this).user
            id.text = user.id.toString()
            userEmail.text = user.email
            gender.text = user.gender
            userName.text = user.name

    }
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}