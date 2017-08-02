package com.stemcloud.smart.web.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/2
 * Description: sensor_data
 */
@Entity
@Table(name = "sensor_data")
public class SensorData {
    @Id
    private int id;

    @Column(name = "sensor_id")
    private int sensorId;

    @Column(name = "data_entry")
    private int entrance;

    @Column(name = "sensor_type")
    private int type;

    private String value;

    private String timestamp;

    public SensorData(int id, int sensorId, int entrance, int type, String value) {
        this.id = id;
        this.sensorId = sensorId;
        this.entrance = entrance;
        this.type = type;
        this.value = value;
    }

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

    public int getEntrance() {
        return entrance;
    }

    public void setEntrance(int entrance) {
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
