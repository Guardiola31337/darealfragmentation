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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import java.util.Calendar;

public class ConnectivityChangeReceiver extends BroadcastReceiver {

  @Override public void onReceive(Context context, Intent intent) {
    debugIntent(intent, Constants.TAG);
    SharedPreferences prefs =
        context.getSharedPreferences(Constants.DA_REAL_FRAGMENTATION_PREFS, Context.MODE_PRIVATE);
    boolean isReceivingNetworkChanges =
        prefs.getBoolean(Constants.IS_RECEIVING_NETWORK_CHANGES, false);
    if (isReceivingNetworkChanges) {
      FileSaver.saveStringToFile(
          Constants.NETWORK_CHANGE_RECEIVED_AT + String.format(Constants.DATE_AND_TIME_FORMATTING,
              Calendar.getInstance()) + Constants.NEW_LINE,
          context.getExternalFilesDir(null).toString() + Constants.NETWORK_CHANGES_LOG_FILE);
    }
  }

  private void debugIntent(Intent intent, String tag) {
    Log.d(tag, "action: " + intent.getAction());
    Log.d(tag, "component: " + intent.getComponent());
    Bundle extras = intent.getExtras();
    if (extras != null) {
      for (String key : extras.keySet()) {
        Log.d(tag, "key [" + key + "]: " + extras.get(key));
      }
    } else {
      Log.d(tag, "no extras");
    }
  }
}