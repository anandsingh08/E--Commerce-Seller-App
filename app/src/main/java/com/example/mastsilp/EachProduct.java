package com.example.mastsilp;

public class EachProduct {

    public String productdesc;
   // public String quantity;
    public String image;

    public EachProduct(String productdesc, String image) {
        this.productdesc = productdesc;
        //this.quantity = quantity;
        this.image = image;
    }

    public String getProductdesc() {
        return productdesc;
    }

   // public String getQuantity() {
     //   return quantity;
    //}

    public String getImage() {
        return image;
    }
}
