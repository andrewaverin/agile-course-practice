package ru.unn.agile.Salary.infrastructure;

import ru.unn.agile.Salary.viewModel.ViewModel;
import ru.unn.agile.Salary.viewModel.ViewModelTest;

public class ViewModelWithTxtLoggerTests extends ViewModelTest {
    @Override
    public void init() {
        TxtLogger realLogger =
            new TxtLogger("./ViewModel_with_TxtLogger_Tests-lab3.log");
        super.setExternalViewModel(new ViewModel(realLogger));
    }
}
