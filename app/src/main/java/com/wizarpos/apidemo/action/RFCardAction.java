
package com.wizarpos.apidemo.action;

import java.util.Map;

import com.cloudpos.DeviceException;
import com.cloudpos.OperationListener;
import com.cloudpos.OperationResult;
import com.cloudpos.POSTerminal;
import com.cloudpos.TimeConstants;
import com.cloudpos.card.ATR;
import com.cloudpos.card.CPUCard;
import com.cloudpos.card.Card;
import com.cloudpos.card.MifareCard;
import com.cloudpos.card.MifareUltralightCard;
import com.cloudpos.card.MoneyValue;
import com.cloudpos.jniinterface.RFCardInterface;
import com.cloudpos.rfcardreader.RFCardReaderDevice;
import com.cloudpos.rfcardreader.RFCardReaderOperationResult;
import com.cloudpos.smartcardreader.SmartCardReaderDevice;
import com.cloudpos.smartcardreader.SmartCardReaderOperationResult;
import com.wizarpos.apidemo.common.Common;
import com.wizarpos.apidemo.util.StringUtility;
import com.wizarpos.apidemoforunionpaycloudpossdk.R;
import com.wizarpos.mvc.base.ActionCallback;
import com.wizarpos.mvc.impl.ActionCallbackImpl;

public class RFCardAction extends ActionModel {
    private RFCardReaderDevice device = null;
    Card rfCard;
    private SmartCardReaderDevice device1 =null;
    private Card psamCard;
    int sectorIndex = 0;
    int blockIndex = 1;
    private int pinType_level3 = 2;

    @Override
    protected void doBefore(Map<String, Object> param, ActionCallback callback) {
        super.doBefore(param, callback);
        if (device == null) {
            device = (RFCardReaderDevice) POSTerminal.getInstance(mContext)
                    .getDevice("cloudpos.device.rfcardreader");
        }
        if (device1 == null) {
            device1 = (SmartCardReaderDevice) POSTerminal.getInstance(mContext)
                    .getDevice("cloudpos.device.smartcardreader",1);
        }
    }

