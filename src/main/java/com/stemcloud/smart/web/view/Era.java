package com.stemcloud.smart.web.view;

import java.util.Calendar;
import java.util.Date;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/19
 * Description:
 */
public class Era {
    private EventDate start_date;
    private EventDate end_date;

    public EventDate getStart_date() {
        return start_date;
    }

    public void setStart_date(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);

        this.start_date = new EventDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
    }

    public EventDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);

        this.end_date = new EventDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
    }
}
