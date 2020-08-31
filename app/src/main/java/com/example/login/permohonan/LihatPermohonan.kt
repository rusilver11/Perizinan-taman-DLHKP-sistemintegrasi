package com.example.login.permohonan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import kotlinx.android.synthetic.main.activity_lihat_permohonan.*
import org.json.JSONArray

class LihatPermohonan : AppCompatActivity() {


    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lihat_permohonan)

        ijnAdapter = profileAdapter(daftarStatus)

        listPermohonan.layoutManager = LinearLayoutManager(this)
        listPermohonan.adapter = ijnAdapter

    }

    var daftarStatus = mutableListOf<HashMap<String,String>>()
    val url = "http://192.168.43.149/App_Perijinan/web_services/show_data.php"
    lateinit var ijnAdapter : profileAdapter
    val user = SharedPrefManager.getInstance(this).user
    var puser = user.idusr



    fun showDataUser(iduser : String){
        val request = object : StringRequest(
            Request.Method.POST,url,
            Response.Listener{ response ->
                daftarStatus.clear()
                val jsonArray = JSONArray(response)
                if(jsonArray?.length()== 0){
                    val intent = Intent(this@LihatPermohonan, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                for(x in 0 .. (jsonArray.length()-1)){
                    val jsonObject = jsonArray.getJSONObject(x)
                    var ijn = HashMap<String,String>()
                    ijn.put("id_mp",jsonObject.getString("id_mp"))
                    ijn.put("tgl_ajuan",jsonObject.getString("tgl_ajuan"))
                    ijn.put("jdl_kegiatan",jsonObject.getString("jdl_kegiatan"))
                    ijn.put("td_status",jsonObject.getString("td_status"))
                    daftarStatus.add(ijn)
                }
                ijnAdapter.notifyDataSetChanged()

            },
            Response.ErrorListener { error ->
                Toast.makeText(this,"Terjadi kesalahan Koneksi Server", Toast.LENGTH_LONG).show()
            }){
            override fun getParams(): MutableMap<String, String> {
                val hm = HashMap<String,String>()
                hm.put("id",iduser)
                return hm
            }
        }
        val queue = Volley.newRequestQueue(this)
        queue.add(request)
    }

    override fun onStart() {
        super.onStart()
        showDataUser(puser.toString())
    }

}
