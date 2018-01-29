package ru.unn.agile.MortgageCalculator.viewmodel;

import ru.unn.agile.MortgageCalculator.viewmodel.ViewModel.Status;
import ru.unn.agile.MortgageCalculator.viewmodel.ViewModel.PaymentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ViewModelTests {

    @Before
    public void setUp() {
        viewModel = new ViewModel();
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultValue() {
        assertEquals("", viewModel.getResultSum());
    }

    @Test
    public void canNotSetInterestRateValue() {
        viewModel.setInterestRate("de");
        assertEquals(Status.BAD_FORMAT, viewModel.getStatus());
    }

    @Test
    public void canNotSetCreditTermValue() {
        viewModel.setPeriod("ew");
        assertEquals(Status.BAD_FORMAT, viewModel.getStatus());
    }

    @Test
    public void canNotSetAmountOfCreditValue() {
        viewModel.setAmountOfCredit("a");
        assertEquals(Status.BAD_FORMAT, viewModel.getStatus());
    }

    @Test
    public void canSetAmountOfCreditValue() {
        viewModel.setAmountOfCredit("1000");
        assertEquals(Status.WAITING, viewModel.getStatus());
    }

    @Test
    public void canSetCreditTermValue() {
        fillInputFields();
        viewModel.processTextChanged();
        assertEquals(Status.READY, viewModel.getStatus());
    }

    @Test
    public void canSetAllValues() {
        fillInputFields();
        viewModel.processTextChanged();
        assertEquals(Status.READY, viewModel.getStatus());
    }

    @Test
    public void canCalculateAnnuityMortgagePerMonth() {
        fillInputFields();
        viewModel.setDurationOfCredit("Month");
        viewModel.calculate();
        assertEquals("336", viewModel.getResultSum());
    }

    @Test
    public void canCalculateAnnuityMortgagePerYear() {
        fillInputFields();
        viewModel.setDurationOfCredit("Year");
        viewModel.calculate();
        assertEquals("29", viewModel.getResultSum());
    }

    @Test
    public void canCalculateDifferentiatedMortgagePerMonth() {
        fillInputFields();
        viewModel.setDurationOfCredit("Month");
        viewModel.setPeriodNumber("2");
        viewModel.setPaymentType(PaymentType.Differentiated);
        viewModel.calculate();
        assertEquals("336", viewModel.getResultSum());
    }

    @Test
    public void canCalculateDifferentiatedMortgagePerYear() {
        fillInputFields();
        viewModel.setPeriodNumber("2");
        viewModel.setDurationOfCredit("Year");
        viewModel.setPaymentType(PaymentType.Differentiated);
        viewModel.calculate();
        assertEquals("32", viewModel.getResultSum());
    }

    @Test
    public void canNotCalculateDifferentiatedMortgagePerYear() {
        viewModel.setAmountOfCredit("1000");
        viewModel.setInterestRate("0.05");
        viewModel.setPeriodNumber("0");
        viewModel.setDurationOfCredit("Year");
        viewModel.setPaymentType(PaymentType.Differentiated);
        viewModel.calculate();
        assertEquals(Status.BAD_FORMAT, viewModel.getStatus());
    }

    @Test
    public void canNotCalculateAnnuityMortgagePerYear() {
        viewModel.setAmountOfCredit("1000");
        viewModel.setInterestRate("0.05");
        viewModel.setPeriod("0");
        viewModel.setDurationOfCredit("Year");
        viewModel.calculate();
        assertEquals(Status.BAD_FORMAT, viewModel.getStatus());
    }


    private ViewModel viewModel;

    private void fillInputFields() {
        viewModel.setAmountOfCredit("1000");
        viewModel.setPeriod("3");
        viewModel.setInterestRate("0.05");
    }
}
