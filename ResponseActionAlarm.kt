package com.informatika19100073.srioktavia_19100073_alarm.model

import com.google.gson.annotations.SerializedName

data class ResponseActionAlarm(

    @field:SerializedName("pesan")
    val pesan: Any? = null,

    @field:SerializedName("data")
    val data: List<Boolean?>? = null,

    @field:SerializedName("status")
    val status: String? = null,
    )
