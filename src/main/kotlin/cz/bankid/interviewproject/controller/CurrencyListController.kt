package cz.bankid.interviewproject.controller

import cz.bankid.interviewproject.dto.CurrencyExchangeRate
import cz.bankid.interviewproject.service.CnbExchangeRateService
import cz.bankid.interviewproject.service.FrankfurterExchangeRateService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/czech-crown-exchange-rates")
class CurrencyListController(
    private val cnbExchangeRateService: CnbExchangeRateService,
    private val frankfurterExchangeRateService: FrankfurterExchangeRateService
) {

    @GetMapping("/cnb")
    fun getCnbExchangeRate(): Mono<CurrencyExchangeRate> {
        return cnbExchangeRateService.getExchangeRate()
    }

    @GetMapping("/frankfurter")
    fun getFrankfurterExchangeRate(): Mono<CurrencyExchangeRate> {
        return frankfurterExchangeRateService.getExchangeRate()
    }
}