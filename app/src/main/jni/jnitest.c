//
// Created by heshaokang on 2015/9/15.
//
#include "com_creativeboy_myapp_ndk_NdkJniUtils.h"
JNIEXPORT jstring JNICALL Java_com_creativeboy_myapp_ndk_NdkJniUtils_getCLanguageString
        (JNIEnv *env, jobject obj){
    return (*env)->NewStringUTF(env,"This just a test for Android Studio NDK JNI developer!");
}

