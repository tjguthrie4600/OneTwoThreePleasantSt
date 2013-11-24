#! /bin/bash

#ant clean
ant debug
adb uninstall com.OneTwoThreePleasantSt
adb install bin/OneTwoThreePleasantSt-debug.apk
