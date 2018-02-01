package ru.unn.agile.Salary.viewModel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import ru.unn.agile.Salary.model.Salary;

import java.util.ArrayList;
import java.util.List;

public class ViewModel {
    // FXML needs default c-tor for binding
    public ViewModel() {
        init();
    }

    public ViewModel(final ILogger logger) {
        setiLogger(logger);
        init();
    }

    public final void setiLogger(final ILogger iLogger) {
        if (iLogger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }
        this.iLogger = iLogger;
    }

    public void calculate() {
        int over = 0, admin = 0;
        if (calculationDisabled.get()) {
            return;
        }
        over = overHours.get().isEmpty() ? 0 : Integer.parseInt(overHours.get());
        admin = adminLeave.get().isEmpty() ? 0 : Integer.parseInt(adminLeave.get());
        Salary salary = new Salary(Integer.parseInt(pay.get()), Integer.parseInt(workedHours.get()),
                over, admin);
        result.set(Double.toString(salary.calculateSalary()));
        status.set(Status.SUCCESS.toString());

        StringBuilder messageCalculate = new StringBuilder(LogMessages.CALCULATE_PRESSED);
        messageCalculate.append("Arguments: Pay = ").append(pay.get())
                .append("; Worked hours = ").append(workedHours.get())
                .append("; Over Hours = ").append(overHours.get())
                .append("; Admin leave = ").append(adminLeave.get());
        iLogger.log(messageCalculate.toString());
        updateLogs();
    }

    public void onChangedFocus(final Boolean oldValue, final Boolean newValue) {
        if (!oldValue && newValue) {
            return;
        }

        for (ValueCachingChangeListener changeListener : valueChangedListeners) {
            if (changeListener.isChanged()) {
                StringBuilder messageFocused = new StringBuilder(LogMessages.EDITING_FINISHED);
                messageFocused.append("Input arguments are: [")
                        .append(pay.get()).append("; ")
                        .append(workedHours.get()).append("; ")
                        .append(overHours.get()).append("; ")
                        .append(adminLeave.get()).append("]");
                iLogger.log(messageFocused.toString());
                updateLogs();

                changeListener.cache();
                break;
            }
        }
    }

    public StringProperty payProperty() {
        return pay;
    }

    public StringProperty workProperty() {
        return workedHours;
    }

    public StringProperty overProperty() {
        return overHours;
    }

    public StringProperty adminProperty() {
        return adminLeave;
    }

    public BooleanProperty calculationDisabledProperty() {
        return calculationDisabled;
    }

    public final boolean isCalculationDisabled() {
        return calculationDisabled.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public final String getStatus() {
        return status.get();
    }

    public StringProperty resultProperty() {
        return result;
    }

    public final String getResult() {
        return result.get();
    }

    public final String getLogs() {
        return logs.get();
    }

    public StringProperty logsProperty() {
        return logs;
    }

    public final List<String> getLog() {
        return iLogger.getLog();
    }

    private Status getInputStatus() {
        Status statusOfInput = Status.READY;
        if (pay.get().isEmpty() || workedHours.get().isEmpty()) {
            statusOfInput = Status.WAITING;
        }
        try {
            if (Double.parseDouble(pay.get()) < 0) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException iae) {
            statusOfInput = Status.BAD_FORMAT;
        }
        try {
            if (!pay.get().isEmpty()) {
                Double.parseDouble(pay.get());
            }
            if (!workedHours.get().isEmpty()) {
                Double.parseDouble(workedHours.get());
            }
            if (!overHours.get().isEmpty()) {
                Double.parseDouble(overHours.get());
            }
            if (!adminLeave.get().isEmpty()) {
                Double.parseDouble(adminLeave.get());
            }
        } catch (NumberFormatException numberException) {
            statusOfInput = Status.BAD_FORMAT;
        }
        return statusOfInput;
    }

    private class ValueChangeListener implements ChangeListener<String> {
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            status.set(getInputStatus().toString());
        }
    }

    private void updateLogs() {
        List<String> fullLog = iLogger.getLog();
        String record = new String("");
        for (String log : fullLog) {
            record += log + "\n";
        }
        logs.set(record);
    }

    private void init() {
        pay.set("");
        workedHours.set("");
        overHours.set("");
        adminLeave.set("");
        result.set("");
        status.set(Status.WAITING.toString());

        BooleanBinding couldCalculate = new BooleanBinding() {
            {
                super.bind(pay, workedHours, overHours, adminLeave);
            }

            @Override
            protected boolean computeValue() {
                return getInputStatus() == Status.READY;
            }
        };
        calculationDisabled.bind(couldCalculate.not());

        // Add listeners to the input text fields
        final List<StringProperty> fields = new ArrayList<StringProperty>() {
            {
                add(pay);
                add(workedHours);
                add(overHours);
                add(adminLeave);
            }
        };
        valueChangedListeners = new ArrayList<>();
        for (StringProperty field : fields) {
            final ValueCachingChangeListener listener = new ValueCachingChangeListener();
            field.addListener(listener);
            valueChangedListeners.add(listener);
        }
    }

    private final StringProperty pay = new SimpleStringProperty();
    private final StringProperty workedHours = new SimpleStringProperty();
    private final StringProperty overHours = new SimpleStringProperty();
    private final StringProperty adminLeave = new SimpleStringProperty();
    private final BooleanProperty calculationDisabled = new SimpleBooleanProperty();
    private final StringProperty result = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty logs = new SimpleStringProperty();

    private ILogger iLogger;
    private List<ValueCachingChangeListener> valueChangedListeners;

    private class ValueCachingChangeListener implements ChangeListener<String> {
        private String prevValue = new String("");
        private String curValue = new String("");

        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            if (oldValue.equals(newValue)) {
                return;
            }
            status.set(getInputStatus().toString());
            curValue = newValue;
        }

        public boolean isChanged() {
            return !prevValue.equals(curValue);
        }

        public void cache() {
            prevValue = curValue;
        }
    }
}

enum Status {
    WAITING("Please provide input data!"),
    READY("Press 'Calculate' or Enter!"),
    BAD_FORMAT("Bad format!"),
    SUCCESS("Success!");

    Status(final String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    private final String name;
}

final class LogMessages {
    public static final String CALCULATE_PRESSED = "Calculate. ";
    public static final String EDITING_FINISHED = "Updated input. ";

    private LogMessages() {
    }
}
