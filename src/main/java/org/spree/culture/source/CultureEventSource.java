package org.spree.culture.source;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.spree.core.event.EventSource;
import org.spree.culture.event.CultureEvent;
import org.spree.culture.exception.CanNotGetCultureEvents;
import org.spree.culture.util.dto.CultureDto;
import org.spree.culture.util.dto.CultureResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CultureEventSource implements EventSource<CultureEvent> {

    private static final Logger LOG = Logger.getLogger(CultureEventSource.class.getName());
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'hh:mm:ss";
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT);
    private static final String CULTURE_URL = "https://all.culture.ru/api/2.2/events";

    public static final int VOLOGDA_CITY_ID = 559;

    {
        setupObjectMapper();
    }

    @Override
    public Collection<CultureEvent> getNew() {
        try {
            HttpResponse<CultureResponse> response = Unirest.get(CULTURE_URL)
                    .queryString("start", GregorianCalendar.getInstance().getTimeInMillis())
                    .asObject(CultureResponse.class);
            checkStatus(response);
            List<CultureDto> events = response.getBody().events;
            return events.stream()
                    .map(CultureEvent::new)
                    .collect(Collectors.toList());
        } catch (UnirestException e) {
            throw new CanNotGetCultureEvents(e);
        }
    }

    private void setupObjectMapper() {
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void checkStatus(HttpResponse<CultureResponse> response) {
        if (response.getStatus() != 200) {
            throw new CanNotGetCultureEvents("Response status is " + response.getStatus());
        }
    }
}
