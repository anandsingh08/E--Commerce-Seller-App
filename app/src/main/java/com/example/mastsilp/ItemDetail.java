package com.example.mastsilp;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ItemDetail {
    public String productdesc;

    public String quantity;

    public String image;



    public ItemDetail() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public ItemDetail(String productdesc, String image,String quantity) {
        this.productdesc = productdesc;
        this.image = image;
        this.quantity=quantity;
    }

    public String getProductdesc() {
        return productdesc;
    }

    public void setProductdesc(String productdesc) {
        this.productdesc = productdesc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
