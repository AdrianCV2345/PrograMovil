package com.calyrsoft.ucbp1.features.Series.data.api.dto

import com.google.gson.annotations.SerializedName

data class SerieDto(val login: String,
    @SerializedName("posters") val pathUrl: String, val title: String)
