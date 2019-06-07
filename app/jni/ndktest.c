#include <jni.h>
#include <string.h>

jstring Java_com_example_testcpp_MainActivity_helloWorld(JNIEnv* env, jobject object){
    return (*env)->NewStringUTF(env, "Hello from library");
}