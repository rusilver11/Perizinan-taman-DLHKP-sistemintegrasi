package com.example.login.permohonan

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
//import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.login.MainActivity
import com.example.login.R
import com.example.login.SharedPrefManager
import kotlinx.android.synthetic.main.activity_form_permohonan.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class form_permohonan : AppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View?) {

        when(v?.id){
            R.id.imUpload ->{
                val intent = Intent()
                intent.setType("image/*")
                intent.setAction(Intent.ACTION_GET_CONTENT)
                startActivityForResult(intent,mediaHelper.getRcGallery())
            }
            R.id.btnSimpan -> {
                queryInsertUpdateDelete("insert")
            }
        }
    }
    //deklarasi
    val urlcat = "http://192.168.43.149/App_Perijinan/web_services/get_lok.php"
    val urloperasi = "http://192.168.43.149/App_Perijinan/web_services/query_operasi.php"
    var pilihCat = ""
    var tgla = ""
    var tglm = ""
    lateinit var CatAdapter : ArrayAdapter<String>
    var daftarCat = mutableListOf<String>()
    var imStr = ""
    val user = SharedPrefManager.getInstance(this).user
    var guser = user.idusr
    lateinit var mediaHelper: MediaHelper

    override fun onStart() {
        super.onStart()
        getCategory()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_permohonan)

        //media helper
        mediaHelper = MediaHelper(this)
        //awal DatePicker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        btntgl.setOnClickListener{
            val dpd = DatePickerDialog(this,DatePickerDialog.OnDateSetListener{view, mYear, mMonth, mDay ->
                tvtgl.setText(""+mYear+"-"+mMonth+"-"+mDay).toString()
            }, year, month, day)
            dpd.show()
        }

        btntgldua.setOnClickListener{
            val dpd = DatePickerDialog(this,DatePickerDialog.OnDateSetListener{view, mYear, mMonth, mDay ->
                tvtgldua.setText(""+mYear+"-"+mMonth+"-"+mDay).toString()
            }, year, month, day)
            dpd.show()
        }
        //Akhir Datepicker
        //Awal Timepicker
        val mPickTimeBtn = findViewById<ImageButton>(R.id.btnwkt)
        val textView     = findViewById<TextView>(R.id.tvwkt)
        mPickTimeBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                textView.text = SimpleDateFormat("HH:mm").format(cal.time).toString()
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        val mPickTimeBtn2 = findViewById<ImageButton>(R.id.btnwktdua)
        val textView2     = findViewById<TextView>(R.id.tvwktdua)

        mPickTimeBtn2.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                textView2.text = SimpleDateFormat("HH:mm").format(cal.time).toString()
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        //Akhir Timepicker
        CatAdapter = ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,daftarCat)
        spLok.adapter = CatAdapter
        spLok.onItemSelectedListener = itemSelected
        btnSimpan.setOnClickListener(this)
        imUpload.setOnClickListener(this)
    }
    val itemSelected = object : AdapterView.OnItemSelectedListener{
        override fun onNothingSelected(parent: AdapterView<*>?) {
            spLok.setSelection(0)
            pilihCat = daftarCat.get(0)
        }
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            pilihCat = daftarCat.get(position)
        }
    }
    fun getCategory(){
        val request = StringRequest(Request.Method.POST,urlcat,
            Response.Listener { response ->
                daftarCat.clear()
                val jsonArray = JSONArray(response)
                for(x in 0 .. (jsonArray.length()-1)){
                    val jsonObject = jsonArray.getJSONObject(x)
                    daftarCat.add(jsonObject.getString("nm_lokasi"))
                }
                CatAdapter.notifyDataSetChanged()
            },
            Response.ErrorListener { error ->  }
        )
        val queue = Volley.newRequestQueue(this)
        queue.add(request)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            if(requestCode == mediaHelper.getRcGallery()){
                imStr = mediaHelper.getBitmapToString(data!!.data, imUpload)
            }
        }
    }

    fun queryInsertUpdateDelete(mode : String){
        val request = object : StringRequest(Method.POST,urloperasi,
            Response.Listener { response ->
                //                val jsonObject = JSONObject(response)
//                val error = jsonObject.getString("kode")
//                if (error.equals("000")){
                Toast.makeText(this,"Operasi Berhasil", Toast.LENGTH_LONG).show()
//
//                }else{
//                    Toast.makeText(this,"Operasi GAGAL", Toast.LENGTH_LONG).show()
//                }

            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Tidak dapat terhubung ke server", Toast.LENGTH_LONG)
                    .show()
            }
        ){
            override fun getParams(): MutableMap<String, String> {
                val hm = HashMap<String,String>()
                val nmFile = "DC"+ android.icu.text.SimpleDateFormat("yyyymmddHHmmss",
                    Locale.getDefault()
                )
                    .format(Date())+",jpg"

                when(mode){
                    "insert" ->{
                        hm.put("mode","insert")
                        hm.put("id_usr",guser.toString())
                        hm.put("judul",tiJudul.text.toString())
                        hm.put("lembaga",tiLembaga.text.toString())
                        hm.put("tgl_mulai",tvtgl.text.toString())
                        hm.put("tgl_akhir",tvtgldua.text.toString())
                        hm.put("wkt_mulai",tvwkt.text.toString())
                        hm.put("wkt_akhir",tvwktdua.text.toString())
                        hm.put("jml_peserta",tiJumlah.text.toString())
                        hm.put("jns_peserta",tiJumlah2.text.toString())
                        hm.put("lokasi",pilihCat)
                        hm.put("kegiatan",tiKegiatan.text.toString())
                        hm.put("image",imStr)
                        hm.put("file",nmFile)
                    }
                }
                return hm
            }
        }
        val queue = Volley.newRequestQueue(this)
        queue.add(request)
    }
}
