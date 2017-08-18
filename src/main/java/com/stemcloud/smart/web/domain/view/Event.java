package com.stemcloud.smart.web.domain.view;

import java.util.Calendar;
import java.util.Date;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/18
 * Description:
 */
public class Event {
    private EventText text;
    private EventDate startDate;
    private EventDate endDate;
    private EventMedia media;
    private int uniqueId;

    public Event(Date startTime, String url, int uniqueId, int time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);

        this.startDate = new EventDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE));
        this.media = new EventMedia(url, "图片时间：" + time + "s", url);
        this.uniqueId = uniqueId;
    }

    public Event(String headline, String text){
        this.text = new EventText(headline, text);
    }

    @Override
    public String toString() {
        return "Event{" +
                "text=" + text.toString() +
                ", startDate=" + startDate.toString() +
                ", endDate=" + endDate.toString() +
                ", media='" + media.toString() + '\'' +
                ", uniqueId=" + uniqueId +
                '}';
    }

    private class EventText{
        private String headline;
        private String text;

        private EventText(String headline, String text) {
            this.headline = headline;
            this.text = text;
        }

        @Override
        public String toString() {
            return "EventText{" +
                    "headline='" + headline + '\'' +
                    ", text='" + text + '\'' +
                    '}';
        }
    }

    private class EventDate{
        private int year;
        private int month;
        private int day;
        private int hour;
        private int minute;

        private EventDate(int year, int month, int day, int hour, int minute) {
            this.year = year;
            this.month = month;
            this.day = day;
            this.hour = hour;
            this.minute = minute;
        }

        @Override
        public String toString() {
            return "EventDate{" +
                    "year=" + year +
                    ", month=" + month +
                    ", day=" + day +
                    ", hour=" + hour +
                    ", minute=" + minute +
                    '}';
        }
    }

    private class EventMedia{
        private String url;
        private String caption;
        private String thumbnail;

        private EventMedia(String url, String caption, String thumbnail) {
            this.url = url;
            this.caption = caption;
            this.thumbnail = thumbnail;
        }

        @Override
        public String toString() {
            return "EventMedia{" +
                    "url='" + url + '\'' +
                    ", caption='" + caption + '\'' +
                    ", thumbnail='" + thumbnail + '\'' +
                    '}';
        }
    }
}