package com.stemcloud.smart.web.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/7/25
 * Description: base_sensor_info
 */
@Entity
@Table(name = "base_sensor_info")
public class SensorInfo {
    @Id
    private int id;

    @Column(name = "sensor_name")
    private String name;

    @Column(name = "sensor_creator")
    private String creator;

    @Column(name = "sensor_owner")
    private String owner;

    @Column(name = "type")
    private int type;

    private Double longitude;
    private Double latitude;

    @Column(name = "sensor_city")
    private String city;

    @Column(name = "sensor_description")
    private String description;

    @Column(name = "sensor_app_id")
    private int appId;

    @Column(name = "is_show")
    private int isShow;

    @Column(name = "sensor_create_time")
    private String createTime;

    @Column(name = "sensor_modify_time")
    private String modifyTime;

    public SensorInfo(int id, String name, String creator, String owner, int type, Double longitude, Double latitude, String city, String description, int appId, int isShow, String createTime) {
        this.id = id;
        this.name = name;
        this.creator = creator;
        this.owner = owner;
        this.type = type;
        this.longitude = longitude;
        this.latitude = latitude;
        this.city = city;
        this.description = description;
        this.appId = appId;
        this.isShow = isShow;
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public int getIsShow() {
        return isShow;
    }

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }
}
