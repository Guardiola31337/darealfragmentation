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
import android.hardware.SensorManager;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.functions.Cancellable;
import io.reactivex.subscribers.DisposableSubscriber;
import java.sql.Timestamp;
import java.util.Date;

public class SensorRx {
  private final SensorManager sensorManager;

  public SensorRx(SensorManager sensorManager) {
    this.sensorManager = sensorManager;
  }

  public Flowable<String> obtainPublisher(final Sensor sensor, final int sensorType,
      final int samplingPeriodInUs) {
    return Flowable.create(new FlowableOnSubscribe<String>() {
      @Override public void subscribe(final FlowableEmitter<String> emitter) throws Exception {
        final SensorEventListener listener = new SensorEventListener() {
          @Override public void onSensorChanged(SensorEvent sensorEvent) {
            String sensorSample = onSampleReceived(sensorEvent, sensorType);
            emitter.onNext(sensorSample);
          }

          @Override public void onAccuracyChanged(Sensor sensor, int accuracy) {
          }
        };

        emitter.setCancellable(new Cancellable() {
          @Override public void cancel() throws Exception {
            sensorManager.unregisterListener(listener, sensor);
          }
        });

        sensorManager.registerListener(listener, sensor, samplingPeriodInUs);
      }
    }, FlowableEmitter.BackpressureMode.BUFFER);
  }

  public DisposableSubscriber<String> obtainSubscriber() {
    return new DisposableSubscriber<String>() {
      @Override public void onComplete() {
        System.out.println("onComplete\n");
      }

      @Override public void onError(Throwable e) {
        System.out.println("onError\n");
      }

      @Override public void onNext(String value) {
        System.out.println("onNext : value : " + value + "\n");
      }
    };
  }

  private String onSampleReceived(SensorEvent event, int sensorType) {
    float[] values = event.values;

    float x = values[0];
    float y = values[1];
    float z = values[2];

    long sensorTimestampInMillis =
        (new Date()).getTime() + (event.timestamp - System.nanoTime()) / 1000000L;
    long currentTimeInMillis = System.currentTimeMillis();

    String sensorValues = "";
    if (sensorType == Sensor.TYPE_ACCELEROMETER) {
      sensorValues = Constants.ACCEL_RECEIVED_AT;
    }

    if (sensorType == Sensor.TYPE_GYROSCOPE) {
      sensorValues = Constants.GYRO_RECEIVED_AT;
    }

    sensorValues += new Timestamp(currentTimeInMillis)
        + " Value (x: "
        + x
        + ", y: "
        + y
        + ", z: "
        + z
        + ") Timestamp: "
        + new Timestamp(sensorTimestampInMillis)
        + Constants.NEW_LINE;

    return sensorValues;
  }
}
