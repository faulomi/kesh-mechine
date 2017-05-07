package io.kesh;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.kraken.KrakenExchange;

import java.io.IOException;
import java.math.BigDecimal;

import javax.inject.Inject;

/**
 * Created by jerome on 5/6/17.
 */
@SpringUI
public class KeshMechineUI extends UI {

    @Inject
    private TickerService tickerService;

    private static CurrencyPairTicker map(String exchange, Ticker ticker) {
        return new CurrencyPairTicker(ticker.getCurrencyPair().toString(), ticker.getBid(),
                                      ticker.getAsk(), exchange, ticker.getVolume());


    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        Grid<CurrencyPairTicker> currencyPairTickerGrid = new Grid<>(CurrencyPairTicker.class);
        currencyPairTickerGrid.setSizeFull();
        currencyPairTickerGrid.setColumnOrder("exchange", "currencyPair", "bid", "volume", "ask");

        setContent(
                new VerticalLayout(currencyPairTickerGrid, new Button("Refresh",
                                                                      clickEvent -> {
                                                                          try {
                                                                              currencyPairTickerGrid
                                                                                      .setItems(
                                                                                              map("Kraken",
                                                                                                  tickerService
                                                                                                          .getTicker(
                                                                                                                  CurrencyPair.BTC_EUR,
                                                                                                                  KrakenExchange.class)));
                                                                          } catch (IOException e) {
                                                                              e.printStackTrace();
                                                                          }
                                                                      })));
    }

    public static class CurrencyPairTicker {

        private final String currencyPair;
        private final BigDecimal bid;
        private final BigDecimal ask;
        private final String exchange;
        private final BigDecimal volume;

        public CurrencyPairTicker(String currencyPair, BigDecimal bid, BigDecimal ask, String exchange, BigDecimal volume) {
            this.currencyPair = currencyPair;
            this.bid = bid;
            this.ask = ask;
            this.exchange = exchange;
            this.volume = volume;
        }

        public String getCurrencyPair() {
            return currencyPair;
        }

        public BigDecimal getBid() {
            return bid;
        }

        public BigDecimal getAsk() {
            return ask;
        }

        public BigDecimal getVolume() {
            return volume;
        }

        public String getExchange() {

            return exchange;
        }
    }
}
