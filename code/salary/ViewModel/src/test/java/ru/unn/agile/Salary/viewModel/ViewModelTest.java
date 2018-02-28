package ru.unn.agile.Salary.viewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ViewModelTest {
    public void setExternalViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void init() {
        if (viewModel == null) {
            viewModel = new ViewModel(new FakeLogger());
        }
    }

    @After
    public void clear() {
        viewModel = null;
    }

    @Test
    public void canDefaultValuesSet() {
        assertEquals("", viewModel.payProperty().get());
        assertEquals("", viewModel.workProperty().get());
        assertEquals("", viewModel.overProperty().get());
        assertEquals("", viewModel.adminProperty().get());
        assertEquals("", viewModel.resultProperty().get());
        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsWaitingWhenCalculateWithEmptyFields() {
        viewModel.calculate();
        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canReportBadFormat() {
        viewModel.workProperty().set("abcd");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsWaitingIfNotEnoughCorrectData() {
        viewModel.payProperty().set("1223");
        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void calculateButtonIsDisabledInitially() {
        assertTrue(viewModel.calculationDisabledProperty().get());
    }

    @Test
    public void calculateButtonIsDisabledWhenFormatIsBad() {
        setInputData();
        viewModel.workProperty().set("abcd");
        assertTrue(viewModel.calculationDisabledProperty().get());
    }

    @Test
    public void statusIsReadyWhenFieldsAreFill() {
        setInputData();
        assertEquals(Status.READY.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void calculateButtonIsDisabledWithIncompleteInput() {
        viewModel.payProperty().set("1");
        assertTrue(viewModel.calculationDisabledProperty().get());
    }

    @Test
    public void statusIsBadFormatWhenCalculateSalaryWithNegativePay() {
        viewModel.payProperty().set("-10");
        viewModel.workProperty().set("5");
        viewModel.overProperty().set("5");
        viewModel.adminProperty().set("1");
        viewModel.calculate();
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void calculateButtonIsEnabledWithCorrectInput() {
        setInputData();
        assertFalse(viewModel.calculationDisabledProperty().get());
    }

    @Test
    public void canSetSuccessMessage() {
        setInputData();
        viewModel.calculate();
        assertEquals(Status.SUCCESS.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsReadyWhenSetProperData() {
        setInputData();
        assertEquals(Status.READY.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void salaryCalculationHasCorrectResultWhenAllDataIsFull() {
        viewModel.payProperty().set("10");
        viewModel.workProperty().set("5");
        viewModel.overProperty().set("2");
        viewModel.adminProperty().set("1");
        viewModel.calculate();
        assertEquals("69.6", viewModel.resultProperty().get());
    }

    @Test
    public void salaryCalculationHasCorrectResultWhenOverHoursIsEmpty() {
        viewModel.payProperty().set("10");
        viewModel.workProperty().set("5");
        viewModel.adminProperty().set("1");
        viewModel.calculate();
        assertEquals("34.8", viewModel.resultProperty().get());
    }

    @Test
    public void salaryCalculationHasCorrectResultWhenAdminLeaveHoursIsEmpty() {
        viewModel.payProperty().set("10");
        viewModel.workProperty().set("5");
        viewModel.overProperty().set("2");
        viewModel.calculate();
        assertEquals("78.3", viewModel.resultProperty().get());
    }

    @Test
    public void salaryCalculationHasCorrectResultWhenAdminLeaveAndOverHoursIsEmpty() {
        viewModel.payProperty().set("10");
        viewModel.workProperty().set("5");
        viewModel.calculate();
        assertEquals("43.5", viewModel.resultProperty().get());
    }

    @Test
    public void canViewModelConstructorThrowsExceptionWithNullLogger() {
        try {
            new ViewModel(null);
            fail("Exception wasn't thrown");
        } catch (IllegalArgumentException ex) {
            assertEquals("Logger parameter can't be null", ex.getMessage());
        } catch (Exception ex) {
            fail("Invalid exception type");
        }
    }

    @Test
    public void isLogEmptyInTheBeginning() {
        List<String> modelLog = viewModel.getLog();

        assertTrue(modelLog.isEmpty());
    }

    @Test
    public void logContainsProperMessageAfterCalculation() {
        setInputData();
        viewModel.calculate();
        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*" + LogMessages.CALCULATE_PRESSED + ".*"));
    }

    @Test
    public void islogContainsInputArgumentsAfterCalculation() {
        setInputData();

        viewModel.calculate();

        String testMessage = viewModel.getLog().get(0);
        assertTrue(testMessage.matches(".*" + viewModel.payProperty().get()
                + ".*" + viewModel.workProperty().get()
                + ".*" + viewModel.overProperty().get()
                + ".*" + viewModel.adminProperty().get() + ".*"));
    }

    @Test
    public void canPutSeveralLogMessages() {
        setInputData();

        viewModel.calculate();
        viewModel.calculate();
        viewModel.calculate();

        assertEquals(3, viewModel.getLog().size());
    }

    @Test
    public void argumentsAreCorrectlyLogged() {
        setInputData();

        viewModel.onChangedFocus(Boolean.TRUE, Boolean.FALSE);

        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*" + LogMessages.EDITING_FINISHED
                + "Input arguments are: \\["
                + viewModel.payProperty().get() + "; "
                + viewModel.workProperty().get() + "; "
                + viewModel.overProperty().get() + "; "
                + viewModel.adminProperty().get() + "\\]"));
    }
    
    @Test
    public void isStatusSUCCESS() {
        setInputData();
        viewModel.calculate();

        assertEquals("Success!", viewModel.getStatus());
    }
    
    @Test
    public void isLogsNotEmptyBeforeCalculate() {
        setInputData();
        viewModel.calculate();

        assertTrue(viewModel.getLogs() != null);
    }
    
    @Test
    public void isStatusReady() {
        setInputData();

        assertEquals("Press 'Calculate' or Enter!", viewModel.getStatus());
    }

    @Test
    public void calculateIsNotCalledWhenButtonIsDisabled() {
        viewModel.calculate();

        assertTrue(viewModel.getLog().isEmpty());
    }

    private void setInputData() {
        viewModel.payProperty().set("10");
        viewModel.workProperty().set("5");
        viewModel.overProperty().set("2");
        viewModel.adminProperty().set("1");
    }

    private ViewModel viewModel;
}
