package com.stemcloud.smart.web.domain.base;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/10/31
 * Description:
 * @author liye
 */
@Entity
@Table(name = "zdc_base_track_info")
public class TrackInfo {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private ExperimentInfo experiment;

    /*@OneToOne
    private SensorInfo sensorInfo;*/

    @Column(name = "is_deleted")
    private int isDeleted = 0;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "create_time", updatable = false)
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @Column(name = "modify_time",
            updatable = false,
            columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date modifyTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ExperimentInfo getExperimentInfo() {
        return experiment;
    }

    public void setExperimentInfo(ExperimentInfo experimentInfo) {
        this.experiment = experimentInfo;
    }

    /*public SensorInfo getSensorInfo() {
        return sensorInfo;
    }

    public void setSensorInfo(SensorInfo sensorInfo) {
        this.sensorInfo = sensorInfo;
    }*/

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }
}
