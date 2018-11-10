package io.potter.partum.Model;

public class Order {
    private String Productid;
    private String ProductName;
    private String Quantity;
    private String Price;
    private String Discount;

    public Order() {
    }

    public Order(String productid, String productName, String quantity, String price, String discount) {
        Productid = productid;
        ProductName = productName;
        Quantity = quantity;
        Price = price;
        Discount = discount;
    }

    public String getProductid() {
        return Productid;
    }

    public void setProductid(String productid) {
        Productid = productid;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }
}
