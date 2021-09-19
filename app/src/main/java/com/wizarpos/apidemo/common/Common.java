
package com.wizarpos.apidemo.common;


public class Common {

    public static byte[] createMasterKey(int length) {
        byte[] array = new byte[length];
        for (int i = 0; i < length; i++) {
            array[i] = (byte) 0x38;
        }
        return array;
    }

    public static int transferErrorCode(int errorCode) {
        int a = -errorCode;
        int b = a & 0x0000FF;
        return -b;
    }


    public static String getMethodName() {
        StackTraceElement[] eles = Thread.currentThread().getStackTrace();
//        for (StackTraceElement stackTraceElement : eles) {
//            Log.e("stackTraceElement", stackTraceElement.getMethodName());
//        }
        return eles[5].getMethodName();
    }

}
