package com.stemcloud.smart.web.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/7/25
 * Description: base_sensor_info
 */
@Entity
@Table(name = "zdc_base_sensor_info")
public class SensorInfo {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "sensor_name", nullable = false)
    private String name;

    @Column(name = "sensor_code", nullable = false)
    private String code;

    @Column(name = "sensor_creator", nullable = false)
    private String creator;

    @Column(name = "sensor_type", nullable = false)
    private int type;

    @OneToOne
    private SensorConfig sensorConfig;

    @Column(length = 20, precision = 6)
    private Double longitude;

    @Column(length = 20, precision = 6)
    private Double latitude;

    @Column(name = "sensor_city")
    private String city;

    @Column(name = "sensor_description")
    private String description;

    @Column(name = "sensor_app_id")
    private long appId;

    @Column(name = "sensor_exp_id", nullable = false)
    private long expId;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(updatable = false)
    private Date createTime;

    @Column(name = "is_deleted")
    private int isDeleted = 0;

    @Column(name = "is_monitor")
    private int isMonitor = 0;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @Column(updatable = false,
            columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date modifyTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public SensorConfig getSensorConfig() {
        return sensorConfig;
    }

    public void setSensorConfig(SensorConfig sensorConfig) {
        this.sensorConfig = sensorConfig;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    public long getExpId() {
        return expId;
    }

    public void setExpId(long expId) {
        this.expId = expId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public int getIsMonitor() {
        return isMonitor;
    }

    public void setIsMonitor(int isMonitor) {
        this.isMonitor = isMonitor;
    }

    public Date getModifyTime() {
        return modifyTime;
    }
}
