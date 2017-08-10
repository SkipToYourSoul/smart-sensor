package com.stemcloud.smart.web.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/2
 * Description: sensor_data
 */
@Entity
@Table(name = "sensor_data")
public class SensorData {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "sensor_id")
    private int sensorId;

    @Column(name = "data_entry")
    private String entrance;

    @Column(name = "sensor_type")
    private int type;

    private String value;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    public SensorData(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
