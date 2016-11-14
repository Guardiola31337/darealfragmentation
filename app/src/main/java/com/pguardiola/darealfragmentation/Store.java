package com.pguardiola.darealfragmentation;

import java.util.Calendar;

public class Store {
  private static final String TIME_LOG = "%s%s%s\n";
  private static final int MAX_ROWS = 10;
  private final String sensorName;
  private final String samplesFilePath;
  private final String logsFilePath;
  private final StringBuilder buffer;
  private int counter;

  public Store(String sensorName, String header, String samplesFilePath, String logsFilePath) {
    this.sensorName = sensorName;
    this.samplesFilePath = samplesFilePath;
    this.logsFilePath = logsFilePath;
    saveLog(String.format(TIME_LOG, sensorName, Constants.SENSOR_SET_AT,
        String.format(Constants.DATE_AND_TIME_FORMATTING, Calendar.getInstance())));
    buffer = new StringBuilder();
    buffer.append(header);
  }

  public void save(String row) {
    buffer.append(row);
    counter = (counter + 1) % MAX_ROWS;
    if (counter == 0) {
      flushSamples();
    }
  }

  public void end() {
    saveLog(String.format(TIME_LOG, sensorName, Constants.SENSOR_STOPPED_AT,
        String.format(Constants.DATE_AND_TIME_FORMATTING, Calendar.getInstance())));
    flushSamples();
  }

  private void saveLog(String log) {
    FileSaver.saveStringToFile(log, logsFilePath);
  }

  private void flushSamples() {
    FileSaver.saveStringToFile(buffer.toString(), samplesFilePath);
    buffer.delete(0, buffer.length());
  }
}
