package com.stemcloud.smart.web.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/18
 * Description:
 */
public class Video {
    private VideoConfig option;
    private Timeline timeline;

    public Video(String poster, String src, Timeline timeline){
        this.option = new VideoConfig(poster, src);
        this.timeline = timeline;
    }

    public VideoConfig getOption() {
        return option;
    }

    public void setOption(VideoConfig option) {
        this.option = option;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public class VideoConfig {
        private String poster;
        private List<Map> sources;

        private VideoConfig(String poster, final String src){
            this.poster = poster;
            Map<String, String> map = new HashMap<String, String>(){{
                put("src", src);
                put("type", "video/mp4");
            }};
            List<Map> list = new ArrayList<Map>();
            list.add(map);
            this.sources = list;
        }

        @Override
        public String toString() {
            return "VideoConfig{" +
                    "poster='" + poster + '\'' +
                    ", sources=" + sources +
                    '}';
        }

        public String getPoster() {
            return poster;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }

        public List<Map> getSources() {
            return sources;
        }

        public void setSources(List<Map> sources) {
            this.sources = sources;
        }
    }

    @Override
    public String toString() {
        return "Video{" +
                "option=" + option.toString() +
                ", timeline=" + timeline.toString() +
                '}';
    }
}
