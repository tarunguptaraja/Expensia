package com.tarunguptaraja.expensia.retrofit

import okhttp3.HttpUrl

val endPointsWithTimeMap = mapOf<String, Long>(
    "gameService/checkUpdateAvailable" to 3 * 1000,
    "paymentService/getPendingWithdrawals" to 30 * 1000,
    "tournaments/getActiveGames" to 5 * 1000,
//    "walletService/getWalletDetailsByToken" to 10 * 1000,
    "bonusService/getUserOffers" to 60 * 1000,
    "bonusService/newUserOffers" to 60 * 1000,
//    "walletService/getJaxClubConfiguration" to 5 * 60 * 1000,
    "tournaments/getSpecialTournaments" to 60 * 1000,
    "pointsTableManager/tableRefill" to 2 * 1000
)

val endPointsWithLastTimeCall = mutableMapOf<String, Pair<Long, String?>>()

fun getCashedResponse(endPoint: String): String? {
    val requiretimeInSecound = endPointsWithTimeMap[endPoint]

    if (!endPointsWithLastTimeCall.isNullOrEmpty() && requiretimeInSecound != null) {
        val cashedResponse = endPointsWithLastTimeCall[endPoint]
        return when {
            cashedResponse == null -> {
                null
            }

            (cashedResponse.first + requiretimeInSecound) > System.currentTimeMillis() -> {
                cashedResponse.second
            }

            else -> {
                null
            }
        }
    }

    return null
}

val HttpUrl.isCached: Boolean
    get() = getCashedResponse(endPoint) != null