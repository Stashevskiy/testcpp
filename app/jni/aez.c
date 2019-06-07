#include <jni.h>

JNIEXPORT void JNICALL Java_com_example_testcpp_MainActivity_aezEncrypt
  (JNIEnv *env, jobject This, jbyteArray plaintext, jbyteArray ad, jbyteArray nonce,
  jbyteArray key, jint expansion) {
       byte* k;
       return;
}