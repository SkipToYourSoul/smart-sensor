package com.stemcloud.smart.web.view;

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

    private int unique_id = -2;

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
                calendar.get(Calendar.MINUTE));
    }

    public EventDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date endTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endTime);

        this.end_date = new EventDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE));
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
        /*if (null != start_date)
            toStr += "startDate=" + start_date.toString() + ",";
        if (null != end_date)
            toStr += "endDate=" + end_date.toString() + ",";*/
        if (null != media)
            toStr += "media=" + media.toString() + ",";
        toStr += "}";

        return toStr;
    }

    public class EventText{
        private String headline = "";
        private String text = "";

        private EventText(String headline, String text) {
            this.headline = headline;
            this.text = text;
        }

        public String getHeadline() {
            return headline;
        }

        public void setHeadline(String headline) {
            this.headline = headline;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
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

    public class EventDate{
        private int year = -1;
        private int month = -1;
        private int day = -1;
        private int hour = -1;
        private int minute = -1;

        private EventDate(int year, int month, int day, int hour, int minute) {
            this.year = year;
            this.month = month;
            this.day = day;
            this.hour = hour;
            this.minute = minute;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public int getHour() {
            return hour;
        }

        public void setHour(int hour) {
            this.hour = hour;
        }

        public int getMinute() {
            return minute;
        }

        public void setMinute(int minute) {
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

    public class EventMedia{
        private String url = "";
        private String caption = "";
        private String thumbnail = "";

        private EventMedia(String url, String caption, String thumbnail) {
            this.url = url;
            this.caption = caption;
            this.thumbnail = thumbnail;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getCaption() {
            return caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
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