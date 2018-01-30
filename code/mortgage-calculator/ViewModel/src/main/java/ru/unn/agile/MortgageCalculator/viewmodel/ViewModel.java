package ru.unn.agile.MortgageCalculator.viewmodel;

import ru.unn.agile.MortgageCalculator.Model.MortgageCalculator;
import ru.unn.agile.MortgageCalculator.Model.MortgageCalculator.PeriodType;

public class ViewModel {

    public ViewModel() {
        this.amountOfCredit = "0.0";
        this.interestRate = "0.0";
        this.period = "0";
        this.paymentType = PaymentType.Annuity;
        this.durationOfCredit = "Month";
        this.resultSum = "";
        this.status = Status.WAITING;
        periodNumber = "0";
    }

    public void setAmountOfCredit(final String amountOfCredit) {
        this.amountOfCredit = amountOfCredit;
    }

    public void setPeriodNumber(final String periodNumber) {
        this.periodNumber = periodNumber;
    }

    public void setInterestRate(final String interestRate) {
        this.interestRate = interestRate;
    }

    public void setDurationOfCredit(final String durationOfCredit) {
        this.durationOfCredit = durationOfCredit;
    }

    public void setPaymentType(final PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public void setPeriod(final String period) {
        this.period = period;
    }

    public String getResultSum() {
        return resultSum;
    }

    public String getStatus() {
        return status;
    }

    public void processTextChanged() {
        parseInput();
    }

    private boolean isCorrectPeriodNumber() {
        if (!isNumbersInString(periodNumber)) {
            return false;
        } else {
            int periodNumberInteger = Integer.parseInt(periodNumber);
            if (periodNumberInteger <= 0 || periodNumberInteger > Integer.parseInt(period)) {
                return false;
            }
        }
        return true;
    }

    public void calculate() {
        if (!parseInput()) {
            return;
        }

        MortgageCalculator mortageCalculator = new MortgageCalculator(amountOfCredit,
                period, interestRate);

        int indexPeriodNumber = Integer.parseInt(periodNumber) - 1;
        int payment = mortageCalculator.getPayments(paymentType.toString(),
                indexPeriodNumber, getDurationOfCredit());

        if (payment != 0) {
            this.resultSum = Integer.toString(payment);
            status = Status.SUCCESS;
        } else {
            status = Status.BAD_FORMAT;
        }
    }

    public final class Status {
        public static final String WAITING = "Please provide input data";
        public static final String READY = "Press 'Calculate' or Enter";
        public static final String BAD_FORMAT = "Bad format";
        public static final String SUCCESS = "Success";

        private Status() {
        }
    }

    public enum PaymentType {
        Annuity("annuity"),
        Differentiated("differentiated");
        private final String name;

        PaymentType(final String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }
    }

    public boolean isDifferentiatedTypeStatusUpdate() {
        return (paymentType == PaymentType.Differentiated);
    }

    private String amountOfCredit;
    private String interestRate;
    private String period;
    private String resultSum;
    private PaymentType paymentType;
    private String status;
    private String durationOfCredit;
    private String periodNumber;

    private boolean parseInput() {
        if (!isCorrectData()) {
            status = Status.BAD_FORMAT;
            return false;
        }

        if (isDifferentiatedTypeStatusUpdate() && !isCorrectPeriodNumber()) {
            status = Status.BAD_FORMAT;
            return false;
        }

        try {
            if (!amountOfCredit.isEmpty()) {
                Float.parseFloat(amountOfCredit);
            }
            if (!interestRate.isEmpty()) {
                Float.parseFloat(interestRate);
            }
            if (!period.isEmpty()) {
                Integer.parseInt(period);
            }
        } catch (Exception e) {
            status = Status.BAD_FORMAT;
            return false;
        }

        if (isInputAvailable()) {
            status = Status.READY;
        } else {
            status = Status.WAITING;
        }

        return isInputAvailable();
    }

    private boolean isInputAvailable() {
        return !amountOfCredit.isEmpty() && !interestRate.isEmpty() && !period.isEmpty();
    }

    private PeriodType getDurationOfCredit() {
        PeriodType periodType = null;

        switch (durationOfCredit) {
            case "Month":
                periodType = PeriodType.month;
                break;
            case "Year":
                periodType = PeriodType.year;
                break;
            default:
                break;
        }
        return periodType;
    }

    private boolean isCorrectFloatValue(final String value) {
        return (isNumbersInString(value) && Float.parseFloat(value) > 0.0f);
    }

    private boolean isCorrectIntValue(final String value) {
        return (isNumbersInString(value) && Integer.parseInt(value) > 0.0f);
    }

    private boolean isCorrectData() {
        return (isCorrectFloatValue(amountOfCredit) && isCorrectFloatValue(interestRate)
                && isCorrectIntValue(period));
    }

    private boolean isNumbersInString(final String line) {
        return line.matches("-?\\d+(\\.\\d+)?");
    }
}
