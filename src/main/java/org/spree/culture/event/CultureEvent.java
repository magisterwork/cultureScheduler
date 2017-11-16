package org.spree.culture.event;

import org.spree.core.category.Category;
import org.spree.core.category.CategoryImpl;
import org.spree.core.event.Event;
import org.spree.culture.util.dto.CultureDto;

import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

public class CultureEvent implements Event {

    public static final String CULTURE_ID = "CULTURE";
    private CultureDto source;

    public CultureEvent(CultureDto material) {
        this.source = material;
    }

    @Override
    public String getName() {
        return source.name;
    }

    @Override
    public String getDescription() {
        return source.description;
    }

    @Override
    public Calendar getStartDate() {
        GregorianCalendar date = new GregorianCalendar();
        date.setTimeInMillis(source.start);
        return date;
    }

    @Override
    public Calendar getFinishDate() {
        GregorianCalendar date = new GregorianCalendar();
        date.setTimeInMillis(source.end);
        return date;
    }

    @Override
    public String getImageUrl() {
        return "https://all.culture.ru/uploads/" + source.image.name;
    }

    @Override
    public String getExtId() {
        return String.valueOf(source._id);
    }

    @Override
    public String getSystemId() {
        return CULTURE_ID;
    }

    @Override
    public List<Category> getCategories() {
        return Collections.singletonList(new CategoryImpl(source.category.name));
    }
}
