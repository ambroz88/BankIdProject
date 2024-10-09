package cz.bankid.interviewproject.controller

import cz.bankid.interviewproject.dto.RateDifference
import cz.bankid.interviewproject.service.ComparisonService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/currency-pairs")
class AvailableCurrenciesController(
    private val comparisonService: ComparisonService,
) {

    @GetMapping
    fun getCurrencyPairs(): Mono<List<String>> {
        return comparisonService.getCurrencyPairs()
    }

    @GetMapping("difference")
    fun getRateDifference(@RequestParam currencyPair: String): Mono<RateDifference> {
        val currencies = currencyPair.split("_")
        return comparisonService.getRateDifference(currencies[1])
    }

}