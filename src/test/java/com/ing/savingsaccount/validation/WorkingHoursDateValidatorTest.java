package com.ing.savingsaccount;

import com.ing.savingsaccount.config.AllowedTimeframe;
import com.ing.savingsaccount.validation.WorkingHoursDateValidator;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class WorkingHoursDateValidatorTest {

    @Test
    public void whenAccountIsValid_then_returnTrue() {
        AllowedTimeframe allowedTimeframe = new AllowedTimeframe(List.of("Monday", "Tuesday"), 8, 20);
        WorkingHoursDateValidator dateValidator = new WorkingHoursDateValidator(allowedTimeframe);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.JULY, 13, 12, 30);
        assertTrue(dateValidator.isValid(calendar.getTime(), null));
    }
}
