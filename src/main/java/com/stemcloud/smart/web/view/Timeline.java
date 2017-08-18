package com.stemcloud.smart.web.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/18
 * Description:
 */
public class Timeline {
    private Event title;
    private List<Event> events;

    public Timeline(Event title, List<Event> events) {
        this.title = title;
        this.events = events;
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

    @Override
    public String toString() {
        return "Timeline{" +
                "title=" + title.toString() +
                ", events=" + events.toString() +
                '}';
    }
}
