package cz.bankid.interviewproject.service

import cz.bankid.interviewproject.dto.CurrencyExchangeRate
import cz.bankid.interviewproject.httpclient.CnbWebClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class CnbExchangeRateService(
    private val client: CnbWebClient
) {

    fun getExchangeRate(): Mono<CurrencyExchangeRate> {
        return client.getExchangeRate()
    }

}