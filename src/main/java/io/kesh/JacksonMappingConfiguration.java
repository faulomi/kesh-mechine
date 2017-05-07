package io.kesh;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.zalando.jackson.datatype.money.MoneyModule;

/**
 * Created by jerome on 5/6/17.
 */
public class JacksonMappingConfiguration {

    public static ObjectMapper createDefaultMapper() {
        final ObjectMapper result = new ObjectMapper();
        result.enable(SerializationFeature.INDENT_OUTPUT);
        result.registerModule(new JavaTimeModule());
        result.registerModule(new MoneyModule());
        result.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        result.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return result;
    }
}
