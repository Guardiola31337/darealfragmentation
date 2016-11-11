package com.pguardiola.darealfragmentation;

import java.util.Calendar;

public class Store {
    private static final String HEADER =
            "CURRENT_TIMESTAMP;SENSOR_RAW_TIMESTAMP_NANOS;VALUE_X;VALUE_Y;VALUE_Z\n";
    private static final String TIME_LOG = "%s%s%s\n";
    private static final int MAX_ROWS = 10;
    private final String sensorName;
    private final String filePath;
    private final StringBuilder buffer;
    private int counter;

    public Store(String sensorName, String filePath) {
        this.sensorName = sensorName;
        this.filePath = filePath;
        buffer = new StringBuilder();
        buffer.append(String.format(TIME_LOG, sensorName, Constants.SENSOR_SET_AT,
                String.format(Constants.DATE_AND_TIME_FORMATTING, Calendar.getInstance())));
        buffer.append(HEADER);
    }

    public void save(String row) {
        buffer.append(row);
        counter = (counter + 1) % MAX_ROWS;
        if (counter == 0) {
            flush();
        }
    }

    public void end() {
        buffer.append(String.format(TIME_LOG, sensorName, Constants.SENSOR_STOPPED_AT,
                String.format(Constants.DATE_AND_TIME_FORMATTING, Calendar.getInstance())));
        flush();
    }

    private void flush() {
        FileSaver.saveStringToFile(buffer.toString(), filePath);
        buffer.delete(0, buffer.length());
    }
}
