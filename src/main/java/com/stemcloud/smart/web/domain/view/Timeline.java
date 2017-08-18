package com.stemcloud.smart.web.domain.view;

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
}
