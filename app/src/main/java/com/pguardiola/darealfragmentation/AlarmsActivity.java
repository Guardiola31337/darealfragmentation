/*
 * Copyright (C) 2016 Pablo Guardiola Sánchez.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pguardiola.darealfragmentation;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import java.util.Calendar;

public class AlarmsActivity extends Activity {
  private PendingIntent pendingIntent;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.alarms);

    Intent alarmIntent = new Intent(AlarmsActivity.this, AlarmReceiver.class);
    pendingIntent = PendingIntent.getBroadcast(AlarmsActivity.this, 0, alarmIntent, 0);

    findViewById(R.id.startAlarmInexactThreeMinInterval).setOnClickListener(
        new View.OnClickListener() {
          @Override public void onClick(View v) {
            startInexactThreeMinInterval();
          }
        });

    findViewById(R.id.startAlarmInexactThirtyMinInterval).setOnClickListener(
        new View.OnClickListener() {
          @Override public void onClick(View v) {
            startInexactThirtyMinInterval();
          }
        });

    findViewById(R.id.stopAlarms).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        cancelAll();
      }
    });
  }

  public void startInexactThreeMinInterval() {
    AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

    //int interval = 8000;
    //manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
    // interval, pendingIntent);
    manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 60000, 3 * 60000,
        pendingIntent);
    Log.d(Constants.TAG, Constants.ALARM_SET_AT + String.format(Constants.DATE_AND_TIME_FORMATTING,
        Calendar.getInstance()));
    FileSaver.saveStringToFile(
        Constants.ALARM_SET_AT + String.format(Constants.DATE_AND_TIME_FORMATTING,
            Calendar.getInstance()) + Constants.NEW_LINE,
        getExternalFilesDir(null).toString() + Constants.INEXACT_THREE_MIN_LOG_FILE);
  }

  public void startInexactThirtyMinInterval() {
    AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

    //int interval = 8000;
    //manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
    // interval, pendingIntent);
    manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 60000, 30 * 60000,
        pendingIntent);
    Log.d(Constants.TAG, Constants.ALARM_SET_AT + String.format(Constants.DATE_AND_TIME_FORMATTING,
        Calendar.getInstance()));
    FileSaver.saveStringToFile(
        Constants.ALARM_SET_AT + String.format(Constants.DATE_AND_TIME_FORMATTING,
            Calendar.getInstance()) + Constants.NEW_LINE,
        getExternalFilesDir(null).toString() + Constants.INEXACT_THIRTY_MIN_LOG_FILE);
  }

  public void cancelAll() {
    AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    manager.cancel(pendingIntent);
    Log.d(Constants.TAG,
        Constants.ALARM_CANCELED_AT + String.format(Constants.DATE_AND_TIME_FORMATTING,
            Calendar.getInstance()));
    FileSaver.saveStringToFile(
        Constants.ALARM_CANCELED_AT + String.format(Constants.DATE_AND_TIME_FORMATTING,
            Calendar.getInstance()) + Constants.NEW_LINE,
        getExternalFilesDir(null).toString() + Constants.INEXACT_THREE_MIN_LOG_FILE);
    FileSaver.saveStringToFile(
        Constants.ALARM_CANCELED_AT + String.format(Constants.DATE_AND_TIME_FORMATTING,
            Calendar.getInstance()) + Constants.NEW_LINE,
        getExternalFilesDir(null).toString() + Constants.INEXACT_THIRTY_MIN_LOG_FILE);
  }

  public void startAt10() {
    AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    int interval = 1000 * 60 * 20;

        /* Set the alarm to start at 10:30 AM */
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(System.currentTimeMillis());
    calendar.set(Calendar.HOUR_OF_DAY, 10);
    calendar.set(Calendar.MINUTE, 30);

        /* Repeating on every 20 minutes interval */
    manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval,
        pendingIntent);
  }
}