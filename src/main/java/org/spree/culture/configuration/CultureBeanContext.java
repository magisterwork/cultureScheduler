package org.spree.culture.configuration;

import org.spree.core.entities.JpaEvent;
import org.spree.culture.scheduling.EventScheduler;
import org.spree.culture.source.CultureEventSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;

@Configuration
public class CultureBeanContext {

    @Autowired
    private CrudRepository<JpaEvent, JpaEvent.EventId> jpaEventCrudRepository;

    @Bean
    public CultureEventSource cultureEventSource() {
        return new CultureEventSource();
    }

    @Bean
    public EventScheduler eventScheduler() {
        return new EventScheduler(jpaEventCrudRepository, cultureEventSource());
    }
}
