package com.stemcloud.smart.web.view.timeline;

import java.util.List;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/18
 * Description:
 */
public class Timeline {
    private Event title;
    private List<Event> events;
    private Era era;

    public Timeline(Event title, List<Event> events, Era era) {
        this.title = title;
        this.events = events;
        this.era = era;
    }

    public Event getTitle() {
        return title;
    }

    public void setTitle(Event title) {
        this.title = title;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public Era getEra() {
        return era;
    }

    public void setEra(Era era) {
        this.era = era;
    }

    @Override
    public String toString() {
        return "Timeline{" +
                "title=" + title.toString() +
                ", events=" + events.toString() +
                '}';
    }
}
