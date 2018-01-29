package ru.unn.agile.MortgageCalculator.view;

import ru.unn.agile.MortgageCalculator.viewmodel.ViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MortgageCalculator {


    public static void main(String[] args) {
        JFrame frame = new JFrame("MortgageCalculator");
        frame.setContentPane(new MortgageCalculator(new ViewModel()).mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private MortgageCalculator(final ViewModel viewModel) {
        this.viewModel = viewModel;

        backBind();

        loadListOfPayments();

        txtPeriodNumber.setEnabled(viewModel.isDifferentiatedTypeStatusUpdate());

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                bind();
                MortgageCalculator.this.viewModel.calculate();
                backBind();
            }
        });

        cbPayment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                viewModel.setPaymentType((ViewModel.PaymentType) cbPayment.getSelectedItem());
                txtPeriodNumber.setEnabled(viewModel.isDifferentiatedTypeStatusUpdate());
            }
        });
    }

    private void loadListOfPayments() {
        ViewModel.PaymentType[] paymentType = ViewModel.PaymentType.values();
        cbPayment.setModel(new JComboBox<>(paymentType).getModel());
    }

    private void bind() {
        viewModel.setAmountOfCredit(setAmount.getText());
        viewModel.setInterestRate(setRate.getText());
        viewModel.setPeriod(setPeriod.getText());

        viewModel.setDurationOfCredit((String) cbPeriodType.getSelectedItem());
    }

    private void backBind() {
        textSum.setText(viewModel.getResultSum());
        lbStatus.setText(viewModel.getStatus());
    }

    private JPanel mainPanel;
    private JTextField setAmount;
    private JLabel nameLabel;
    private JTextField setRate;
    private JLabel labelPrice;
    private JLabel procent;
    private JTextField setPeriod;
    private JLabel data;
    private JLabel typePrice;
    private JButton calculateButton;
    private JTextField textSum;
    private JLabel labelSum;
    private JComboBox cbPayment;
    private JLabel lbStatus;
    private JLabel statusName;
    private JComboBox cbPeriodType;
    private JTextField txtPeriodNumber;
    private JLabel lbPeriodNumber;
    private ViewModel viewModel;
}
