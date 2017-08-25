package com.stemcloud.smart.web.view;

import com.stemcloud.smart.web.view.chart.TimeSeries;

import java.util.Date;
import java.util.List;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/24
 * Description:
 */
public class Chart {
    private Date startTime;
    private Date endTime;
    private int flag;
    private List<TimeSeries> timeSeries;

    public Chart(Date startTime, Date endTime, int flag, List<TimeSeries> timeSeries) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.flag = flag;
        this.timeSeries = timeSeries;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public List<TimeSeries> getTimeSeries() {
        return timeSeries;
    }

    public void setTimeSeries(List<TimeSeries> timeSeries) {
        this.timeSeries = timeSeries;
    }
}
