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

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import java.util.Locale;

public class SensorReceiver implements SensorEventListener {
    private static final String SENSOR_ROW = "%d;%d;%f;%f;%f\n";
    private final OnSensorChanged sensorChanged;

    public SensorReceiver(OnSensorChanged sensorChanged) {
        this.sensorChanged = sensorChanged;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();

        if (sensorType == Sensor.TYPE_ACCELEROMETER) {
            onSampleReceived(event, sensorType);
        }

        if (sensorType == Sensor.TYPE_GYROSCOPE) {
            onSampleReceived(event, sensorType);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void onSampleReceived(SensorEvent event, int sensorType) {
        float[] values = event.values;

        float x = values[0];
        float y = values[1];
        float z = values[2];

        long sensorRawTimestampInNanos = event.timestamp;
        long currentTimeInMillis = System.currentTimeMillis();

        String sensorValues =
                String.format(Locale.US, SENSOR_ROW, currentTimeInMillis, sensorRawTimestampInNanos, x, y, z);
        sensorChanged.onChanged(sensorValues, sensorType);
    }
}
