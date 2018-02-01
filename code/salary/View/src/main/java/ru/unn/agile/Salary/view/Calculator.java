package ru.unn.agile.Salary.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ru.unn.agile.Salary.viewModel.ViewModel;
import ru.unn.agile.Salary.infrastructure.TxtLogger;

public class Calculator {
    @FXML
    void initialize() {
        viewModel.setiLogger(new TxtLogger("./TxtLogger_Tests-lab3.log"));

        final ChangeListener<Boolean> focusListenerChange = new ChangeListener<Boolean>() {
            @Override
            public void changed(final ObservableValue<? extends Boolean> observable,
                                final Boolean oldValue, final Boolean newValue) {
                viewModel.onChangedFocus(oldValue, newValue);
            }
        };
        // Two-way binding hasn't supported by FXML yet, so place it in code-behind
        txtPay.textProperty().bindBidirectional(viewModel.payProperty());
        txtPay.focusedProperty().addListener(focusListenerChange);

        txtWorked.textProperty().bindBidirectional(viewModel.workProperty());
        txtWorked.focusedProperty().addListener(focusListenerChange);

        txtOver.textProperty().bindBidirectional(viewModel.overProperty());
        txtOver.focusedProperty().addListener(focusListenerChange);

        txtAdmin.textProperty().bindBidirectional(viewModel.adminProperty());
        txtAdmin.focusedProperty().addListener(focusListenerChange);

        btnCalc.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.calculate();
            }
        });
    }

    @FXML
    private ViewModel viewModel;
    @FXML
    private TextField txtPay;
    @FXML
    private TextField txtWorked;
    @FXML
    private TextField txtOver;
    @FXML
    private TextField txtAdmin;
    @FXML
    private Button btnCalc;
}
