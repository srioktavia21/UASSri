package com.informatika19100073.srioktavia_19100073_alarm

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.informatika19100073.srioktavia_19100073_alarm.adapter.ListContent
import com.informatika19100073.srioktavia_19100073_alarm.model.ResponseActionAlarm
import com.informatika19100073.srioktavia_19100073_alarm.model.ResponseAlarm
import com.informatika19100073.srioktavia_19100073_alarm.network.koneksi
import kotlinx.android.synthetic.main.activity_update_data.*
import kotlinx.android.synthetic.main.activity_update_data.et_menit
import kotlinx.android.synthetic.main.activity_update_data.et_jam
import kotlinx.android.synthetic.main.activity_update_data.rv_data_alarm
import kotlinx.android.synthetic.main.activity_update_data.toolbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateDataActivity : AppCompatActivity() {
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_data)
        toolbar.title = "UPDATE DATA"
        toolbar.setTitleTextColor(Color.WHITE)

        val i = intent
        val id = i.getStringExtra("IDALARM")
        val Jam = i.getStringExtra("JAMALARM")
        val Menit = i.getStringExtra("MENITALARM")

        et_jam.setText(Jam)
        et_menit.setText(Menit)
        btn_submit.setOnClickListener {
            val etJam = et_jam.text
            val etMenit = et_menit.text
            if (etJam.isEmpty()){
                Toast.makeText(this@UpdateDataActivity, "Jam Tidak Boleh Kosong", Toast.LENGTH_LONG).show()
            }else if (etMenit.isEmpty()){
                Toast.makeText(this@UpdateDataActivity, "Menit Tidak Boleh Kosong", Toast.LENGTH_LONG).show()
            }else{
                actionDataAlarm(id.toString(), etJam.toString(), etMenit.toString())
            }
        }
        btn_back.setOnClickListener {
            finish()
        }
        getData()
    }
    fun actionDataAlarm(id : String, Jam : String, Menit : String){
        koneksi.service.updateAlarm(id, Jam, Menit).enqueue(object : Callback<ResponseActionAlarm> {
            override fun onFailure(call: Call<ResponseActionAlarm>, t: Throwable) {
                Log.d("pesan1", t.localizedMessage)
            }

            override fun onResponse(
                call: Call<ResponseActionAlarm>,
                response: Response<ResponseActionAlarm>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@UpdateDataActivity,
                        "data berhasil diupdate",
                        Toast.LENGTH_LONG
                    ).show()
                    getData()
                }
            }
        })
    }
    fun getData(){
        koneksi.service.getAlarm().enqueue(object : Callback<ResponseAlarm>{
            override fun onFailure(call: Call<ResponseAlarm>, t: Throwable) {
                Log.d("pesan1", t.localizedMessage)
            }

            override fun onResponse(
                call: Call<ResponseAlarm>,
                response: Response<ResponseAlarm>
            ) {
                if (response.isSuccessful){
                    val dataBody = response.body()
                    val datacontent = dataBody!!.data

                    val rvAdapter = ListContent(datacontent, this@UpdateDataActivity, "UpdateDataActivity")


                    rv_data_alarm.apply {
                        adapter = rvAdapter
                        layoutManager = LinearLayoutManager(this@UpdateDataActivity)
                    }

                    }
                }

        })
    }
}

private fun Any.enqueue(callback: Callback<ResponseActionAlarm>) {

}

