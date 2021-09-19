
package com.wizarpos.apidemo.action;

import java.util.Map;
import java.util.Random;

import com.cloudpos.AlgorithmConstants;
import com.cloudpos.DeviceException;
import com.cloudpos.OperationListener;
import com.cloudpos.OperationResult;
import com.cloudpos.POSTerminal;
import com.cloudpos.TimeConstants;
import com.cloudpos.pinpad.KeyInfo;
import com.cloudpos.pinpad.PINPadDevice;
import com.cloudpos.pinpad.PINPadOperationResult;
import com.cloudpos.pinpad.extend.PINPadExtendDevice;
import com.wizarpos.apidemo.common.Common;
import com.wizarpos.apidemo.util.StringUtility;
import com.wizarpos.apidemoforunionpaycloudpossdk.R;
import com.wizarpos.mvc.base.ActionCallback;

public class PINPadAction extends ActionModel {

    private PINPadExtendDevice device = null;
    private int masterKeyID = 0;
    private int userKeyID = 0;
    private int algo = 0;

    @Override
    protected void doBefore(Map<String, Object> param, ActionCallback callback) {
        super.doBefore(param, callback);
        if (device == null) {
            device = (PINPadExtendDevice) POSTerminal.getInstance(mContext)
                    .getDevice("cloudpos.device.pinpad");
        }
    }

