package com.wizarpos;

import java.io.Serializable;

public class ReceivePurchaseDataModel implements Serializable {

    private String purchaseID;

    private String paybleAmount;

    private String centerName;
    private String purchaseSMS;

    private String farmerName;
    private Double qty;
    private Double price;
    private String typeOfFood;

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getPurchaseSMS() {
        return purchaseSMS;
    }

    public void setPurchaseSMS(String purchaseSMS) {
        this.purchaseSMS = purchaseSMS;
    }

    public String getFarmerName() {
        return farmerName;
    }

    public void setFarmerName(String farmerName) {
        this.farmerName = farmerName;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public String getPurchaseID() {
        return purchaseID;
    }

    public void setPurchaseID(String purchaseID) {
        this.purchaseID = purchaseID;
    }

    public String getPaybleAmount() {
        return paybleAmount;
    }

    public void setPaybleAmount(String paybleAmount) {
        this.paybleAmount = paybleAmount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTypeOfFood() {
        return typeOfFood;
    }

    public void setTypeOfFood(String typeOfFood) {
        this.typeOfFood = typeOfFood;
    }

    @Override
    public String toString() {
        return "ReceivePurchaseDataModel{" +
                "purchaseID='" + purchaseID + '\'' +
                ", paybleAmount=" + paybleAmount +
                '}';
    }
}

