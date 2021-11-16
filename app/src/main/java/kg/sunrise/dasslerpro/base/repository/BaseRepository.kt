package kg.sunrise.dasslerpro.base.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

abstract class BaseRepository(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun <T> makeRequest(request: suspend () -> Response<T>?): Response<T>? =
        try {
            withContext(defaultDispatcher) {
                request()
            }
        } catch (e: Exception) {
            null
        }
}