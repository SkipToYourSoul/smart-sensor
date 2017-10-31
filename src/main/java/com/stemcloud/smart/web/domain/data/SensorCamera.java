package com.stemcloud.smart.web.domain.data;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/17
 * Description: camera sensor
 */
@Entity
@Table(name = "zdc_sensor_camera_data")
public class SensorCamera {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "sensor_id", nullable = false)
    private long sensorId;

    @Column(name = "exp_id", nullable = false)
    private long expId;

    @Column(name = "app_id", nullable = false)
    private long appId;

    @Column(name = "data_entry", nullable = false)
    private String entrance;

    @Column(name = "sensor_type", nullable = false)
    private int type;

    @Column(name = "source_path", nullable = false)
    private String sourcePath;

    @Column(nullable = false)
    private int duration;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_time", nullable = false)
    private Date dataTime;

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

    public long getSensorId() {
        return sensorId;
    }

    public void setSensorId(long sensorId) {
        this.sensorId = sensorId;
    }

    public long getExpId() {
        return expId;
    }

    public void setExpId(long expId) {
        this.expId = expId;
    }

    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    public String getEntrance() {
        return entrance;
    }

    public void setEntrance(String entrance) {
        this.entrance = entrance;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getDataTime() {
        return dataTime;
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }
}
