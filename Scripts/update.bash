#! /bin/bash

ant clean 
cp -r ../XML-RPC-Client-Classes/bin .
ant debug
adb uninstall com.OneTwoThreePleasantSt
adb install bin/OneTwoThreePleasantSt-debug.apk
