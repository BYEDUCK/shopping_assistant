package com.byeduck.shoppingassistant

import com.byeduck.shoppingassistant.products.remote.ErrorResponse
import com.google.gson.Gson
import io.vavr.control.Either
import io.vavr.control.Try
import retrofit2.Response

class ResponseHandler {

    private val gson: Gson = Gson().newBuilder()
        .create()

    fun <T> handleResponse(
        response: Response<T>, onSuccessAction: (T) -> Unit, onErrorAction: (ErrorResponse) -> Unit
    ) {
        handleResponse(response)
            .peekLeft(onErrorAction)
            .forEach(onSuccessAction)
    }

    private fun <T> handleResponse(response: Response<T>): Either<ErrorResponse, T> {
        return if (response.isSuccessful && response.body() != null) {
            Either.right(response.body())
        } else {
            val errorResponse = deserializeErrorResponse(response.errorBody()?.string())
            Either.left(errorResponse)
        }
    }

    private fun deserializeErrorResponse(response: String?): ErrorResponse {
        return Try.of { gson.fromJson(response, ErrorResponse::class.java) }
            .getOrElse(ErrorResponse.getDefault())
    }
}