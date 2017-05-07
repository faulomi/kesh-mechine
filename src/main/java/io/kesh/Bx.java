package io.kesh;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import feign.Param;

/**
 * Created by jerome on 5/6/17.
 */

@FeignClient(name = "bx", url = "https://bx.in.th/api")
public interface Bx {

    @RequestMapping(path = "/pairing", method = RequestMethod.GET, consumes = "application/json")
    String rawCurrencyPairs();


    @RequestMapping(path = "/orderbook?pairing={pairingId}")
    String orderBook(Long pairingId);


    default List<CurrencyPair> currencyPairs() throws IOException {
        ObjectMapper objectMapper = JacksonMappingConfiguration.createDefaultMapper();
        List<CurrencyPair> pairs = new ArrayList<>();

        final JsonNode rootNode = objectMapper.readTree(rawCurrencyPairs());

        for (JsonNode pair : rootNode) {
            pairs.add(new CurrencyPair(pair.get("primary_currency").asText(),
                                       pair.get("secondary_currency").asText()));
        }
        return pairs;
    }

}

