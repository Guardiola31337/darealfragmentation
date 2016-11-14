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
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import java.util.Calendar;

public class SensorsBatchingService extends Service {
  private static final int FIVE_SAMPLES_PER_SECOND = 200000;
  private static final int MAX_REPORT_LATENCY = 600000;
  private SensorManager sensorManager;
  private SensorReceiver sensorReceiver;
  private SparseArray<Store> stores;

  @TargetApi(Build.VERSION_CODES.KITKAT) @Override public void onCreate() {
    super.onCreate();
    initStores();
    sensorReceiver = new SensorReceiver(new OnSensorChanged() {
      @Override public void onChanged(String sample, int sensorType) {
        saveValues(sample, sensorType);
      }
    });
    sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    sensorManager.registerListener(sensorReceiver,
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), FIVE_SAMPLES_PER_SECOND,
        MAX_REPORT_LATENCY);
    sensorManager.registerListener(sensorReceiver,
        sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), FIVE_SAMPLES_PER_SECOND,
        MAX_REPORT_LATENCY);
  }

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    return START_STICKY;
  }

  @Nullable @Override public IBinder onBind(Intent intent) {
    return null;
  }

  @Override public void onDestroy() {
    sensorManager.unregisterListener(sensorReceiver);
    stores.get(Sensor.TYPE_ACCELEROMETER).end();
    stores.get(Sensor.TYPE_GYROSCOPE).end();
    super.onDestroy();
  }

  private void initStores() {
    stores = new SparseArray<>();
    String externalPath = getExternalFilesDir(null).toString();
    Calendar current = Calendar.getInstance();
    String accelSamplesStorePath =
        externalPath + String.format(Constants.ACCEL_BATCHING_SIX_MS_SAMPLES_LOG_FILE, current);
    String accelLogsStorePath = externalPath + Constants.ACCEL_BATCHING_SIX_MS_LOG_FILE;
    stores.put(Sensor.TYPE_ACCELEROMETER,
        new Store(Constants.ACCEL, Constants.SENSOR_HEADER, accelSamplesStorePath,
            accelLogsStorePath));
    String gyroSamplesStorePath =
        externalPath + String.format(Constants.GYRO_BATCHING_SIX_MS_SAMPLES_LOG_FILE, current);
    String gyroLogsStorePath = externalPath + Constants.GYRO_BATCHING_SIX_MS_LOG_FILE;
    stores.put(Sensor.TYPE_GYROSCOPE,
        new Store(Constants.GYROSCOPE, Constants.SENSOR_HEADER, gyroSamplesStorePath,
            gyroLogsStorePath));
  }

  private void saveValues(String values, int sensorType) {
    stores.get(sensorType).save(values);
  }
}
