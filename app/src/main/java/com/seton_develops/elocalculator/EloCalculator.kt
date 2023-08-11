package com.seton_develops.elocalculator
import kotlin.math.pow

class EloCalculator {
    companion object {

        fun calculateUpdatedElos(userElo: Int,
                                 opponentElo: Int,
                                 kCoefficient: Int,
                                 result: Double): Pair<Int, Int> {



            val expectedValueUser = calculateExpectedValue(userElo,opponentElo)
            val expectedvalueOpponent = 1.0 - expectedValueUser

            val updatedUserElo = userElo + kCoefficient * (result - expectedValueUser)
            val updatedOpponentElo = opponentElo + kCoefficient * (result - expectedvalueOpponent)

            //cast to integer
            return Pair(updatedUserElo.toInt(), updatedOpponentElo.toInt())
        }

        private fun calculateExpectedValue(userElo: Int, OpponentElo: Int): Double {
            return 1 / (1 + (10.0).pow((OpponentElo - userElo) / 400))
        }



    }

}