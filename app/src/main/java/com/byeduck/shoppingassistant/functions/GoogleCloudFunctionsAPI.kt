package com.byeduck.shoppingassistant.functions

import com.byeduck.shoppingassistant.ranked.RankRequest
import com.byeduck.shoppingassistant.ranked.RankedProduct
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface GoogleCloudFunctionsAPI {

    @POST("/product-rank")
    fun rankProducts(
        @Body rankRequest: RankRequest
    ): Call<List<RankedProduct>>
}