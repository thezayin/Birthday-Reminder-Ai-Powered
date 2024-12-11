package com.thezayin.framework.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ServerUrl(
    @SerialName("first_url") val first: String,
    @SerialName("second_url") val second: String,
    @SerialName("third_url") val third: String,
    @SerialName("fourth_url") val fourth: String,
    @SerialName("fifth_url") val fifth: String
)

val defaultServerUrl = """
{
  "first_url": "https://simdata.net/",
  "second_url": "https://simownerdata.com.pk/",
  "third_url": "https://simdatabase.info/search",
  "fourth_url": "https://simsinfopk.com/sim-info-3",
  "fifth_url": "https://pakdb.xyz/pak-sim-data/"
}
""".trimIndent()