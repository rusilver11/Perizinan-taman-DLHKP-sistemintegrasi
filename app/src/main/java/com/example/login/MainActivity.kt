package com.example.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.login.permohonan.LihatPermohonan
import com.example.login.permohonan.form_permohonan
import com.example.login.profile.user_profile

class MainActivity : AppCompatActivity(), View.OnClickListener {

    internal lateinit var id: TextView
    internal lateinit var idusr: TextView
    internal lateinit var userName: TextView
    internal lateinit var userEmail: TextView
    internal lateinit var gender: TextView

    internal lateinit var btnLogout: ImageButton
    lateinit var btn: ImageButton
    lateinit var btnProfile: ImageButton
    lateinit var btnLihat: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (SharedPrefManager.getInstance(this).isLoggedIn) {
            id = findViewById(R.id.textViewId)
            idusr = findViewById(R.id.textViewIdusr)
            userName = findViewById(R.id.textViewUsername)
            userEmail = findViewById(R.id.textViewEmail)
            gender = findViewById(R.id.textViewGender)

            btnLogout = findViewById(R.id.buttonLogout)
            btn = findViewById(R.id.btn_permohonan)
            btnProfile = findViewById(R.id.btn_profile)
            btnLihat = findViewById(R.id.btn_lihat)

            val user = SharedPrefManager.getInstance(this).user

            id.text = user.id.toString()
            idusr.text = user.idusr
            userEmail.text = user.email
            gender.text = user.gender
            userName.text = user.name

            btnProfile.setOnClickListener(this)
            btn.setOnClickListener(this)
            btnLogout.setOnClickListener(this)
            btnLihat.setOnClickListener(this)

        } else {
            val intent = Intent(this@MainActivity, Activity_login::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onClick(view: View) {
        if (view == btnLogout) {
            SharedPrefManager.getInstance(applicationContext).logout()
        }
        if (view == btn) {
            SharedPrefManager.getInstance(applicationContext)
            val intent = Intent(this@MainActivity, form_permohonan::class.java)
            startActivity(intent)
            finish()
        }
        if (view == btnProfile) {
            SharedPrefManager.getInstance(applicationContext)
            val intent = Intent(this@MainActivity, user_profile::class.java)
            startActivity(intent)
            finish()
        }
        if (view == btnLihat) {
            SharedPrefManager.getInstance(applicationContext)
            val intent = Intent(this@MainActivity, LihatPermohonan::class.java)
            startActivity(intent)
            finish()
        }
    }
}