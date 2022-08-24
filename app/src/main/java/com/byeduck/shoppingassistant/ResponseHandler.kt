package com.byeduck.shoppingassistant

import io.vavr.control.Either
import retrofit2.Response

class ResponseHandler {

    fun <T> handleResponse(
        response: Response<T>,
        onSuccessAction: (T) -> Unit,
        onErrorAction: (GenericResponse) -> Unit
    ) {
        handleResponse(response)
            .peekLeft(onErrorAction)
            .forEach(onSuccessAction)
    }

    private fun <T> handleResponse(response: Response<T>): Either<GenericResponse, T> {
        return if (response.isSuccessful && response.body() != null) {
            Either.right(response.body())
        } else {
            val errorResponse = GenericResponse(response.code(), response.errorBody()?.string())
            Either.left(errorResponse)
        }
    }
}