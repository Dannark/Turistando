package com.dannark.turistando.domain

data class Friend (
    var friendshipId : Long,
    var userId: Long,
    var friendId: Long,
    var creationDate: Long,
    var approvedDate: Long,
    var firstName: String,
    var lastName: String,
    var email: String,
    var city: String,
    var state: String,
    var country: String,
    var img: String,
)