package io.kesh;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.stream.Stream;

/**
 * Created by jerome on 5/6/17.
 */

@FeignClient(name = "bx", url = "https://bx.in.th/api")
public interface Bx {

    @RequestMapping(path = "/pairing", method = RequestMethod.GET, consumes = "application/json")
    String rawCurrencyPairs();

    @RequestMapping(path = "/orderbook/")
    String orderBook(@RequestParam("pairing") int pairingId);

    default Stream<KeshMechineUI.CurrencyPairTicker> currencyPairTickers(CurrencyPair... currencyPair) throws IOException {
        ObjectMapper objectMapper = JacksonMappingConfiguration.createDefaultMapper();
        return Stream.of(currencyPair).map(pair -> {
            final JsonNode rootNode;
            try {
                rootNode = objectMapper.readTree(orderBook(pair.pairId));
                BigDecimal latestBid = new BigDecimal(rootNode.get("bids").get(0).get(0).asText());
                BigDecimal latestAsk = new BigDecimal(rootNode.get("asks").get(0).get(0).asText());
                BigDecimal bidVolume = new BigDecimal(rootNode.get("bids").get(0).get(1).asText());
                BigDecimal askVolume = new BigDecimal(rootNode.get("asks").get(0).get(1).asText());

                return new KeshMechineUI.CurrencyPairTicker(pair.toString(), latestBid, latestAsk,
                                                            "BX",
                                                            bidVolume.max(askVolume));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

    }


    enum CurrencyPair {

        BTC_THB(1) {
            @Override
            public String toString() {
                return "BTC/THB";
            }
        },
        ETH_THB(21) {
            @Override
            public String toString() {
                return "ETH/THB";
            }

        };

        private final int pairId;

        private CurrencyPair(int pairId) {
            this.pairId = pairId;
        }


    }

}

