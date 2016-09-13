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

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.Calendar;

public class AlarmsFragment extends Fragment {
  private PendingIntent pendingIntentThree;
  private PendingIntent pendingIntentThirty;
  private PendingIntent pendingIntentThreeIdle;
  private PendingIntent pendingIntentThirtyIdle;
  private AlarmManager manager;
  private SharedPreferences.Editor editor;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.alarms, parent, false);
    view.findViewById(R.id.startAlarmInexactThreeMinInterval)
        .setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View v) {
            startInexactThreeMinInterval();
          }
        });

    view.findViewById(R.id.startAlarmInexactThirtyMinInterval)
        .setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View v) {
            startInexactThirtyMinInterval();
          }
        });

    view.findViewById(R.id.startAlarmInexactThreeMinIntervalIdle)
        .setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View v) {
            startInexactThreeMinIntervalIdle();
          }
        });

    view.findViewById(R.id.startAlarmInexactThirtyMinIntervalIdle)
        .setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View v) {
            startInexactThirtyMinIntervalIdle();
          }
        });

    view.findViewById(R.id.stopAlarms).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        cancelAll();
      }
    });

    view.findViewById(R.id.startNetworkChanges).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        startNetworkChanges();
      }
    });

    view.findViewById(R.id.stopNetworkChanges).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        stopNetworkChanges();
      }
    });
    return view;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    manager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
  }

  private void startInexactThreeMinInterval() {
    Intent alarmIntentThree = new Intent(getActivity(), AlarmReceiver.class);
    alarmIntentThree.setAction(Constants.THREE);
    pendingIntentThree = PendingIntent.getBroadcast(getActivity(), 3, alarmIntentThree, 0);

    //int interval = 8000;
    //manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
    // interval, pendingIntent);

    long elapsedRealTime = SystemClock.elapsedRealtime();
    manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, elapsedRealTime + 60000,
        3 * 60000, pendingIntentThree);
    Log.d(Constants.TAG, Constants.ALARM_SET_AT + String.format(Constants.DATE_AND_TIME_FORMATTING,
        Calendar.getInstance()));
    FileSaver.saveStringToFile(
        Constants.ALARM_SET_AT + String.format(Constants.DATE_AND_TIME_FORMATTING,
            Calendar.getInstance()) + Constants.NEW_LINE,
        getActivity().getExternalFilesDir(null).toString() + Constants.INEXACT_THREE_MIN_LOG_FILE);
  }

  private void startInexactThirtyMinInterval() {
    Intent alarmIntentThirty = new Intent(getActivity(), AlarmReceiver.class);
    alarmIntentThirty.setAction(Constants.THIRTY);
    pendingIntentThirty = PendingIntent.getBroadcast(getActivity(), 30, alarmIntentThirty, 0);

    //int interval = 8000;
    //manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
    // interval, pendingIntent);

    long elapsedRealTime = SystemClock.elapsedRealtime();
    manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, elapsedRealTime + 60000,
        30 * 60000, pendingIntentThirty);
    Log.d(Constants.TAG, Constants.ALARM_SET_AT + String.format(Constants.DATE_AND_TIME_FORMATTING,
        Calendar.getInstance()));
    FileSaver.saveStringToFile(
        Constants.ALARM_SET_AT + String.format(Constants.DATE_AND_TIME_FORMATTING,
            Calendar.getInstance()) + Constants.NEW_LINE,
        getActivity().getExternalFilesDir(null).toString() + Constants.INEXACT_THIRTY_MIN_LOG_FILE);
  }

  @TargetApi(Build.VERSION_CODES.M) private void startInexactThreeMinIntervalIdle() {
    Intent alarmIntentThreeIdle = new Intent(getActivity(), AlarmReceiver.class);
    alarmIntentThreeIdle.setAction(Constants.THREE + Constants.IDLE);
    pendingIntentThreeIdle = PendingIntent.getBroadcast(getActivity(), 33, alarmIntentThreeIdle, 0);

    //int interval = 8000;
    //manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
    // interval, pendingIntent);
    //manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
    //        60000,
    //        3 * 60000, pendingIntentThreeIdle);

    long elapsedRealTime = SystemClock.elapsedRealtime();
    manager.setAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, elapsedRealTime + 3 * 60000,
        pendingIntentThreeIdle);
    Log.d(Constants.TAG, Constants.ALARM_SET_AT + String.format(Constants.DATE_AND_TIME_FORMATTING,
        Calendar.getInstance()));
    FileSaver.saveStringToFile(
        Constants.ALARM_SET_AT + String.format(Constants.DATE_AND_TIME_FORMATTING,
            Calendar.getInstance()) + Constants.NEW_LINE,
        getActivity().getExternalFilesDir(null).toString()
            + Constants.INEXACT_THREE_MIN_IDLE_LOG_FILE);
  }

  @TargetApi(Build.VERSION_CODES.M) private void startInexactThirtyMinIntervalIdle() {
    Intent alarmIntentThirtyIdle = new Intent(getActivity(), AlarmReceiver.class);
    alarmIntentThirtyIdle.setAction(Constants.THIRTY + Constants.IDLE);
    pendingIntentThirtyIdle =
        PendingIntent.getBroadcast(getActivity(), 303, alarmIntentThirtyIdle, 0);

    //int interval = 8000;
    //manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
    // interval, pendingIntent);
    //manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
    //      60000,
    //      30 * 60000, pendingIntentThirtyIdle);

    long elapsedRealTime = SystemClock.elapsedRealtime();
    manager.setAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, elapsedRealTime + 30 * 60000,
        pendingIntentThirtyIdle);
    Log.d(Constants.TAG, Constants.ALARM_SET_AT + String.format(Constants.DATE_AND_TIME_FORMATTING,
        Calendar.getInstance()));
    FileSaver.saveStringToFile(
        Constants.ALARM_SET_AT + String.format(Constants.DATE_AND_TIME_FORMATTING,
            Calendar.getInstance()) + Constants.NEW_LINE,
        getActivity().getExternalFilesDir(null).toString()
            + Constants.INEXACT_THIRTY_MIN_IDLE_LOG_FILE);
  }

  private void cancelAll() {
    manager.cancel(pendingIntentThree);
    manager.cancel(pendingIntentThirty);
    manager.cancel(pendingIntentThreeIdle);
    manager.cancel(pendingIntentThirtyIdle);
    Log.d(Constants.TAG,
        Constants.ALARM_CANCELED_AT + String.format(Constants.DATE_AND_TIME_FORMATTING,
            Calendar.getInstance()));
    FileSaver.saveStringToFile(
        Constants.ALARM_CANCELED_AT + String.format(Constants.DATE_AND_TIME_FORMATTING,
            Calendar.getInstance()) + Constants.NEW_LINE,
        getActivity().getExternalFilesDir(null).toString() + Constants.INEXACT_THREE_MIN_LOG_FILE);
    FileSaver.saveStringToFile(
        Constants.ALARM_CANCELED_AT + String.format(Constants.DATE_AND_TIME_FORMATTING,
            Calendar.getInstance()) + Constants.NEW_LINE,
        getActivity().getExternalFilesDir(null).toString() + Constants.INEXACT_THIRTY_MIN_LOG_FILE);
    FileSaver.saveStringToFile(
        Constants.ALARM_CANCELED_AT + String.format(Constants.DATE_AND_TIME_FORMATTING,
            Calendar.getInstance()) + Constants.NEW_LINE,
        getActivity().getExternalFilesDir(null).toString()
            + Constants.INEXACT_THREE_MIN_IDLE_LOG_FILE);
    FileSaver.saveStringToFile(
        Constants.ALARM_CANCELED_AT + String.format(Constants.DATE_AND_TIME_FORMATTING,
            Calendar.getInstance()) + Constants.NEW_LINE,
        getActivity().getExternalFilesDir(null).toString()
            + Constants.INEXACT_THIRTY_MIN_IDLE_LOG_FILE);
  }

  private void startNetworkChanges() {
    editor = obtainEditor();
    editor.putBoolean(Constants.IS_RECEIVING_NETWORK_CHANGES, true);
    editor.commit();
    Log.d(Constants.TAG,
        Constants.NETWORK_CHANGES_SET_AT + String.format(Constants.DATE_AND_TIME_FORMATTING,
            Calendar.getInstance()));
    FileSaver.saveStringToFile(
        Constants.NETWORK_CHANGES_SET_AT + String.format(Constants.DATE_AND_TIME_FORMATTING,
            Calendar.getInstance()) + Constants.NEW_LINE,
        getActivity().getExternalFilesDir(null).toString() + Constants.NETWORK_CHANGES_LOG_FILE);
  }

  private void stopNetworkChanges() {
    editor = obtainEditor();
    editor.putBoolean(Constants.IS_RECEIVING_NETWORK_CHANGES, false);
    editor.commit();
    Log.d(Constants.TAG,
        Constants.NETWORK_CHANGES_CANCELED_AT + String.format(Constants.DATE_AND_TIME_FORMATTING,
            Calendar.getInstance()));
    FileSaver.saveStringToFile(
        Constants.NETWORK_CHANGES_CANCELED_AT + String.format(Constants.DATE_AND_TIME_FORMATTING,
            Calendar.getInstance()) + Constants.NEW_LINE,
        getActivity().getExternalFilesDir(null).toString() + Constants.NETWORK_CHANGES_LOG_FILE);
  }

  private SharedPreferences.Editor obtainEditor() {
    return getActivity().getSharedPreferences(Constants.DA_REAL_FRAGMENTATION_PREFS,
        Context.MODE_PRIVATE).edit();
  }
}
