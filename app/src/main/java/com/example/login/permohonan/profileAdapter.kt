package com.example.login.permohonan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.login.R

class profileAdapter (val dataIjn : List<HashMap<String,String>>) :
    RecyclerView.Adapter<profileAdapter.HolderDataMhs>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): HolderDataMhs {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.row_ijn,p0,false)
        return HolderDataMhs(v)
    }

    override fun getItemCount(): Int {
        return dataIjn.size
    }

    override fun onBindViewHolder(p0: HolderDataMhs, p1: Int) {
        val data = dataIjn.get(p1)
        p0.txId.setText(data.get("id_mp"))
        p0.txNm.setText(data.get("tgl_ajuan"))
        p0.txJdl.setText(data.get("jdl_kegiatan"))
        p0.txStatus.setText(data.get("td_status"))

    }
    class HolderDataMhs (v : View) : RecyclerView.ViewHolder(v){
        val txId = v.findViewById<TextView>(R.id.txId_mp)
        val txNm = v.findViewById<TextView>(R.id.txNm_user)
        val txJdl = v.findViewById<TextView>(R.id.txJudul)
        val txStatus = v.findViewById<TextView>(R.id.txStatus)

    }

}