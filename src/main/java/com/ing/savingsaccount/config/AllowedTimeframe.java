package com.ing.savingsaccount.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AllowedTimeframe {

    @Value("#{'${businesstime.days}'.split(',')}")
    private List<String> days;

    @Value("${businesstime.startHour}")
    private Integer startHour;

    @Value("${businesstime.endHour}")
    private Integer endHour;

    public List<String> getDays() {
        return days;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }

    public Integer getStartHour() {
        return startHour;
    }

    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }

    public Integer getEndHour() {
        return endHour;
    }

    public void setEndHour(Integer endHour) {
        this.endHour = endHour;
    }

    @Override
    public String toString() {
        return "AllowedTimeframe{" +
                "days=" + days +
                ", startHour=" + startHour +
                ", endHour=" + endHour +
                '}';
    }
}
