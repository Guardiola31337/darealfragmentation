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

import android.util.Log;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileSaver {

  public static boolean saveStringToFile(String data, String fullPathFileName) {
    boolean result = true;
    File file = new File(fullPathFileName);
    try {
      FileWriter fw = new FileWriter(file, true);
      fw.write(data);
      fw.close();
    } catch (IOException ioe) {
      Log.d(Constants.TAG, "saveStringToFile: ", ioe);
      result = false;
    }
    return result;
  }
}
