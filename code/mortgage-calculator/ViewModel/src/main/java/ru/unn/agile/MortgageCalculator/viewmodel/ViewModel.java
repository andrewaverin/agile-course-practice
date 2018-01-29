package ru.unn.agile.MortgageCalculator.viewmodel;

import ru.unn.agile.MortgageCalculator.Model.MortgageCalculator;
import ru.unn.agile.MortgageCalculator.Model.MortgageCalculator.PeriodType;

public class ViewModel {

    public ViewModel() {
        this.AmountOfCredit = "";
        this.InterestRate = "";
        this.period = "0";
        this.paymentType = PaymentType.Annuity;
        this.durationOfCredit = "Month";
        this.resultSum = "";
        this.status = Status.WAITING;
        periodNumber = 0;
    }

    public void setAmountOfCredit(final String AmountOfCredit) {
        if (!isNumbersInString(AmountOfCredit)) {
            status = Status.BAD_FORMAT;
            return;
        }

        this.AmountOfCredit = AmountOfCredit;
    }

    public void setPeriodNumber(final String PeriodNumber) {
        try {
            int PeriodNumberInteger = Integer.parseInt(PeriodNumber);
            if (PeriodNumberInteger > 0 && PeriodNumberInteger <= Integer.parseInt(period)) {
                this.periodNumber = Integer.parseInt(PeriodNumber);
            } else {
                status = Status.BAD_FORMAT;
                return;
            }
        } catch (Exception IllegalArgumentException) {
            status = Status.BAD_FORMAT;
            return;
        }
    }

    public void setInterestRate(final String InterestRate) {
        if (!isNumbersInString(InterestRate)) {
            status = Status.BAD_FORMAT;
            return;
        }

        this.InterestRate = InterestRate;
    }

    public void setDurationOfCredit(final String durationOfCredit) {
        this.durationOfCredit = durationOfCredit;
    }

    public void setPaymentType(final PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public void setPeriod(final String period) {
        if (!isNumbersInString(AmountOfCredit) || Integer.parseInt(period) <= 0) {
            status = Status.BAD_FORMAT;
            return;
        }

        this.period = period;
    }

    public String getResultSum() {
        return resultSum;
    }

    public String getAmountOfCredit() {
        return this.period;
    }

    public String getStatus() {
        return status;
    }

    public String getPeriodNumber() {
        return Integer.toString(periodNumber);
    }

    public void processTextChanged() {
        parseInput();
    }

    public void calculate() {
        if (!parseInput()) {
            return;
        }

        MortgageCalculator mortageCalculator = new MortgageCalculator(AmountOfCredit,
                period, InterestRate);
        int payment = 0;

        int indexPeriodNumber = periodNumber - 1;
        payment = mortageCalculator.getPayments(paymentType.toString(),
                indexPeriodNumber, getDurationOfCredit());

        if (payment != 0) {
            this.resultSum = Integer.toString(payment);
            status = Status.SUCCESS;
        } else {
            status = Status.BAD_FORMAT;
        }
        this.resultSum = Integer.toString(payment);
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
        if (paymentType == PaymentType.Differentiated) {
            return true;
        } else {
            return false;
        }
    }

    private String AmountOfCredit;
    private String InterestRate;
    private String period;
    private String resultSum;
    private PaymentType paymentType;
    private String status;
    private String durationOfCredit;
    private int periodNumber;

    private boolean parseInput() {
        if (Integer.parseInt(period) <= 0) {
            status = Status.BAD_FORMAT;
            return false;
        }

        try {
            if (!AmountOfCredit.isEmpty()) {
                Float.parseFloat(AmountOfCredit);
            }
            if (!InterestRate.isEmpty()) {
                Float.parseFloat(InterestRate);
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
        return !AmountOfCredit.isEmpty() && !InterestRate.isEmpty() && !period.isEmpty();
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
        }
        return periodType;
    }

    private boolean isNumbersInString(String line) {
        return line.matches("-?\\d+(\\.\\d+)?");
    }
}
