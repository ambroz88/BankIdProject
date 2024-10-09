package cz.bankid.interviewproject.httpclient

import cz.bankid.interviewproject.dto.CurrencyExchangeRate
import cz.bankid.interviewproject.mapper.CurrencyExchangeRateMapper
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

private val URL = "https://www.cnb.cz"

@Component
class CnbWebClient {

    private val webClient: WebClient = WebClient.create(URL)

    fun getExchangeRate(): Mono<CurrencyExchangeRate> {
        return webClient.get().uri("/cs/financni_trhy/devizovy_trh/kurzy_devizoveho_trhu/denni_kurz.txt")
            .accept(MediaType.TEXT_PLAIN)
            .retrieve()
            .bodyToMono(String::class.java)
            .map {
                CurrencyExchangeRateMapper.fromCnbPlainText(it)
            }
    }
}