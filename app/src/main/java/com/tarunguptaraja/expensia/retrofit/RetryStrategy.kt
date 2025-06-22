package com.tarunguptaraja.expensia.retrofit

data class RetryStrategy<T>(var times: Int, val predicate: (T?, Int) -> Boolean)