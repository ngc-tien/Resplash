package com.ngc.tien.resplash.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.UnknownHostException

sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>
    data object FirstPageLoading : UiState<Nothing>
    data class Success<T>(
        val data: T,
        val currentPage: Int = 0,
        val nextPageState: NextPageState = NextPageState.Idle
    ) : UiState<Nothing>

    data object NetworkError : UiState<Nothing>
    data class Error(val message: String) : UiState<Nothing>
    sealed interface NextPageState {
        data object Loading : NextPageState
        data object Idle : NextPageState
        data object Error : NextPageState
        data object Done : NextPageState
    }
}

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): UiState<T> {
    return withContext(dispatcher) {
        try {
            UiState.Success(apiCall.invoke())
        } catch (exception: Exception) {
            when (exception) {
                is IOException,
                is UnknownHostException -> UiState.NetworkError

                else -> UiState.Error(exception.message.toString())
            }
        }
    }
}