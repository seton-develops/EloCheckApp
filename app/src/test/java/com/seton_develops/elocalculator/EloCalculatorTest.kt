package com.seton_develops.elocalculator

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
internal class EloCalculatorTest {

    @Test
    fun checkCalculation1() {
        var userNewScore = EloCalculator.calculateUpdatedElos(userElo =2039,
            opponentElo = 1571,
            kCoefficient = 20,
            result = 0.5)

        assertThat(userNewScore).isEqualTo(2031)
    }

    @Test
    fun checkCalculation2() {
        var userNewScore = EloCalculator.calculateUpdatedElos(userElo =2068,
            opponentElo = 2193,
            kCoefficient = 20,
            result = 0.0)

        assertThat(userNewScore).isEqualTo(2061)
    }

    @Test
    fun checkExpectedCalculation1() {
        var expect = EloCalculator.calculateExpectedValue(2600,2300)

        assertThat(expect).isEqualTo(0.849)
    }
}