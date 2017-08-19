package com.stemcloud.smart.web.view;

public class EventText{
    private String headline = "";
    private String text = "";

    public EventText(String headline, String text) {
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