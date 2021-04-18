package com.dannark.turistando.domain

data class User (
    var userId: Long,
    var creationDate: Long,
    var lastUpdateDate: Long,
    var firstName: String,
    var lastName: String,
    var email: String,
    var city: String,
    var state: String,
    var country: String,
    var img: String,
)