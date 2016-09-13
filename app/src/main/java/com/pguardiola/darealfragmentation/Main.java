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

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

public class Main extends AppCompatActivity {
  private BottomBar bottomBar;
  private AlarmsFragment alarms;
  private SensorsFragment sensors;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    alarms = new AlarmsFragment();
    sensors = new SensorsFragment();

    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    ft.replace(R.id.customContent, alarms);
    ft.commit();

    bottomBar = BottomBar.attach(this, savedInstanceState);
    bottomBar.setItems(R.menu.bottom_bar_menu);
    bottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
      @Override public void onMenuTabSelected(@IdRes int menuItemId) {
        if (menuItemId == R.id.bottomBarAlarmsItem) {
          FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
          ft.replace(R.id.customContent, alarms);
          ft.commit();
        } else if (menuItemId == R.id.bottomBarSensorsItem) {
          FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
          ft.replace(R.id.customContent, sensors);
          ft.commit();
        }
      }

      @Override public void onMenuTabReSelected(@IdRes int menuItemId) {
        if (menuItemId == R.id.bottomBarAlarmsItem) {
        } else if (menuItemId == R.id.bottomBarSensorsItem) {
        }
      }
    });
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    bottomBar.onSaveInstanceState(outState);
  }
}
