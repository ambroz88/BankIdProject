package cz.bankid.interviewproject.service

import cz.bankid.interviewproject.dto.RateDifference
import cz.bankid.interviewproject.httpclient.CnbWebClient
import cz.bankid.interviewproject.httpclient.FrankfurterWebClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.math.RoundingMode

@Service
class ComparisonService(
    private val cnbWebClient: CnbWebClient,
    private val frankfurterWebClient: FrankfurterWebClient,
) {

    fun getCurrencyPairs(): Mono<List<String>> {
        return cnbWebClient.getExchangeRate()
            .flatMap { cnbRates ->
                val cnbCurrencies = cnbRates.rates.keys
                frankfurterWebClient.getAvailableCurrencies()
                    .map { currencies ->
                        currencies.intersect(cnbCurrencies).map { "CZK_$it" }
                    }
            }
    }

    fun getRateDifference(currency: String): Mono<RateDifference> {
        return frankfurterWebClient.getSpecificExchangeRate(currency)
            .flatMap { exchangeRate ->
                exchangeRate.rates[currency]
                    ?.let { compareRate(it, currency) }
                    ?: Mono.empty()
            }
    }

    private fun compareRate(frankfurtRate: Double, currency: String): Mono<RateDifference> {
        return cnbWebClient.getExchangeRate()
            .mapNotNull {
                it.rates[currency]?.let { cnbRate ->
                    val difference = BigDecimal.valueOf(cnbRate - frankfurtRate).setScale(5, RoundingMode.HALF_UP)
                    RateDifference(
                        cnbRate = cnbRate,
                        frankfurtRate = frankfurtRate,
                        difference = difference.toDouble()
                    )
                }
            }
    }
}