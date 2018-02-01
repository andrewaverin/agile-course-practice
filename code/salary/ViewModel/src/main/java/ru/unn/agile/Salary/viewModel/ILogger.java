package ru.unn.agile.Salary.viewModel;

import java.util.List;

public interface ILogger {
    void log(String s);

    List<String> getLog();
}
