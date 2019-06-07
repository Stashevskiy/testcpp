#include <jni.h>
#include "encrypt.h"

extern "C" {
JNIEXPORT jbyteArray JNICALL Java_com_example_testcpp_MainActivity_aezEncrypt
  (JNIEnv *env, jobject This, jbyteArray plaintext, jbyteArray ad, jbyteArray nonce,
  jbyteArray key, jint expansion) {
       //-- No error handling is being done...
       unsigned plaintext_len = (unsigned)env->GetArrayLength (plaintext);
       unsigned ad_len = (unsigned)env->GetArrayLength (ad);
       unsigned key_len = (unsigned)env->GetArrayLength (key);
       unsigned text_expansion = (unsigned)expansion;
        //plaintext
       byte* plaintext_cpp = new byte[plaintext_len];
       env->GetByteArrayRegion (plaintext, 0, plaintext_len, reinterpret_cast<jbyte*>(plaintext_cpp));
       //ad
       byte* ad_cpp = new byte[ad_len];
       env->GetByteArrayRegion (ad, 0, ad_len, reinterpret_cast<jbyte*>(ad_cpp));
       byte* AD_new[] = { ad_cpp };
       unsigned adbytes[] = { ad_len };
       //nonce
       byte* nonce_cpp = new byte[0];

       //key
       byte* key_cpp = new byte[key_len];
       env->GetByteArrayRegion (key, 0, key_len, reinterpret_cast<jbyte*>(key_cpp));
       //dest
       byte* dest = new byte[23];
       Encrypt(
       			key_cpp, key_len,
       			nonce_cpp, 0,
       			AD_new, adbytes, 1,
       			text_expansion,
       			plaintext_cpp, plaintext_len,
       			dest
       		);
       jbyteArray array = env->NewByteArray (23);
       env->SetByteArrayRegion (array, 0, 23, reinterpret_cast<jbyte*>(dest));
       return array;
}
}

