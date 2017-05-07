package io.kesh;

import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Created by jerome on 5/6/17.
 */
@Component
@Path("/")
public class CurrencyPairingResource {


    @Inject
    private Bx bx;

    @GET
    @Path("currencyPairs")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CurrencyPair> currencyPairs() throws IOException {
        return bx.currencyPairs();
    }

    @GET
    @Path("orderBook")
    @Produces(MediaType.APPLICATION_JSON)
    public String orderBook(@QueryParam("pairId") Long pairId) {
        return bx.orderBook(pairId);
    }


}
