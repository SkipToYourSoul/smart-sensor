package com.stemcloud.smart.web.domain.data;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/17
 * Description:
 */
@Entity
@Table(name = "zdc_sensor_camera_photo_data")
public class SensorCameraPhotos {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "sensor_id", nullable = false)
    private int sensorId;

    @Column(name = "video_id", nullable = false)
    private long videoId;

    @Column(name = "source_path", nullable = false)
    private String sourcePath;

    @Column(name = "time_in_video", nullable = false)
    private int timeInVideo;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @Column(name = "modify_time",
            updatable = false,
            columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date modifyTime;

    public long getId() {
        return id;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public long getVideoId() {
        return videoId;
    }

    public void setVideoId(long videoId) {
        this.videoId = videoId;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public int getTimeInVideo() {
        return timeInVideo;
    }

    public void setTimeInVideo(int timeInVideo) {
        this.timeInVideo = timeInVideo;
    }
}
