package com.toolgeo.server.view

data class COSTmpToken(
    var tmpSecretId: String,
    var tmpSecretKey: String,
    var sessionToken: String,
    var startTime: Number,
    var expiredTime: Number,
)