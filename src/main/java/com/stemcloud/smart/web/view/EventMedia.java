package com.stemcloud.smart.web.view;

public class EventMedia{
    private String url = "";
    private String caption = "";
    private String thumbnail = "";

    public EventMedia(String url, String caption, String thumbnail) {
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