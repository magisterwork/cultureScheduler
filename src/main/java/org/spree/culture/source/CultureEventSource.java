package org.spree.culture.source;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.spree.core.event.EventSource;
import org.spree.culture.event.CultureEvent;
import org.spree.culture.exception.CanNotGetCultureEvents;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Logger;

public class CultureEventSource implements EventSource<CultureEvent> {

    private static final Logger LOG = Logger.getLogger(CultureEventSource.class.getName());
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'hh:mm:ss";
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT);
    private static final String CULTURE_URL = "http://www.culture.ru/api/v1/materials";

    public static final int VOLOGDA_CITY_ID = 559;

    @Override
    public Collection<CultureEvent> getNew() {
        try {
            HttpResponse<JsonNode> jsonNodeHttpResponse = Unirest.get(CULTURE_URL)
                    .queryString("type", "event")
                    .queryString("city", VOLOGDA_CITY_ID)
                    .queryString("start_date_from", SIMPLE_DATE_FORMAT.format(new Date()))
                    .asJson();
            return null;
        } catch (UnirestException e) {
            throw new CanNotGetCultureEvents(e);
        }
    }
}
