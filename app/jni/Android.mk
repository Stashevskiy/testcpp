LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := aez
LOCAL_SRC_FILES := aez.cpp encrypt.cpp rijndael-alg-fst.cpp blake2b.cpp

include $(BUILD_SHARED_LIBRARY)