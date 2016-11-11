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

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import io.reactivex.Flowable;
import io.reactivex.subscribers.DisposableSubscriber;

public class SensorsFragment extends Fragment {
  private static final int FIVE_SAMPLES_PER_SECOND = 200000;
  private Context context;
  private SensorRx sensorRx;
  private DisposableSubscriber<String> subscriber;
  private Flowable<String> publisher;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.sensors, parent, false);
    view.findViewById(R.id.startSensors).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        startSensorsService();
      }
    });

    view.findViewById(R.id.stopSensors).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        stopSensorsService();
      }
    });

    view.findViewById(R.id.startSensorsRx).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        startSensorsRx();
      }
    });

    view.findViewById(R.id.stopSensorsRx).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        stopSensorsRx();
      }
    });
    return view;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    context = getActivity();
    initializeRx();
  }

  private void startSensorsService() {
    Intent intent = new Intent(context, SensorsService.class);
    context.startService(intent);
  }

  private void stopSensorsService() {
    if (context != null) {
      Intent intent = new Intent(context, SensorsService.class);
      context.stopService(intent);
    }
  }

  private void initializeRx() {
    SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    sensorRx = new SensorRx(sensorManager);
    subscriber = sensorRx.obtainSubscriber();
    Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    publisher = sensorRx.obtainPublisher(accelerometer, Sensor.TYPE_ACCELEROMETER, FIVE_SAMPLES_PER_SECOND);
  }

  private void startSensorsRx() {
    if (subscriber.isDisposed()) {
      subscriber = sensorRx.obtainSubscriber();
    }
    publisher.subscribe(subscriber);
  }

  private void stopSensorsRx() {
    if (subscriber != null) {
      subscriber.dispose();
    }
  }
}
