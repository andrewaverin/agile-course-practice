package ru.unn.agile.Salary.infrastructure;

import ru.unn.agile.Salary.viewModel.ILogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TxtLogger implements ILogger {
    private static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
    private final BufferedWriter wrBuffer;
    private final String nameFile;

    private static String now() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW, Locale.ENGLISH);
        return sdf.format(calendar.getTime());
    }

    public TxtLogger(final String nameFile) {
        this.nameFile = nameFile;

        BufferedWriter logBufWriter = null;
        try {
            logBufWriter = new BufferedWriter(new FileWriter(this.nameFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
        wrBuffer = logBufWriter;
    }

    @Override
    public void log(final String s) {
        try {
            wrBuffer.write(now() + " > " + s);
            wrBuffer.newLine();
            wrBuffer.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader bufferedReader;
        ArrayList<String> log = new ArrayList<String>();
        try {
            bufferedReader = new BufferedReader(new FileReader(nameFile));
            String readLine = bufferedReader.readLine();

            while (readLine != null) {
                log.add(readLine);
                readLine = bufferedReader.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return log;
    }

}
