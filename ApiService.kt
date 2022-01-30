package com.informatika19100073.srioktavia_19100073_alarm.network

import com.informatika19100073.srioktavia_19100073_alarm.model.ResponseActionAlarm
import com.informatika19100073.srioktavia_19100073_alarm.model.ResponseAdmin
import com.informatika19100073.srioktavia_19100073_alarm.model.ResponseAlarm
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiService {
    @GET("read.php")
    fun getAlarm(): Call<ResponseAlarm>

    @FormUrlEncoded
    @POST("create.php")
    fun insertAlarm(
        @Field("Jam") Jam: String?,
        @Field("Menit") Menit: String?
    ): Call<ResponseActionAlarm>

    @FormUrlEncoded
    @POST("update.php")
    fun updateAlarm(
        @Field("id") id: String?,
        @Field("Jam") Jam: String?,
        @Field("Menit") Menit: String?
    ): Call<ResponseActionAlarm>

    @FormUrlEncoded
    @POST("delete.php")
    fun deleteAlarm(
        @Field("id") id: String?
    ): Call<ResponseActionAlarm>

    @FormUrlEncoded
    @POST("login.php")
    fun logIn(
        @Field("Username") Username : String?,
        @Field("Password") Password : String?
    ):Call<ResponseAdmin>

    @FormUrlEncoded
    @POST("register.php")
    fun register(
        @Field("Username") Username : String?,
        @Field("Password") Password : String?
    ):Call<ResponseAdmin>

    abstract fun updateAlarm(id: String, jam: String, menit: String): Any
}