package com.seton_develops.elocalculator.Model

data class EloData(
    val userElo: Int,
    val opponentElo: Int,
    val kValueIndex: Int,
    val organization: String?
)
