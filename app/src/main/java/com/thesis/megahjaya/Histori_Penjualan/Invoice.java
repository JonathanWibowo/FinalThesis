package com.thesis.megahjaya.Histori_Penjualan;

public class Invoice {

    String invoiceDate, customerName, customerAddress, customerInfo;
    Integer totalWholePrice;

    public Invoice(String invoiceDate, String customerName, String customerAddress, String customerInfo, Integer totalWholePrice) {
        this.invoiceDate = invoiceDate;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerInfo = customerInfo;
        this.totalWholePrice = totalWholePrice;
    }

    public String getInvoiceDate() {
        return invoiceDate;
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
