package com.example.chaithra.kotlinwithrecycleviewexample.BookApi

import com.google.gson.JsonObject
import io.reactivex.Observable
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Retrofit


interface BookApiFetch {
    //here you can change title you want to search
    //for example q= android it will search 20 android related books you can the number too.
    @GET("volumes?maxVolume=20&q=ios/swift")
    fun searchBook(): Observable<JsonObject>


    }
