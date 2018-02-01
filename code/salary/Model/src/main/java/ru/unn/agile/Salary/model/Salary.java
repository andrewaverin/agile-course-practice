package ru.unn.agile.Salary.model;

public class Salary {

    public static final int MAX_HOUR_IN_MONTH = 160;
    public static final int MAX_OVERTIME_IN_MONTH = 584;
    public static final double INCOME_TAX = 0.13;
    public static final double ONE_HUNDRED_PERCENT = 1;
    public static final double OVERTIME_RATE = 2;

    public Salary(final int payInHour, final int hoursWorked,
                  final int overtimeHours, final int administrativeLeaveHours) {
        this.changeHourInMonth(hoursWorked);
        this.changePayInHour(payInHour);
        this.addOvertimes(overtimeHours);
        this.addAdministrativeLeave(administrativeLeaveHours);
    }

    public double calculateSalary() {
        return payInHour * (hoursWorked + overtimeHours * OVERTIME_RATE - administrativeLeaveHours)
                * (ONE_HUNDRED_PERCENT - INCOME_TAX);
    }

    public void changePayInHour(final int pay) {
        if (pay <= 0) {
            throw new IllegalArgumentException("Hour argument must be positive");
        } else {
            payInHour = pay;
        }
    }

    public void changeHourInMonth(final int hour) {
        if (hour <= 0) {
            throw new IllegalArgumentException("Hour argument must be positive");
        }
        if (hour > MAX_HOUR_IN_MONTH) {
            throw new IllegalArgumentException("Maximum value of working hours a month 160");
        } else {
            hoursWorked = hour;
        }
    }

    public void addAdministrativeLeave(final int additionalAdministrativeLeaveHours) {
        if (additionalAdministrativeLeaveHours < 0) {
            throw new IllegalArgumentException("Hour argument must be positive or zero");
        }
        if ((additionalAdministrativeLeaveHours + administrativeLeaveHours) > hoursWorked) {
            throw new IllegalArgumentException("AdministrativeLeave is greater than HourInMonth");
        } else {
            administrativeLeaveHours += additionalAdministrativeLeaveHours;
        }
    }

    public void addOvertimes(final int additionalOvertimeHours) {
        if (overtimeHours + additionalOvertimeHours < 0) {
            throw new IllegalArgumentException("Hour argument must be positive");
        }
        if ((additionalOvertimeHours + overtimeHours) > MAX_OVERTIME_IN_MONTH) {
            throw new IllegalArgumentException("Overtimes is more than free hours in month 5048");
        } else {
            overtimeHours += additionalOvertimeHours;
        }
    }

    public double getPayInHour() {
        return payInHour;
    }

    public int getAdministrativeLeave() {
        return administrativeLeaveHours;
    }

    public int getOvertime() {
        return overtimeHours;
    }

    public int getHourInMonth() {
        return hoursWorked;
    }

    private double payInHour;
    private int overtimeHours;
    private int hoursWorked;
    private int administrativeLeaveHours;
}
