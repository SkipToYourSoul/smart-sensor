package com.stemcloud.smart.web.view.chart;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/23
 * Description: echart time series
 */
public class TimeSeries {
    private List<Object> value;

    public TimeSeries(Date date, String value) {
        List<Object> list = new ArrayList<Object>();
        list.add(date);
        list.add(value);
        this.value = list;
    }

    public List<Object> getValue() {
        return value;
    }

    public void setValue(List<Object> value) {
        this.value = value;
    }
}
