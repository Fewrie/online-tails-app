package com.wekomodo.onlinetails.webServices

import com.wekomodo.onlinetails.utils.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private  val retrofit by lazy {
         val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
         val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(logging).build()
        Retrofit.Builder()
            .baseUrl(Constant.BASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}

