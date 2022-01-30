package com.informatika19100073.srioktavia_19100073_alarm.model

import com.google.gson.annotations.SerializedName

data class ResponseAdmin(

	@field:SerializedName("pesan")
	val pesan: String? = null,

	@field:SerializedName("data")
	val data: List<DataItem1?>? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class DataItem1(

	@field:SerializedName("Username")
	val Username: String? = null,

	@field:SerializedName("Password")
	val Password: String? = null
)
