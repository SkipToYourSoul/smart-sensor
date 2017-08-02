package com.stemcloud.smart.web.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/2
 * Description: base app info
 */
@Entity
@Table(name = "base_app_info")
public class AppInfo {
    @Id
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

    public AppInfo(int id, String name, String creator, String description, String createTime) {
        this.id = id;
        this.name = name;
        this.creator = creator;
        this.description = description;
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
}
