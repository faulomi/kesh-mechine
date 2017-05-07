package io.kesh;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.javamoney.moneta.CurrencyUnitBuilder;

import javax.money.CurrencySupplier;
import javax.money.CurrencyUnit;
import javax.money.Monetary;

/**
 * Created by jerome on 5/6/17.
 */
public class CurrencyPair {

    private final String baseCurrency;
    private final String quoteCurrency;


    public CurrencyPair(String baseCurrency, String quoteCurrency) {
        this.baseCurrency = baseCurrency;
        this.quoteCurrency = quoteCurrency;
    }


    public String getBaseCurrency() {
        return baseCurrency;
    }

    public String getQuoteCurrency() {
        return quoteCurrency;
    }

    @Override
    public String toString() {
        return baseCurrency + "/" + quoteCurrency;
    }
}
