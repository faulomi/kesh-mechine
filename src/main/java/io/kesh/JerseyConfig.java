package io.kesh;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.ApplicationPath;

/**
 * Created by jerome on 5/6/17.
 */
@ApplicationPath("/api")
@Configuration
public class JerseyConfig extends ResourceConfig {


    public JerseyConfig() {

        packages("io.kesh");
        register(CurrencyPairingResource.class);
    }

    @Bean
    ObjectMapper objectMapper() {
        return JacksonMappingConfiguration.createDefaultMapper();
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setObjectMapper(objectMapper());
        jsonConverter.setSupportedMediaTypes(
                Stream.concat(Stream.of(MediaType.valueOf("text/javascript")),
                              jsonConverter.getSupportedMediaTypes().stream()
                ).collect(
                        Collectors.toList()));
        return jsonConverter;
    }
}
