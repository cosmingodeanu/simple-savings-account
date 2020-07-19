package com.ing.savingsaccount.validation;

import com.ing.savingsaccount.config.AllowedTimeframe;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WorkingHoursDateValidatorTest {

    @Test
    public void testDate_timeframe_scenarios() {
        AllowedTimeframe allowedTimeframe = new AllowedTimeframe();
        allowedTimeframe.setDays(List.of("Monday", "Tuesday"));
        allowedTimeframe.setStartHour(8);
        allowedTimeframe.setEndHour(20);
        WorkingHoursDateValidator dateValidator = new WorkingHoursDateValidator(allowedTimeframe);
        Calendar calendar = Calendar.getInstance();

        //within constraints
        calendar.set(2020, Calendar.JULY, 13, 12, 30);
        assertTrue(dateValidator.isValid(calendar.getTime(), null));

        //outside day
        calendar.set(2020, Calendar.JULY, 12, 12, 30);
        assertFalse(dateValidator.isValid(calendar.getTime(), null));

        //outside hours
        calendar.set(2020, Calendar.JULY, 13, 7, 30);
        assertFalse(dateValidator.isValid(calendar.getTime(), null));
    }
}
