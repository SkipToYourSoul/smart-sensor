package com.stemcloud.smart.web.domain;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/2
 * Description: value sensor
 */
@Entity
@Table(name = "zdc_sensor_value_data")
public class SensorData {
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

    @Column(name = "data_flag", nullable = false)
    private String flag;

    @Column(name = "sensor_type", nullable = false)
    private int type;

    @Column(nullable = false)
    private String value;

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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getDataTime() {
        return dataTime;
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }
}
