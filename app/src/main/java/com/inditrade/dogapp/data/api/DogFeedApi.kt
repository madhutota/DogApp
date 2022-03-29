package com.inditrade.dogapp.data.api

import com.inditrade.dogapp.data.models.DogFeedModel
import retrofit2.Response
import retrofit2.http.*

interface DogFeedApi {
    @GET(DOG_FEED)
    suspend fun getDogFeedApi(): Response<DogFeedModel>

    @GET(DOG_BREED)
    suspend fun getDogFeedByBreedApi(@Path("query") path:String ): Response<DogFeedModel>


}