package com.stemcloud.smart.web.domain.view;

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

    private class VideoConfig{
        private String poster;
        private List<Map> source;

        private VideoConfig(String poster, final String src){
            this.poster = poster;
            Map<String, String> map = new HashMap<String, String>(){{
                put("src", src);
                put("type", "video/mp4");
            }};
            List<Map> list = new ArrayList<Map>();
            list.add(map);
            this.source = list;
        }
    }
}
