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

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class SensorsService extends Service {
  private SensorManager sensorManager;
  private SensorReceiver sensorReceiver;

  @Override public void onCreate() {
    super.onCreate();
    sensorReceiver = new SensorReceiver(new OnSensorChanged() {
      @Override public void onChanged(String sample, int sensorType) {
        saveValues(sample, sensorType);
      }
    });
    sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    sensorManager.registerListener(sensorReceiver,
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), 200000);
    sensorManager.registerListener(sensorReceiver,
        sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), 200000);
  }

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    return START_STICKY;
  }

  @Nullable @Override public IBinder onBind(Intent intent) {
    return null;
  }

  @Override public void onDestroy() {
    super.onDestroy();
    sensorManager.unregisterListener(sensorReceiver);
  }

  private void saveValues(String values, int sensorType) {
    if (sensorType == Sensor.TYPE_ACCELEROMETER) {
      FileSaver.saveStringToFile(values,
          getExternalFilesDir(null).toString() + Constants.ACCEL_FIVE_HZ_LOG_FILE);
    }

    if (sensorType == Sensor.TYPE_GYROSCOPE) {
      FileSaver.saveStringToFile(values,
          getExternalFilesDir(null).toString() + Constants.GYRO_FIVE_HZ_LOG_FILE);
    }
  }
}
