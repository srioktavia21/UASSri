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
import kotlinx.android.synthetic.main.activity_insert_data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InsertDataActivity : AppCompatActivity() {
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_data)
        toolbar.title = "INSERT DATA"
        toolbar.setTitleTextColor(Color.WHITE)

        btn_submit.setOnClickListener {
            val etJam = et_jam.text
            val etMenit = et_menit.text
            if (etMenit.isEmpty()) {
                Toast.makeText(
                    this@InsertDataActivity,
                    "Menit Tidak Boleh Kosong",
                    Toast.LENGTH_LONG
                ).show()
            } else if (etJam.isEmpty()) {
                Toast.makeText(
                    this@InsertDataActivity,
                    "Jam Tidak Boleh Kosong",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                actionData(etJam.toString(), etMenit.toString())
            }
        }

        btn_clean.setOnClickListener {
            formClear()
        }
        getData()
    }

    fun formClear() {
        et_jam.text.clear()
        et_menit.text.clear()

    }

    fun actionData(Jam: String, Menit: String) {
        koneksi.service.insertAlarm(Jam, Menit)
            .enqueue(object : Callback<ResponseActionAlarm> {
                override fun onFailure(call: Call<ResponseActionAlarm>, t: Throwable) {
                    Log.d("pesan1", t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<ResponseActionAlarm>,
                    response: Response<ResponseActionAlarm>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@InsertDataActivity,
                            "data berhasil disimpan",
                            Toast.LENGTH_LONG
                        ).show()
                        formClear()
                        getData()
                    }
                }
            })
    }

    fun getData() {
        koneksi.service.getAlarm().enqueue(object : Callback<ResponseAlarm> {
            override fun onFailure(call: Call<ResponseAlarm>, t: Throwable) {
                Log.d("pesan1", t.localizedMessage)
            }

            override fun onResponse(
                call: Call<ResponseAlarm>,
                response: Response<ResponseAlarm>
            ) {
                if (response.isSuccessful) {
                    val dataBody = response.body()
                    val datacontent = dataBody!!.data

                    val rvAdapter = ListContent(datacontent, this@InsertDataActivity, "InsertDataActivity")

                    rv_data_alarm.apply {
                        adapter = rvAdapter
                        layoutManager = LinearLayoutManager(this@InsertDataActivity)
                    }
                }
            }
        })
    }
}

