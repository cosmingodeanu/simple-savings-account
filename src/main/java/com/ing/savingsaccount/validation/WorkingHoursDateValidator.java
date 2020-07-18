package com.ing.savingsaccount.validation;

import com.ing.savingsaccount.config.AllowedTimeframe;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.LONG;

public class WorkingHoursDateValidator implements ConstraintValidator<WorkingHoursConstraint, Date> {

    private AllowedTimeframe allowedTimeframe;

    @Autowired
    public WorkingHoursDateValidator(AllowedTimeframe allowedTimeframe) {
        this.allowedTimeframe = allowedTimeframe;
    }

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String accountDayOfWeek = calendar.getDisplayName(DAY_OF_WEEK, LONG, Locale.ENGLISH);
        int accountHourOfDay = calendar.get(Calendar.HOUR_OF_DAY); // get hour in 24h format

        return allowedTimeframe.getStartHour() <= accountHourOfDay
                && allowedTimeframe.getEndHour() > accountHourOfDay
                && allowedTimeframe.getDays().stream().anyMatch(allowedDay -> allowedDay.equalsIgnoreCase(accountDayOfWeek));
    }
}
