package com.stemcloud.smart.web.domain;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/2
 * Description: base app info
 */
@Entity
@Table(name = "zdc_base_app_info")
public class AppInfo {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "app_name", nullable = false)
    private String name;

    @Column(name = "app_creator", nullable = false)
    private String creator;

    @Column(name = "app_description")
    private String description;

    @Column(length = 20, precision = 6)
    private Double longitude;

    @Column(length = 20, precision = 6)
    private Double latitude;

    @Column(name = "app_city")
    private String city;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "app_create_time", updatable = false)
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @Column(name = "app_modify_time",
            updatable = false,
            columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date modifyTime;

    @Column(name = "is_deleted")
    private int isDeleted = 0;

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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Date getCreateTime() {
        return createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }
}
