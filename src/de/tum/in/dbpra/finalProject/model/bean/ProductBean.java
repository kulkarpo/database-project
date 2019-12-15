package de.tum.in.dbpra.finalProject.model.bean;

import java.sql.Time;
import java.util.Date;

public class ProductBean {
    int productId;
    String productName;
    Double unitPrice ;
    String vendor;
    String extraDescription;
    String productType;

    int remainingQuantity;
    int registeredQuantity;

    public ProductBean() {
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getExtraDescription() {
        return extraDescription;
    }

    public void setExtraDescription(String extraDescription) {
        this.extraDescription = extraDescription;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getRemainingQuantity() {
        return remainingQuantity;
    }

    public void setRemainingQuantity(int remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }

    public int getRegisteredQuantity() {
        return registeredQuantity;
    }

    public void setRegisteredQuantity(int registeredQuantity) {
        this.registeredQuantity = registeredQuantity;
    }

}
