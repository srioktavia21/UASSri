package com.informatika19100073.srioktavia_19100073_alarm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.informatika19100073.srioktavia_19100073_alarm.model.ResponseAdmin
import com.informatika19100073.srioktavia_19100073_alarm.network.koneksi
import com.informatika19100073.srioktavia_19100073_alarm.service.SessionPreferences
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var sessionPreferences: SessionPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        setContentView(R.layout.activity_login)
        btn_submit.setOnClickListener {
            val Username = et_username.text.toString()
            val Password = et_password.text.toString()

            if (Username.isEmpty() || Password.isEmpty()){
                Toast.makeText(this, "Form tidak boleh kosong!", Toast.LENGTH_LONG).show()
            }else{
                actionData(Username, Password)
            }
        }
        btn_clean.setOnClickListener {
            formClear()
        }
        tv_disini.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
    fun actionData(Username : String, Password : String){
        koneksi.service.logIn(Username, Password).enqueue(object : Callback<ResponseAdmin> {
            override fun onFailure(call: Call<ResponseAdmin>, t: Throwable) {
                Log.d("pesan1", t.localizedMessage)
            }

            override fun onResponse(call: Call<ResponseAdmin>, response: Response<ResponseAdmin>) {
                if (response.isSuccessful){
                    val resbody = response.body()
                    val resStatus = resbody?.status
                    val resUsername = resbody?.data?.get(0)?.Username
                    Log.d("pesan", resUsername.toString())
                    if (resStatus == true){
                        sessionPreferences = SessionPreferences(this@LoginActivity)
                        sessionPreferences.actionLogin(resUsername.toString())
                        val i = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(i)
                        finish()
                    }else if (resStatus == false){
                        Toast.makeText(this@LoginActivity, "Username atau Password Anda Salah!", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }
    fun formClear(){
        et_username.text.clear()
        et_password.text.clear()
    }
}