package io.kesh;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.*;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by jerome on 5/6/17.
 */
@Service
public class TickerService {


    public Stream<Ticker> getTickers(Stream<org.knowm.xchange.currency.CurrencyPair> currencyPairs, Class<? extends Exchange> exchangeClass) throws IOException {

        Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exchangeClass.getName());
        MarketDataService marketDataService = exchange.getMarketDataService();
        return currencyPairs.map(currencyPair -> {
            try {
                return marketDataService.getTicker(currencyPair);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }


}

