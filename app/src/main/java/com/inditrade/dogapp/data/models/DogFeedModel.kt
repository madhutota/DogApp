package com.inditrade.dogapp.data.models

import com.google.gson.annotations.SerializedName

data class DogFeedModel(
    @SerializedName("message")
    var imagesList: MutableList<String>,
    @SerializedName("status")
    var status: String? = null
)