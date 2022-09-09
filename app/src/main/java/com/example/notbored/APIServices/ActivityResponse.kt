package com.example.notbored.APIServices


import com.google.gson.annotations.SerializedName

/**
 * data class for the API response
 */
data class ActivityResponse(
    @SerializedName("activity") var activity: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("participants") var participants: Int? = null,
    @SerializedName("price") var price: Double? = null,
    @SerializedName("accessibility") var accessibility: Double? = null,
    var error: String = ""

)
