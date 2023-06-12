package com.example.it_link_xml.data

import com.example.it_link_xml.BASE_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.OkHttpClient
import okhttp3.Request

class Repository {
    private val client: OkHttpClient = OkHttpClient()

    suspend fun getImageList() = flow {
        val request = Request.Builder()
            .url(BASE_URL)
            .build()

        try {
            val linksText = client.newCall(request).execute().body?.string()
            linksText?.split("\r\n")?.let { webList ->
                emit(webList.distinct())
            }
        } catch (e: Exception) {
            emit(e.message)
        }

    }
        .flowOn(Dispatchers.IO)
}