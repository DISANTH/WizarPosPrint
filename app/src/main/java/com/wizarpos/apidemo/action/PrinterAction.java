
package com.wizarpos.apidemo.action;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.cloudpos.DeviceException;
import com.cloudpos.POSTerminal;
import com.cloudpos.printer.Format;
import com.cloudpos.printer.PrinterDevice;
import com.wizarpos.ReceivePurchaseDataModel;
import com.wizarpos.apidemoforunionpaycloudpossdk.R;
import com.wizarpos.mvc.base.ActionCallback;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class PrinterAction extends ActionModel {

    private PrinterDevice device = null;

    @Override
    protected void doBefore(Map<String, Object> param, ActionCallback callback) {
        super.doBefore(param, callback);
        if (device == null) {
            device = (PrinterDevice) POSTerminal.getInstance(mContext)
                    .getDevice("cloudpos.device.printer");
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

    public void printText(Map<String, Object> param, ActionCallback callback) {
        try {
            device.printText(
                    "Demo receipts" +
                            "MERCHANT COPY" +
                            "" +
                            "MERCHANT NAME" +
                            "SHXXXXXXCo.,LTD." +
                            "530310041315039" +
                            "TERMINAL NO" +
                            "50000045" +
                            "OPERATOR" +
                            "50000045" +
                            "" +
                            "CARD NO" +
                            "623020xxxxxx3994 I" +
                            "ISSUER ACQUIRER" +
                            "" +
                            "TRANS TYPE" +
                            "PAY SALE" +
                            "PAY SALE" +
                            "" +
                            "DATE/TIME EXP DATE" +
                            "2005/01/21 16:52:32 2099/12" +
                            "REF NO BATCH NO" +
                            "165232857468 000001" +
                            "VOUCHER AUTH NO" +
                            "000042" +
                            "AMOUT:" +
                            "RMB:0.01" +
                            "" +
                            "BEIZHU" +
                            "SCN:01" +
                            "UMPR NUM:4F682D56" +
                            "TC:EF789E918A548668" +
                            "TUR:008004E000" +
                            "AID:A000000333010101" +
                            "TSI:F800" +
                            "ATC:0440" +
                            "APPLAB:PBOC DEBIT" +
                            "APPNAME:PBOC DEBIT" +
                            "AIP:7C00" +
                            "CUMR:020300" +
                            "IAD:07010103602002010A01000000000005DD79CB" +
                            "TermCap:EOE1C8" +
                            "CARD HOLDER SIGNATURE" +
                            "I ACKNOWLEDGE SATISFACTORY RECEIPT OF RELATIVE GOODS/SERVICE" +
                            "I ACKNOWLEDGE SATISFACTORY RECEIPT OF RELATIVE GOODS/SERVICE" +
                            "I ACKNOWLEDGE SATISFACTORY RECEIPT OF RELATIVE GOODS/SERVICE" +
                            "" +
                            "Demo receipts,do not sign!" +
                            "" +
                            "" +
                            "" + "Demo receipts" +
                            "MERCHANT COPY" +
                            "" +
                            "MERCHANT NAME" +
                            "SHXXXXXXCo.,LTD." +
                            "530310041315039" +
                            "TERMINAL NO" +
                            "50000045" +
                            "OPERATOR" +
                            "50000045" +
                            "" +
                            "CARD NO" +
                            "623020xxxxxx3994 I" +
                            "ISSUER ACQUIRER" +
                            "" +
                            "TRANS TYPE" +
                            "PAY SALE" +
                            "PAY SALE" +
                            "" +
                            "DATE/TIME EXP DATE" +
                            "2005/01/21 16:52:32 2099/12" +
                            "REF NO BATCH NO" +
                            "165232857468 000001" +
                            "VOUCHER AUTH NO" +
                            "000042" +
                            "AMOUT:" +
                            "RMB:0.01" +
                            "" +
                            "BEIZHU" +
                            "SCN:01" +
                            "UMPR NUM:4F682D56" +
                            "TC:EF789E918A548668" +
                            "TUR:008004E000" +
                            "AID:A000000333010101" +
                            "TSI:F800" +
                            "ATC:0440" +
                            "APPLAB:PBOC DEBIT" +
                            "APPNAME:PBOC DEBIT" +
                            "AIP:7C00" +
                            "CUMR:020300" +
                            "IAD:07010103602002010A01000000000005DD79CB" +
                            "TermCap:EOE1C8" +
                            "CARD HOLDER SIGNATURE" +
                            "I ACKNOWLEDGE SATISFACTORY RECEIPT OF RELATIVE GOODS/SERVICE" +
                            "I ACKNOWLEDGE SATISFACTORY RECEIPT OF RELATIVE GOODS/SERVICE" +
                            "I ACKNOWLEDGE SATISFACTORY RECEIPT OF RELATIVE GOODS/SERVICE" +
                            "" +
                            "Demo receipts,do not sign!" +
                            "" +
                            "");
            sendSuccessLog(mContext.getString(R.string.operation_succeed));
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void printTextForFormat(Map<String, Object> param, ActionCallback callback) {
        try {
            Format format = new Format();
            format.setParameter(Format.FORMAT_FONT_SIZE, Format.FORMAT_FONT_SIZE_SMALL);
            format.setParameter(Format.FORMAT_FONT_BOLD, Format.FORMAT_FONT_VAL_TRUE);
            device.printText(format, "This is printTextForFormat" +
                    "This is printTextForFormat\n" +
                    "This is printTextForFormat\n" +
                    "This is printTextForFormat\n" +
                    "This is printTextForFormat\n" +
                    "This is printTextForFormat\n" +
                    "This is printTextForFormat\n" +
                    "This is printTextForFormat\n" +
                    "This is printTextForFormat\n");
            sendSuccessLog(mContext.getString(R.string.operation_succeed));
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }


    public void sendESCCommand(Map<String, Object> param, ActionCallback callback) {
        byte[] command = new byte[]{
                (byte) 0x1D, (byte) 0x4C, 10,1
//                (byte) 0x1B, (byte) 0x24, 10,1
        };
        try {
            device.sendESCCommand(command);
            sendSuccessLog(mContext.getString(R.string.operation_succeed));
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void queryStatus(Map<String, Object> param, ActionCallback callback) {
        try {
            int status = device.queryStatus();
            sendSuccessLog(mContext.getString(R.string.operation_succeed)
                    + " Status: "
                    + (status == PrinterDevice.STATUS_OUT_OF_PAPER ? "out of paper" : "paper exists"));
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void cutPaper(Map<String, Object> param, ActionCallback callback) {
        try {
            device.cutPaper();
            sendSuccessLog(mContext.getString(R.string.operation_succeed));
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void printBitmap(Map<String, Object> param, ActionCallback callback) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(mContext.getResources().getAssets()
                    .open("printer_barcode_low.png"));
        } catch (Exception e1) {
            e1.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
        try {
            Format format = new Format();
            format.setParameter(Format.FORMAT_ALIGN, Format.FORMAT_ALIGN_RIGHT);
            device.printBitmap(format, bitmap);
            device.printText("printBitmap end,this bitmap show align right,printBitmap also support other format param.\n\n\n");
            sendSuccessLog(mContext.getString(R.string.operation_succeed));
        } catch (DeviceException e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }

    public void printHtml(final Map<String, Object> param, final ActionCallback callback) {
        try {
            final String htmlContent =
                    "<html>" +
                    "<body>" +
                    "Demo receipts<br />" +
                    "MERCHANT COPY<br />" +
                    "<hr/>"  +
                    "<br />" +
                    "<br />" +
                    "<br />" +
                    "</body>" +
                    "</html>";

            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());//"MM/dd/yyyy hh:mm:ss aa"
            SimpleDateFormat lastPurdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
            String lastPurchaseDate = lastPurdf.format(c);
            String currentDate = df.format(c);

            String style = "";//"<p style=\"text-align:left;font-size:23px;\">";

            Bitmap bitmap = BitmapFactory.decodeStream(mContext.getAssets().open("logo2.png"));

            Format format = new Format();
            format.setParameter(Format.FORMAT_ALIGN, Format.FORMAT_ALIGN_CENTER);
            device.printBitmap(format, bitmap);

            ReceivePurchaseDataModel receivePurchaseDataModel1 = new ReceivePurchaseDataModel();
            receivePurchaseDataModel1.setPaybleAmount("1840.0");
            receivePurchaseDataModel1.setQty(1.0);
            receivePurchaseDataModel1.setPrice(1884.50);
            receivePurchaseDataModel1.setTypeOfFood("डिवाइस लॉग को सर्वर पर भेजें");
            receivePurchaseDataModel1.setCenterName("उत्तर प्रदेश नागरिक आपूर्ति");
            receivePurchaseDataModel1.setPurchaseID("136472821");
            receivePurchaseDataModel1.setFarmerName("धीरेंद्र वर्मा");

            String endTag = "<br />";

            String htmlText = "<html>" +
                    "<body>" +
//                    "<p style=\"text-align:center;font-size:33px;\">"+mContext.getString(R.string.print_dept) +endTag+
//                    "<p style=\"text-align:center;font-size:32px;\">"+mContext.getString(R.string.print_state) +endTag+
//                    "<p style=\"text-align:center;font-size:31px;\">"+mContext.getString(R.string.print_eProc) +endTag+
//                    "<p style=\"text-align:center;font-size:30px;\">"+mContext.getString(R.string.print_head) +endTag+
                    style+mContext.getString(R.string.p_dotline_1) +endTag+
                    style+mContext.getString(R.string.p_district) + " : " + "आँध्रप्रदेश" +endTag+
                    style+mContext.getString(R.string.p_purchaseCentre) + " : " + "उत्तर प्रदेश नागरिक आपूर्ति" +endTag+
                    style+mContext.getString(R.string.p_seasonId_kharif) + " : " + "2021-22" +endTag+
                    style+mContext.getString(R.string.p_purchaseId) + " : " + receivePurchaseDataModel1.getPurchaseID() +endTag+
                    style+mContext.getString(R.string.p_purchaseDt) + " : " + currentDate +endTag+
                    style+mContext.getString(R.string.p_farmerName) + " : " + receivePurchaseDataModel1.getFarmerName() +endTag+
                    style+mContext.getString(R.string.p_typeOffood) + " : " + receivePurchaseDataModel1.getTypeOfFood() +endTag+
                    style+mContext.getString(R.string.p_qty) + " : " + receivePurchaseDataModel1.getQty() + mContext.getString(R.string.quintalunits) +endTag+
                    style+mContext.getString(R.string.p_price) + " : " + receivePurchaseDataModel1.getPrice() +endTag+
                    style+mContext.getString(R.string.p_payableAmt) + " : " + receivePurchaseDataModel1.getPaybleAmount() +endTag+
                    style+mContext.getString(R.string.p_dotline_1) +endTag+
                    style+mContext.getString(R.string.p_farm_sig) +endTag+
                    style+style+mContext.getString(R.string.p_incharge_sig)+endTag+
                    style+mContext.getString(R.string.p_incharge_name) + " : " + "जेजो किरण" +endTag+
                    style+mContext.getString(R.string.p_print_date) + currentDate +"<br /><br />"+
                    style+mContext.getString(R.string.p_note)+""+
                    "<br />" +
                    "<br />" +
                    "<br />" +
                    "</body>" +
                    "</html>";
            Log.e("Q2PosPrint",htmlText);
            device.printHTML(htmlText,null);

            Log.e("printHTML",htmlContent);
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    try {
                        device.printHTML(htmlContent, null);
                        sendSuccessLog(mContext.getString(R.string.operation_succeed));
                    } catch (Exception e) {
                        sendFailedLog(mContext.getString(R.string.operation_failed));
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            sendFailedLog(mContext.getString(R.string.operation_failed));
        }
    }


    public void printBarcode(Map<String, Object> param, ActionCallback callback) {
        try {
            Format format = new Format();
            format.setParameter("brI-location", "up");
            //0, 01234567896
            //1, "04310000526"
            //
            device.printBarcode(format, PrinterDevice.BARCODE_CODE128, "000023");
            device.printText("\n\n\n");
            sendSuccessLog(mContext.getString(R.string.operation_succeed));
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
