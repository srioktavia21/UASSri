package com.informatika19100073.srioktavia_19100073_alarm.model

import com.google.gson.annotations.SerializedName

data class ResponseAlarm(

    @field:SerializedName( "pesan")
    val pesan: String? = null,

    @field:SerializedName("data")
    val data: List<DataItem?>? = null,

    @field:SerializedName("status")
    val status: Boolean? = null
)

data class DataItem(


    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("Jam")
    val Jam: String? = null,

    @field:SerializedName("Menit")
    val Menit: String? =null
)