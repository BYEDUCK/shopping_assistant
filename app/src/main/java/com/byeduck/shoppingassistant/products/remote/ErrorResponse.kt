package com.byeduck.shoppingassistant.products.remote

data class ErrorResponse(val status: Int, val error: String, val requestId: String) {

    companion object {

        fun getDefault(): ErrorResponse {
            return ErrorResponse(500, "Unknown", "N/A")
        }
    }
}
