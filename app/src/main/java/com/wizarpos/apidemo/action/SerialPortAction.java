package com.wizarpos.apidemo.action;

import com.cloudpos.DeviceException;
import com.cloudpos.OperationListener;
import com.cloudpos.OperationResult;
import com.cloudpos.POSTerminal;
import com.cloudpos.TimeConstants;
import com.cloudpos.jniinterface.SerialPortInterface;
import com.cloudpos.pinpad.extend.PINPadExtendDevice;
import com.cloudpos.rfcardreader.RFCardReaderOperationResult;
import com.cloudpos.sdk.common.SystemProperties;
import com.cloudpos.serialport.SerialPortDevice;
import com.cloudpos.serialport.SerialPortOperationResult;
import com.wizarpos.apidemoforunionpaycloudpossdk.R;
import com.wizarpos.mvc.base.ActionCallback;
import com.wizarpos.mvc.impl.ActionCallbackImpl;

import java.util.Map;

/**
 * create by rf.w 19-8-7下午3:23
 */
public class SerialPortAction extends ActionModel {

    private SerialPortDevice device = null;

    private int timeout = 3000;
    private int baudrate = 38400;
    private String testString = "wizarpos";

    @Override
    protected void doBefore(Map<String, Object> param, ActionCallback callback) {
        super.doBefore(param, callback);
        if (device == null) {
            device = (SerialPortDevice) POSTerminal.getInstance(mContext).getDevice("cloudpos.device.serialport");
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

    public void close(Map<String, Object> param, ActionCallback callback) {
        try {
            device.close();
            sendSuccessLog(mContext.getString(R.string.operation_succeed));
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void waitForRead(Map<String, Object> param, ActionCallbackImpl callback) {
        try {
            final byte[] arryData = new byte[testString.length()];
            SerialPortOperationResult serialPortOperationResult = device.waitForRead(arryData.length, timeout);
            if (serialPortOperationResult.getData() != null) {
                sendSuccessLog("Result = " + new String(serialPortOperationResult.getData()));
                sendSuccessLog(mContext.getString(R.string.port_waitforread_succeed));
            } else {
                sendFailedLog(mContext.getString(R.string.port_waitforread_failed));
            }

        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void listenForRead(Map<String, Object> param, ActionCallbackImpl callback) {
        final byte[] arryData = new byte[testString.length()];
        try {
            OperationListener listener = new OperationListener() {
                @Override
                public void handleResult(OperationResult arg0) {
                    if (arg0.getResultCode() == OperationResult.SUCCESS) {
                        sendSuccessLog2(mContext.getString(R.string.port_listenforread_succeed));
                        sendSuccessLog("\nResult = " + new String(arryData));
                    } else {
                        sendFailedLog2(mContext.getString(R.string.port_listenforread_failed));
                    }
                }
            };
            device.listenForRead(arryData.length, listener, timeout);
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void write(Map<String, Object> param, ActionCallbackImpl callback) {
        try {
            final byte[] arryData = new String(testString).getBytes();
            final int length = 5;
            final int offset = 2;
            device.write(arryData, 0, length);
            sendSuccessLog2(mContext.getString(R.string.port_write_succeed));
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    private enum Mode {SLAVE, HOST}

    private String getModelName(Mode mode) {
        //    	"USB_SLAVE_SERIAL" : slave mode,(USB)
//    	"USB_HOST_SERIAL" : host mode(OTG)
        String deviceName;
        String model = SystemProperties.getSystemPropertie("ro.wp.product.model").trim().replace(" ", "_");
        if (mode.equals(Mode.SLAVE)) {
            deviceName = "USB_SLAVE_SERIAL";
            if (model.equalsIgnoreCase("W1") || model.equalsIgnoreCase("W1V2")) {
                deviceName = "DB9";
            } else if (model.equalsIgnoreCase("Q1")) {
                deviceName = "WIZARHANDQ1";
            }
        } else {
            deviceName = "USB_SERIAL";
            if (model.equalsIgnoreCase("W1") || model.equalsIgnoreCase("W1V2")) {
                deviceName = "GS0_Q1";
            }
        }
        return deviceName;
    }
}
