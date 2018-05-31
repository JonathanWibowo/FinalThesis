package com.thesis.megahjaya.Histori_Penjualan;

import java.util.ArrayList;

public class Invoice {

    String invoiceDate, invoiceNumber, customerName, customerAddress, customerInfo;
    Integer invoiceType, totalWholePrice;
//    ArrayList<Object> customerListMaterial;

    public Invoice(Integer invoiceType, /*String invoiceDate,*/ String invoiceNumber, String customerName, String customerAddress, String customerInfo, Integer totalWholePrice) {
        this.invoiceType = invoiceType;
//        this.invoiceDate = invoiceDate;
        this.invoiceNumber = invoiceNumber;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerInfo = customerInfo;
        this.totalWholePrice = totalWholePrice;
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

//    public String getInvoiceDate() {
//        return invoiceDate;
//    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public String getCustomerInfo() {
        return customerInfo;
    }

    public Integer getTotalWholePrice() {
        return totalWholePrice;
    }
}
