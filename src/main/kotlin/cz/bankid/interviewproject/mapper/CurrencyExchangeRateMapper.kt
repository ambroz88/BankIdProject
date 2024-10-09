package cz.bankid.interviewproject.mapper

import cz.bankid.interviewproject.dto.CurrencyExchangeRate
import java.io.BufferedReader
import java.io.StringReader
import java.math.BigDecimal
import java.math.RoundingMode


object CurrencyExchangeRateMapper {

    fun fromCnbPlainText(textContent: String): CurrencyExchangeRate {
        val date: String
        return BufferedReader(StringReader(textContent)).readLines()
            .also {
                date = it[0].split(" ")[0]
            }
            .drop(2)
            .associate { line ->
                val currencyInfo = line.split("|")
                val rate = 1 / currencyInfo[4].replace(",", ".").toDouble()
                currencyInfo[3] to (BigDecimal.valueOf(rate).setScale(5, RoundingMode.HALF_UP)).toDouble()
            }
            .let { rateMap ->
                CurrencyExchangeRate(
                    base = "CZK",
                    date = date,
                    amount = 1,
                    rates = rateMap
                )
            }
    }
}