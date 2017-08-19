package com.stemcloud.smart.web.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Calendar;
import java.util.Date;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/18
 * Description:
 */
public class Event {
    private EventText text = null;

    private EventDate start_date = null;

    private EventDate end_date = null;

    private EventMedia media = null;

    private int unique_id = -1;

    public EventText getText() {
        return text;
    }

    public void setText(String headline, String text) {
        this.text = new EventText(headline, text);
    }

    public EventMedia getMedia() {
        return media;
    }

    public void setMedia(String url, int time) {
        this.media = new EventMedia(url, "图片时间：" + time + "s", url);
    }

    public EventDate getStart_date() {
        return start_date;
    }

    public void setStart_date(Date startTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);

        this.start_date = new EventDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
    }

    public EventDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date endTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endTime);

        this.end_date = new EventDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
    }

    public int getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(int unique_id) {
        this.unique_id = unique_id;
    }

    @Override
    public String toString() {
        String toStr = "Event{";
        if (null != this.text)
            toStr += "text=" + text.toString() + ",";
        if (null != start_date)
            toStr += "startDate=" + start_date.toString() + ",";
        if (null != end_date)
            toStr += "endDate=" + end_date.toString() + ",";
        if (null != media)
            toStr += "media=" + media.toString() + ",";
        toStr += "}";

        return toStr;
    }
}