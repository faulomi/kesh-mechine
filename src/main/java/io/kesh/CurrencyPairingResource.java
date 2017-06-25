package io.kesh;

import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.Path;

/**
 * Created by jerome on 5/6/17.
 */
@Component
@Path("/")
public class CurrencyPairingResource {


    @Inject
    private Bx bx;




}
