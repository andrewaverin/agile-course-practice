package ru.unn.agile.Salary.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SalaryTest {

    public static final double DELTA_CALCULATION = 0.0001;

    @Test
    public void canCreateSalary() {
        Salary sal = new Salary(43, 3, 7, 1);
        assertNotNull(sal);
    }

    @Test
    public void canGetPayInHour() {
        Salary sal = new Salary(6, 2, 0, 0);
        assertEquals(6, sal.getPayInHour(), DELTA_CALCULATION);
    }

    @Test
    public void canGetHourInMonth() {
        Salary sal = new Salary(6, 2, 0, 0);
        assertEquals(2, sal.getHourInMonth());
    }

    @Test
    public void canGetOvertime() {
        Salary sal = new Salary(6, 2, 5, 0);
        assertEquals(5, sal.getOvertime());
    }

    @Test
    public void canGetAdministrativeLeave() {
        Salary sal = new Salary(6, 10, 5, 8);
        assertEquals(8, sal.getAdministrativeLeave());
    }

    @Test
    public void isCalculateSalaryCorrectWithoutAdministrativeLeave() {
        Salary sal = new Salary(2, 5, 5, 0);
        double result = sal.calculateSalary();
        assertEquals(26.1, result, DELTA_CALCULATION);
    }

    @Test
    public void isCalculateSalaryCorrectWithoutOverTime() {
        Salary sal = new Salary(2, 10, 0, 6);
        double result = sal.calculateSalary();
        assertEquals(6.96, result, DELTA_CALCULATION);
    }

    @Test
    public void isCalculateSalaryCorrect() {
        Salary sal = new Salary(2, 10, 5, 6);
        double result = sal.calculateSalary();
        assertEquals(24.36, result, DELTA_CALCULATION);
    }

    @Test
    public void canAddAdministrativeLeave() {
        Salary sal = new Salary(2, 9, 6, 4);
        sal.addAdministrativeLeave(4);
        assertEquals(8, sal.getAdministrativeLeave());
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenAddAdministrativeHaveNegativeArgument() {
        Salary sal = new Salary(1, 1, 2, 1);
        sal.addAdministrativeLeave(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenAddAdministrativeMoreThanHourInMonth() {
        Salary sal = new Salary(6, 4, 5, 0);
        sal.addAdministrativeLeave(5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenAdministrativeLeaveMoreThanHourInMonth() {
        Salary sal = new Salary(1, 6, 9, 4);
        sal.addAdministrativeLeave(4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenAddOvertimeHaveNegativeArgument() {
        Salary sal = new Salary(1, 6, 9, 3);
        sal.addOvertimes(-10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSumAddAdministrativeLeaveAndHourMoreHourInMonth() {
        Salary sal = new Salary(1, 6, 9, 4);
        sal.addAdministrativeLeave(4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenAddOverTimeMoreThanMaxValue() {
        Salary sal = new Salary(1, 2, 0, 0);
        sal.addOvertimes(5049);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenAddOvertimeAndHourMoreThanMaxValueHourInMonth() {
        Salary sal = new Salary(1, 2, 3000, 0);
        sal.addOvertimes(3000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenPayInHourIsZero() {
        final Salary sal = new Salary(5, 1, 2, 0);
        sal.changePayInHour(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenPayInHourHasNegativeArgument() {
        final Salary sal = new Salary(1, 2, 0, 0);
        sal.changePayInHour(-2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenHourInMonthHasNegativeArgument() {
        final Salary sal = new Salary(1, 2, 1, 1);
        sal.changeHourInMonth(-2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenHourInMonthMoreThanMaxValueWorkingHours() {
        final Salary sal = new Salary(2, 1, 1, 1);
        sal.changeHourInMonth(200);
    }

    @Test
    public void isCalculationSalaryCorrectAfterAddingOvertime() {
        Salary sal = new Salary(2, 10, 5, 6);
        sal.addOvertimes(3);
        double result = sal.calculateSalary();
        assertEquals(34.8, result, DELTA_CALCULATION);
    }

    @Test
    public void isCalculationSalaryCorrectAfterAddingAdministrativeLeave() {
        Salary sal = new Salary(2, 10, 5, 4);
        sal.addAdministrativeLeave(2);
        double result = sal.calculateSalary();
        assertEquals(24.36, result, DELTA_CALCULATION);
    }
}
