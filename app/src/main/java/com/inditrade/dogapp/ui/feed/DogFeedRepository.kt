package com.inditrade.dogapp.ui.feed

import com.inditrade.dogapp.data.api.DogFeedApi
import com.inditrade.dogapp.data.api.NetworkBoundRepository
import com.inditrade.dogapp.data.api.State
import com.inditrade.dogapp.data.models.DogFeedModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class DogFeedRepository @Inject constructor(var api: DogFeedApi) {

    fun getDogFeed(isBreed: Boolean, path: String): Flow<State<DogFeedModel>> {
        return object : NetworkBoundRepository<DogFeedModel>() {
            override suspend fun fetchFromRemote(): Response<DogFeedModel> {
                val response = if (isBreed) {
                    api.getDogFeedByBreedApi(path)
                } else {
                    api.getDogFeedApi()
                }
                return response
            }

        }.asFlow().flowOn(Dispatchers.IO)
    }
}