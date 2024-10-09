package cz.bankid.interviewproject.service

import cz.bankid.interviewproject.dto.CurrencyExchangeRate
import cz.bankid.interviewproject.httpclient.FrankfurterWebClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class FrankfurterExchangeRateService(
    private val client: FrankfurterWebClient
) {

    fun getExchangeRate(): Mono<CurrencyExchangeRate> {
        return client.getExchangeRate()
    }

}