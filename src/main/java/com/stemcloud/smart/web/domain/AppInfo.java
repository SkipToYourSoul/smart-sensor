package com.stemcloud.smart.web.domain;

import javax.persistence.*;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/2
 * Description: base app info
 */
@Entity
@Table(name = "base_app_info")
public class AppInfo {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "app_name")
    private String name;

    @Column(name = "app_creator")
    private String creator;

    @Column(name = "app_description")
    private String description;

    @Column(name = "app_create_time")
    private String createTime;

    @Column(name = "app_modify_time")
    private String modifyTime;

    @Column(name = "is_deleted")
    private String isDeleted;

    public AppInfo(String name, String creator, String description, String createTime, String modifyTime, String isDeleted) {
        this.name = name;
        this.creator = creator;
        this.description = description;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
        this.isDeleted = isDeleted;
    }

    public AppInfo(){}

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }
}
