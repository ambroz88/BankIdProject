package cz.bankid.interviewproject.dto

data class CurrencyExchangeRate(
    val base: String,
    val date: String,
    val amount: Int,
    val rates: Map<String, Double>
)
