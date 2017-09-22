package com.stemcloud.smart.web.domain;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/9/23
 * Description: experiment value sensor
 */
@Entity
@Table(name = "zdc_experiment_value_data")
public class ExpData {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "exp_id", nullable = false)
    private long expId;

    @Column(name = "recorder_id", nullable = false)
    private long recorderId;

    @Column(name = "sensor_id", nullable = false)
    private long sensorId;

    @Column(name = "data_flag", nullable = false)
    private String flag;

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

    public long getExpId() {
        return expId;
    }

    public void setExpId(long expId) {
        this.expId = expId;
    }

    public long getRecorderId() {
        return recorderId;
    }

    public void setRecorderId(long recorderId) {
        this.recorderId = recorderId;
    }

    public long getSensorId() {
        return sensorId;
    }

    public void setSensorId(long sensorId) {
        this.sensorId = sensorId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
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
