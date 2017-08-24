package com.stemcloud.smart.web.view.timeline;

public class EventMedia{
    private String url = "";
    private String caption = "";
    private String thumbnail = "";
    int timeInVideo = 0;

    public EventMedia(String url, String caption, String thumbnail, int timeInVideo) {
        this.url = url;
        this.caption = caption;
        this.thumbnail = thumbnail;
        this.timeInVideo = timeInVideo;
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

    public int getTimeInVideo() {
        return timeInVideo;
    }

    public void setTimeInVideo(int timeInVideo) {
        this.timeInVideo = timeInVideo;
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