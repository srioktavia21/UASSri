package com.informatika19100073.srioktavia_19100073_alarm.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.informatika19100073.srioktavia_19100073_alarm.InsertDataActivity
import com.informatika19100073.srioktavia_19100073_alarm.MainActivity
import com.informatika19100073.srioktavia_19100073_alarm.R
import com.informatika19100073.srioktavia_19100073_alarm.UpdateDataActivity
import com.informatika19100073.srioktavia_19100073_alarm.model.DataItem
import com.informatika19100073.srioktavia_19100073_alarm.model.ResponseActionAlarm
import com.informatika19100073.srioktavia_19100073_alarm.network.koneksi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListContent(val ldata : List<DataItem?>?, val context: Context, val kondisi : String) :
        RecyclerView.Adapter<ListContent.ViewHolder>() {
        class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
            val Jam = view.findViewById<TextView>(R.id.tv_jam)
            val Menit = view.findViewById<TextView>(R.id.tv_menit)
            val editAlarm = view.findViewById<TextView>(R.id.tv_edit)
            val deleteAlarm = view.findViewById<TextView>(R.id.tv_delete)

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_barang, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return ldata!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = ldata?.get(position)
        holder.Jam.text = model?.Jam
        holder.Menit.text = model?.Menit
        holder.editAlarm.setOnClickListener {
            val i = Intent(context, UpdateDataActivity::class.java)
            i.putExtra("IDALARM", model?.id)
            i.putExtra("JAMALARM", model?.Jam)
            i.putExtra("MENITALARM", model?.Menit)
            context.startActivity(i)
        }
        holder.deleteAlarm.setOnClickListener {
            AlertDialog.Builder(context)
               .setTitle("Delete" + model?.Jam)
                .setMessage("Apakah Anda Ingin Mengahapus Data Ini?")
                .setPositiveButton("Ya", DialogInterface.OnClickListener { dialog, which ->

                    koneksi.service.deleteAlarm(model?.id).enqueue(object : Callback<ResponseActionAlarm>{
                        override fun onFailure(call: Call<ResponseActionAlarm>, t: Throwable) {
                            Log.d("pesan1", t.localizedMessage)
                        }

                        override fun onResponse(
                            call: Call<ResponseActionAlarm>,
                            response: Response<ResponseActionAlarm>
                        ) {
                            if(response.isSuccessful){
                                Toast.makeText(context, "Data Berhasil Dihapus", Toast.LENGTH_LONG).show()
                                notifyDataSetChanged()
                                notifyItemRemoved(position)
                                notifyItemChanged(position)
                                notifyItemRangeChanged(position, ldata!!.size)

                                if (kondisi == " InsertDataActivity"){
                                    val activity = (context as InsertDataActivity)
                                    activity.getData()
                                }else if (kondisi == " UpdateDataActivity"){
                                    val activity = (context as UpdateDataActivity)
                                    activity.getData()
                                }else{
                                    val activity = (context as MainActivity)
                                    activity.getData()
                                }

                                Log.d("bpesan", response.body().toString())

                            }
                        }
                    })
                })
                .setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
                    dialog.cancel()
                })
                .show()
        }
    }


}