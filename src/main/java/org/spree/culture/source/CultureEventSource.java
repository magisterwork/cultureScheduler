package org.spree.culture.source;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
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

    private static final String REGION_LOCALES = "182,166,177,187,185,202,85,200,191";

    private ThreadLocal<SearchResult> previousResult = new InheritableThreadLocal<>();

    {
        setupObjectMapper();
    }

    @Override
    public Collection<CultureEvent> getNew() {
        try {
            int count = 100;
            int offset;
            if (previousResult.get() == null || previousResult.get().total <= previousResult.get().offset + previousResult.get().count) {
                offset = 0;
            } else {
                offset = previousResult.get().offset + count;
            }

            HttpRequest request = Unirest.get(CULTURE_URL)
                    .queryString("start", GregorianCalendar.getInstance().getTimeInMillis())
                    .queryString("locales", REGION_LOCALES)
                    .queryString("limit", count)
                    .queryString("sort", "-_id")
                    .queryString("offset", offset);
            HttpResponse<CultureResponse> response = request
                    .asObject(CultureResponse.class);
            checkStatus(response);
            previousResult.set(new SearchResult(response.getBody().total, offset, count));
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

    private static class SearchResult {

        public SearchResult(int total, int offset, int count) {
            this.total = total;
            this.offset = offset;
            this.count = count;
        }

        int total;
        int offset;
        int count;
    }
}
