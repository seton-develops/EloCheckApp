package com.seton_develops.elocalculator
import kotlin.math.pow

class EloCalculator {
    companion object {

        fun calculateUpdatedElos(userElo: Int,
                                 opponentElo: Int,
                                 kCoefficient: Int,
                                 result: Double): Int {



            val expectedValueUser = calculateExpectedValue(userElo, opponentElo)

            val updatedUserElo = userElo + kCoefficient * (result - expectedValueUser)

            //cast to integer
            return updatedUserElo.toInt()
        }

        fun calculateExpectedValue(userElo: Int, opponentElo: Int): Double {
            return 1/(1 + 10.0.pow((opponentElo-userElo)/400.0))
        }



    }

}