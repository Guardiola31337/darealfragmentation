Da Real Fragmentation
=====================
[![Build Status](https://travis-ci.org/Guardiola31337/darealfragmentation.svg?branch=master)](https://travis-ci.org/Guardiola31337/darealfragmentation)
[![Hex.pm](https://img.shields.io/hexpm/l/plug.svg)](http://www.apache.org/licenses/LICENSE-2.0)
[![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)

Nowadays, __Fragmentation in Android__ is a fact. As developers, we have to deal constantly with random behaviors between different versions of the operating system, incomplete ROMs, different hardware qualities, manufacturers that modify the specs of some APIs or even ignore them...

That’s _Da Real Fragmentation_ and it makes __development__ much more __difficult__.

In addition, if you have __background processes__ running, your life becomes a __nightmare__!

This sample repository illustrates the problems that we found along the way creating an app which needs to keep track of __sensor signals__ in __background__.

Reviewing how to deal with the different versions of [Alarms in Android](https://developer.android.com/training/scheduling/alarms.html), how to live with [Doze mode](https://developer.android.com/training/monitoring-device-state/doze-standby.html) and other battery optimization apps and showing some hardware and software peculiarities of different manufacturers.
 
Apart from that, it explains how we have solved such fragmentation issues in order to develop an app that works properly in as many devices as possible.

License
-------

    Copyright 2016 Pablo Guardiola Sánchez.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.