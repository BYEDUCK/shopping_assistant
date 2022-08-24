package com.byeduck.shoppingassistant.remote

data class ErrorResponse(val status: Int, val error: String, val requestId: String) {

    companion object {

        fun getDefault(status: Int = 500): ErrorResponse {
            return ErrorResponse(status, "Unknown", "N/A")
        }
    }
}
