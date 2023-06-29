package com.hasanaltunbay.kriptopara.service

import com.hasanaltunbay.kriptopara.model.KriptoModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET

interface KriptoAPI {

    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    fun getData(): Observable<List<KriptoModel>>

}