    public void open(Map<String, Object> param, ActionCallback callback) {
        try {
            device.open();
            sendSuccessLog(mContext.getString(R.string.operation_succeed));
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void showText(Map<String, Object> param, ActionCallback callback) {
        try {
            device.showText(0, "密码余额元");
            device.showText(1, "show test");
            sendSuccessLog(mContext.getString(R.string.operation_succeed));
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void clearText(Map<String, Object> param, ActionCallback callback) {
        try {
            device.clearText();
            sendSuccessLog(mContext.getString(R.string.operation_succeed));
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void setPINLength(Map<String, Object> param, ActionCallback callback) {
        try {
            device.setPINLength(4, 12);
            sendSuccessLog(mContext.getString(R.string.operation_succeed));
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void getSN(Map<String, Object> param, ActionCallback callback) {
        try {
            String sn = device.getSN();
            sendSuccessLog(mContext.getString(R.string.operation_succeed) + " Pinpad SN = " + sn);
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void getRandom(Map<String, Object> param, ActionCallback callback) {
        try {
            byte[] random = device.getRandom(5);
            sendSuccessLog(mContext.getString(R.string.operation_succeed) + " random = "
                    + StringUtility.byteArray2String(random));
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void updateUserKey1(Map<String, Object> param, ActionCallback callback) {
        String userKey = "09 FA 17 0B 03 11 22 76 09 FA 17 0B 03 11 22 76";
        byte[] arryCipherNewUserKey = new byte[16];
        StringUtility.StringToByteArray(userKey, arryCipherNewUserKey);
        try {
            device.updateUserKey(masterKeyID, userKeyID, arryCipherNewUserKey);
            sendSuccessLog(mContext.getString(R.string.operation_succeed));
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void updateUserKey2(Map<String, Object> param, ActionCallback callback) {
        String userKey = "09 FA 17 0B 03 11 22 76 09 FA 17 0B 03 11 22 76";//密文
        byte[] arryCipherNewUserKey = new byte[16];
        StringUtility.StringToByteArray(userKey, arryCipherNewUserKey);
        String checkValue = "A5 17 3A D5";
        byte[] arryCheckValue = new byte[4];
        StringUtility.StringToByteArray(checkValue, arryCheckValue);
        try {
            device.updateUserKey(masterKeyID, userKeyID, arryCipherNewUserKey,
                    PINPadDevice.CHECK_TYPE_CUP, arryCheckValue);
            sendSuccessLog(mContext.getString(R.string.operation_succeed));
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void updateUserKey3(Map<String, Object> param, ActionCallback callback) {
        String userKey = "09 FA 17 0B 03 11 22 76 09 FA 17 0B 03 11 22 76";
        byte[] arryCipherNewUserKey = new byte[16];
        StringUtility.StringToByteArray(userKey, arryCipherNewUserKey);
        String checkValue = "A5 17 3A";
        byte[] arryCheckValue = new byte[3];
        StringUtility.StringToByteArray(checkValue, arryCheckValue);
        try {
            device.updateUserKey(masterKeyID, userKeyID, arryCipherNewUserKey,
                    PINPadDevice.CHECK_TYPE_CUP, arryCheckValue);
            sendSuccessLog(mContext.getString(R.string.operation_succeed));
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void getSessionKeyCheckValue(Map<String, Object> param, ActionCallback callback) {
        try {
            byte[] getSessionKeyCheckValue = device.getSessionKeyCheckValue(masterKeyID, userKeyID, algo);
            sendSuccessLog(mContext.getString(R.string.operation_succeed) + " getSessionKeyCheckValue = "
                    + StringUtility.byteArray2String(getSessionKeyCheckValue));
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void encryptData(Map<String, Object> param, ActionCallback callback) {
        KeyInfo keyInfo = new KeyInfo(PINPadDevice.KEY_TYPE_MK_SK, 0, 2,
                AlgorithmConstants.ALG_3DES);
        byte[] plain = new byte[]{
                0x38, 0x38, 0x38, 0x38, 0x38, 0x38, 0x38, 0x38
        };
        try {
            byte[] cipher = device.encryptData(keyInfo, plain);
            sendSuccessLog(mContext.getString(R.string.operation_succeed) + " cipher data = "
                    + StringUtility.byteArray2String(cipher));
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void calculateMAC(Map<String, Object> param, ActionCallback callback) {
        KeyInfo keyInfo = new KeyInfo(PINPadDevice.KEY_TYPE_MK_SK, 0, 1,
                AlgorithmConstants.ALG_3DES);
        byte[] arryMACInData = Common.createMasterKey(8);
        try {
            byte[] mac = device.calculateMac(keyInfo, AlgorithmConstants.ALG_MAC_METHOD_X99,
                    arryMACInData);
            sendSuccessLog(mContext.getString(R.string.operation_succeed) + " mac data = "
                    + StringUtility.byteArray2String(mac));
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    private byte[] dukptMAC = {0x34, 0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39, 0x30, 0x39, 0x44, 0x39, 0x38, 0x37, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

    public void calculateDukptMAC(Map<String, Object> param, ActionCallback callback) {
        KeyInfo keyInfo = new KeyInfo(PINPadDevice.KEY_TYPE_TDUKPT_2009, 0, 0,
                AlgorithmConstants.ALG_3DES);
        try {
            byte[] mac = device.calculateMac(keyInfo, AlgorithmConstants.ALG_MAC_METHOD_SE919, dukptMAC);
            sendSuccessLog(mContext.getString(R.string.operation_succeed) + " mac data = " + StringUtility.byteArray2String(mac));
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void verifyResponseMAC(Map<String, Object> param, ActionCallback callback) {
        KeyInfo keyInfo = new KeyInfo(PINPadDevice.KEY_TYPE_TDUKPT_2009, masterKeyID, 0,
                AlgorithmConstants.ALG_3DES);
        try {
            byte[] mac = device.calculateMac(keyInfo, AlgorithmConstants.ALG_MAC_METHOD_SE919, dukptMAC);
            sendSuccessLog(mContext.getString(R.string.operation_succeed) + " mac data = " + StringUtility.byteArray2String(mac));

            byte[] verifyRespMACArr = new byte[8];
            StringUtility.StringToByteArray("20 36 42 23 C1 FF 00 FA", verifyRespMACArr);//0001次的response mac
            int macFlag = 2;
            int nDirection = 0;
            boolean b = device.verifyResponseMac(keyInfo, dukptMAC, macFlag, verifyRespMACArr, nDirection);
            if (b) {
                sendSuccessLog(mContext.getString(R.string.operation_succeed) + " request mac = " + StringUtility.byteArray2String(verifyRespMACArr));
            }
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void listenForPinBlock(Map<String, Object> param, ActionCallback callback) {
        KeyInfo keyInfo = new KeyInfo(PINPadDevice.KEY_TYPE_MK_SK, 0, 0, 4);
        String pan = "0123456789012345678";
        try {
            OperationListener listener = new OperationListener() {

                @Override
                public void handleResult(OperationResult arg0) {
                    if (arg0.getResultCode() == OperationResult.SUCCESS) {
                        byte[] pinBlock = ((PINPadOperationResult) arg0).getEncryptedPINBlock();
                        sendSuccessLog2("PINBlock = " + StringUtility.byteArray2String(pinBlock));
                    } else {
                        sendFailedLog2(mContext.getString(R.string.operation_failed));
                    }
                }
            };
            device.listenForPinBlock(keyInfo, pan, false, listener, TimeConstants.FOREVER);
            sendSuccessLog("");
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void waitForPinBlock(Map<String, Object> param, ActionCallback callback) {
        KeyInfo keyInfo = new KeyInfo(PINPadDevice.KEY_TYPE_MK_SK, 0, 0, 4);
        String pan = "0123456789012345678";
        try {
            sendSuccessLog("");
            OperationResult operationResult = device.waitForPinBlock(keyInfo, pan, false,
                    TimeConstants.FOREVER);
            if (operationResult.getResultCode() == OperationResult.SUCCESS) {
                byte[] pinBlock = ((PINPadOperationResult) operationResult).getEncryptedPINBlock();
                sendSuccessLog2("PINBlock = " + StringUtility.byteArray2String(pinBlock));
            } else {
                sendFailedLog2(mContext.getString(R.string.operation_failed));
            }
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void setGUIConfiguration(Map<String, Object> param, ActionCallback callback) {
        try {
            int flag = 1;
            byte[] data = new byte[1];
            data[0] = 0x01;
            byte[] data2 = "test1234560".getBytes();
            boolean b = device.setGUIConfiguration(flag, data);
            if (b) {
                sendSuccessLog(mContext.getString(R.string.operation_succeed));
            } else {
                sendFailedLog(mContext.getString(R.string.operation_failed));
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    int changeSound = 1;

    public void setGUISound(Map<String, Object> param, ActionCallback callback) {
        try {
            changeSound++;
            boolean b = device.setGUIConfiguration("sound", changeSound % 2 == 0 ? "true" : "false");
            if (b) {
                sendSuccessLog(mContext.getString(R.string.operation_succeed) + "");
            } else {
                sendFailedLog(mContext.getString(R.string.operation_failed) + "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void setGUIStyle(Map<String, Object> param, ActionCallback callback) {
        try {
            boolean b = device.setGUIConfiguration("style", "dajiavoozcredit");
            if (b) {
                sendSuccessLog(mContext.getString(R.string.operation_succeed));
            } else {
                sendFailedLog(mContext.getString(R.string.operation_failed));
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void getMkStatus(Map<String, Object> param, ActionCallback callback) {
        try {
            int mkid = 0;
            int result = device.getMkStatus(mkid);
            if (result == 0) {
                sendSuccessLog(mContext.getString(R.string.operation_succeed) +", it does not exist");
            } else if (result > 0) {
                sendSuccessLog(mContext.getString(R.string.operation_succeed) +",it exist,mkid = " + mkid);
            } else {
                sendFailedLog(mContext.getString(R.string.operation_failed));
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void getSkStatus(Map<String, Object> param, ActionCallback callback) {
        try {
            int mkid = 0;
            int skid = 0;

            int result = device.getSkStatus(mkid,skid);
            if (result == 0) {
                sendSuccessLog(mContext.getString(R.string.operation_succeed) +", it does not exist");
            } else if (result > 0) {
                sendSuccessLog(mContext.getString(R.string.operation_succeed) +",it exist,mkid = " + mkid + ",skid = " + skid);
            } else {
                sendFailedLog(mContext.getString(R.string.operation_failed));
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void getDukptStatus(Map<String, Object> param, ActionCallback callback) {
        try {
            int mkid = 0;
            byte[] dukptData = new byte[32];

            int result = device.getDukptStatus(mkid,dukptData);
            if (result == 0) {
                sendSuccessLog(mContext.getString(R.string.operation_succeed) +", it does not exist");
            } else if (result > 0) {
                sendSuccessLog(mContext.getString(R.string.operation_succeed) +"getDukptStatus success , KSN = " + StringUtility.ByteArrayToString(dukptData, result));
            } else {
                sendFailedLog(mContext.getString(R.string.operation_failed));
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }
    public void cancelRequest(Map<String, Object> param, ActionCallback callback) {
        try {
            device.cancelRequest();
            sendSuccessLog(mContext.getString(R.string.operation_succeed));
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void close(Map<String, Object> param, ActionCallback callback) {
        try {
            device.close();
            sendSuccessLog(mContext.getString(R.string.operation_succeed));
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }
}
