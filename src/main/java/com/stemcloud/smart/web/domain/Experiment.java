package com.stemcloud.smart.web.domain;

import javax.persistence.*;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/9/18
 * Description:
 */
@Entity
@Table(name = "zdc_base_experiment_info")
public class Experiment {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "app_id", nullable = false)
    private long appId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }
}
