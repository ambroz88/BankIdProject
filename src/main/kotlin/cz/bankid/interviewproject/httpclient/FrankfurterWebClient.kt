package cz.bankid.interviewproject.httpclient

import cz.bankid.interviewproject.dto.CurrencyExchangeRate
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

private val URL = "https://api.frankfurter.app"

@Component
class FrankfurterWebClient {

    private val webClient: WebClient = WebClient.create(URL)

    fun getExchangeRate(): Mono<CurrencyExchangeRate> {
        return webClient.get().uri("/latest?base=CZK")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(CurrencyExchangeRate::class.java)
    }

    fun getSpecificExchangeRate(currencyCode: String): Mono<CurrencyExchangeRate> {
        return webClient.get().uri("/latest?base=CZK&symbols=$currencyCode")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(CurrencyExchangeRate::class.java)
    }

    fun getAvailableCurrencies(): Mono<Set<*>> {
        return webClient.get().uri("/currencies")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Map::class.java)
            .map { it.keys }
    }
}