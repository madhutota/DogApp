package com.inditrade.dogapp.data.api

import androidx.annotation.MainThread
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import timber.log.Timber

@ExperimentalCoroutinesApi
abstract class NetworkBoundRepository<T> {

    fun asFlow() = flow<State<T>> {
        // Emit Loading State
        emit(State.loading())
        try {
            // Fetch latest data from remote
            val apiResponse = fetchFromRemote()
            // Parse body
            val remotePosts = apiResponse.body()
            // Check for response validation
            if (apiResponse.isSuccessful && remotePosts != null) {
                emit(State.success(remotePosts))
            } else {
                // Something went wrong! Emit Error state.
                emit(State.error(apiResponse.message()))
            }
        } catch (e: Exception) {
            // Exception occurred! Emit error
            Timber.e("Exception ----> ${e.localizedMessage}")
            emit(State.error("No Data Found."))
            e.printStackTrace()
        }
    }

    @MainThread
    protected abstract suspend fun fetchFromRemote(): Response<T>
    /*   @MainThread
       protected abstract suspend fun fetchFromDB(): Response<T>*/
}