    public void open(Map<String, Object> param, ActionCallback callback) {
        try {
            device.open();
            device1.open(1);
            sendSuccessLog(mContext.getString(R.string.operation_succeed));
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void listenForCardPresent(Map<String, Object> param, ActionCallback callback) {
        try {
            OperationListener listener = new OperationListener() {

                @Override
                public void handleResult(OperationResult arg0) {
                    if (arg0.getResultCode() == OperationResult.SUCCESS) {
                        sendSuccessLog2(mContext.getString(R.string.find_card_succeed));
                        rfCard = ((RFCardReaderOperationResult) arg0).getCard();
                    } else {
                        sendFailedLog2(mContext.getString(R.string.find_card_failed));
                    }
                }
            };
            device.listenForCardPresent(listener, TimeConstants.FOREVER);
            OperationListener listener1 = new OperationListener() {

                @Override
                public void handleResult(OperationResult arg0) {
                    if (arg0.getResultCode() == OperationResult.SUCCESS) {
                        sendSuccessLog2(mContext.getString(R.string.find_card_succeed));
                        psamCard = ((SmartCardReaderOperationResult) arg0).getCard();
                        try {
                            sendSuccessLog2(mContext.getString(R.string.find_card_succeed)+psamCard.getID());
                        } catch (DeviceException e) {
                            e.printStackTrace();
                            sendFailedLog(mContext.getString(R.string.operation_failed));
                        }
                    } else {
                        sendFailedLog2(mContext.getString(R.string.find_card_failed));
                    }
                }
            };
            device1.listenForCardPresent(listener1, TimeConstants.FOREVER);
            sendSuccessLog("");
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void waitForCardPresent(Map<String, Object> param, ActionCallback callback) {
        try {
            sendSuccessLog("");
            OperationResult operationResult = device.waitForCardPresent(TimeConstants.FOREVER);
            if (operationResult.getResultCode() == OperationResult.SUCCESS) {
                sendSuccessLog2(mContext.getString(R.string.find_card_succeed));
                rfCard = ((RFCardReaderOperationResult) operationResult).getCard();
            } else {
                sendFailedLog2(mContext.getString(R.string.find_card_failed));
            }
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void listenForCardAbsent(Map<String, Object> param, ActionCallback callback) {
        try {
            OperationListener listener = new OperationListener() {

                @Override
                public void handleResult(OperationResult arg0) {
                    if (arg0.getResultCode() == OperationResult.SUCCESS) {
                        sendSuccessLog2(mContext.getString(R.string.absent_card_succeed));
                        rfCard = null;
                    } else {
                        sendFailedLog2(mContext.getString(R.string.absent_card_failed));
                    }
                }
            };
            device.listenForCardAbsent(listener, TimeConstants.FOREVER);
            sendSuccessLog("");
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void waitForCardAbsent(Map<String, Object> param, ActionCallback callback) {
        try {
            sendSuccessLog("");
            OperationResult operationResult = device.waitForCardAbsent(TimeConstants.FOREVER);
            if (operationResult.getResultCode() == OperationResult.SUCCESS) {
                sendSuccessLog2(mContext.getString(R.string.absent_card_succeed));
                rfCard = null;
            } else {
                sendFailedLog2(mContext.getString(R.string.absent_card_failed));
            }
        } catch (DeviceException e) {
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

    public void getMode(Map<String, Object> param, ActionCallback callback) {
        try {
            int mode = device.getMode();
            sendSuccessLog(mContext.getString(R.string.operation_succeed) + " Mode = " + mode);
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void setSpeed(Map<String, Object> param, ActionCallback callback) {
        try {
            device.setSpeed(460800);
            sendSuccessLog(mContext.getString(R.string.operation_succeed));
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void getSpeed(Map<String, Object> param, ActionCallback callback) {
        try {
            int speed = device.getSpeed();
            sendSuccessLog(mContext.getString(R.string.operation_succeed) + " Speed = " + speed);
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void getID(Map<String, Object> param, ActionCallback callback) {
        try {
            byte[] cardID = rfCard.getID();
            sendSuccessLog(mContext.getString(R.string.operation_succeed) + " Card ID = "
                    + StringUtility.byteArray2String(cardID));
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void getProtocol(Map<String, Object> param, ActionCallback callback) {
        try {
            int protocol = rfCard.getProtocol();
            sendSuccessLog(mContext.getString(R.string.operation_succeed) + " Protocol = "
                    + protocol);
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void getCardStatus(Map<String, Object> param, ActionCallback callback) {
        try {
            int cardStatus = rfCard.getCardStatus();
            sendSuccessLog(mContext.getString(R.string.operation_succeed) + " Card Status = "
                    + cardStatus);
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void verifyKeyA(Map<String, Object> param, ActionCallback callback) {
        byte[] key = new byte[]{
                (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
                (byte) 0xFF
        };
        try {

            boolean verifyResult = ((MifareCard) rfCard).verifyKeyA(sectorIndex, key);
            sendSuccessLog(mContext.getString(R.string.operation_succeed));
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void verifyKeyB(Map<String, Object> param, ActionCallback callback) {
        byte[] key = new byte[]{
                (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
                (byte) 0xFF
        };
        try {

            boolean verifyResult = ((MifareCard) rfCard).verifyKeyB(sectorIndex, key);
            sendSuccessLog(mContext.getString(R.string.operation_succeed));
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void verify_level3(Map<String, Object> param, ActionCallback callback) {
        final byte[] arryKey = {
                (byte) 0x49, (byte) 0x45, (byte) 0x4D, (byte) 0x4B, (byte) 0x41, (byte) 0x45, (byte) 0x52, (byte) 0x42,
                (byte) 0x21, (byte) 0x4E, (byte) 0x41, (byte) 0x43, (byte) 0x55, (byte) 0x4F, (byte) 0x59, (byte) 0x46
        };
        try {
            boolean verifyLevel3Result = ((MifareUltralightCard) rfCard).verifyKey(arryKey);
            sendSuccessLog(mContext.getString(R.string.operation_succeed));
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void transmit_level3(Map<String, Object> param, ActionCallbackImpl callback) {
        final byte[] arryAPDU = new byte[]{
                (byte) 0x30, (byte) 0x00
        };
        try {
            byte[] result = null;
            if (rfCard instanceof CPUCard) {
                result = ((CPUCard) rfCard).transmit(arryAPDU, 0);
            } else if (rfCard instanceof MifareCard) {
                result = ((MifareCard) rfCard).transmit(arryAPDU, 0);
            } else if (rfCard instanceof MifareUltralightCard) {
                result = ((MifareUltralightCard) rfCard).transmit(arryAPDU, 0);
            } else {
                //result = (Real Card Type) rfCard.transmit(arryAPDU, 0);
            }
            sendSuccessLog(mContext.getString(R.string.operation_succeed) + " (" + sectorIndex
                    + ", " + blockIndex + ")transmit_level3: " + StringUtility.byteArray2String(result));
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void readBlock(Map<String, Object> param, ActionCallback callback) {
        try {
            byte[] result = ((MifareCard) rfCard).readBlock(sectorIndex, blockIndex);
            sendSuccessLog(mContext.getString(R.string.operation_succeed) + " (" + sectorIndex
                    + ", " + blockIndex + ")Block data: " + StringUtility.byteArray2String(result));
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }


    public void writeBlock(Map<String, Object> param, ActionCallback callback) {
        byte[] arryData = Common.createMasterKey(16);// 随机创造16个字节的数组
        try {
            ((MifareCard) rfCard).writeBlock(sectorIndex, blockIndex, arryData);
            sendSuccessLog(mContext.getString(R.string.operation_succeed));
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void readValue(Map<String, Object> param, ActionCallback callback) {
        try {
            MoneyValue value = ((MifareCard) rfCard).readValue(sectorIndex, blockIndex);
            sendSuccessLog(mContext.getString(R.string.operation_succeed) + " value = "
                    + value.getMoney() + " user data: "
                    + StringUtility.byteArray2String(value.getUserData()));
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void writeValue(Map<String, Object> param, ActionCallback callback) {
        try {
            MoneyValue value = new MoneyValue(new byte[]{
                    (byte) 0x39
            }, 1024);
            ((MifareCard) rfCard).writeValue(sectorIndex, blockIndex, value);
            sendSuccessLog(mContext.getString(R.string.operation_succeed));
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void incrementValue(Map<String, Object> param, ActionCallback callback) {
        try {
            ((MifareCard) rfCard).increaseValue(sectorIndex, blockIndex, 10);
            sendSuccessLog(mContext.getString(R.string.operation_succeed));
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void decrementValue(Map<String, Object> param, ActionCallback callback) {
        try {
            ((MifareCard) rfCard).decreaseValue(sectorIndex, blockIndex, 10);
            sendSuccessLog(mContext.getString(R.string.operation_succeed));
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void read(Map<String, Object> param, ActionCallback callback) {
        try {
            byte[] result = ((MifareUltralightCard) rfCard).read(blockIndex);
            sendSuccessLog(mContext.getString(R.string.operation_succeed) + " (" + sectorIndex
                    + ", " + blockIndex + ")Block data: " + StringUtility.byteArray2String(result));
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void write(Map<String, Object> param, ActionCallback callback) {
        byte[] arryData = Common.createMasterKey(4);// 随机创造4个字节的数组
        try {
            ((MifareUltralightCard) rfCard).write(blockIndex, arryData);
            sendSuccessLog(mContext.getString(R.string.operation_succeed));
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void connect(Map<String, Object> param, ActionCallback callback) {
        try {
            ATR atr = ((CPUCard) rfCard).connect();
            sendSuccessLog(mContext.getString(R.string.operation_succeed) + " ATR: "
                    + StringUtility.byteArray2String(atr.getBytes()));
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void transmit(Map<String, Object> param, ActionCallback callback) {
        final byte[] arryAPDU = new byte[]{
                (byte) 0x00, (byte) 0xA4, (byte) 0x04, (byte) 0x00,
                (byte) 0x0E, (byte) 0x32, (byte) 0x50, (byte) 0x41,
                (byte) 0x59, (byte) 0x2E, (byte) 0x53, (byte) 0x59,
                (byte) 0x53, (byte) 0x2E, (byte) 0x44, (byte) 0x44,
                (byte) 0x46, (byte) 0x30, (byte) 0x31
        };
        try {
            byte[] apduResponse = ((CPUCard) rfCard).transmit(arryAPDU);
            sendSuccessLog(mContext.getString(R.string.operation_succeed) + " APDUResponse: "
                    + StringUtility.byteArray2String(apduResponse));
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void disconnect(Map<String, Object> param, ActionCallback callback) {
        try {
            sendNormalLog(mContext.getString(R.string.rfcard_remove_card));
            ((CPUCard) rfCard).disconnect();
            sendSuccessLog(mContext.getString(R.string.operation_succeed));
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void close(Map<String, Object> param, ActionCallback callback) {
        try {
            rfCard = null;
            device.close();
            sendSuccessLog(mContext.getString(R.string.operation_succeed));
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }
}
