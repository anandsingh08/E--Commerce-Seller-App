package com.example.mastsilp;

/*public class Product {
    private  String desc;
    private  String price;
    private  String photo;
   // private  boolean permission;

    public Product() {
    }

    public Product(String desc, String price, String photo) {
        this.desc = desc;
        this.price = price;
        this.photo = photo;
       // this.permission = permission;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

   // public boolean isPermission() {
   //     return permission;
    //}

    //public void setPermission(boolean permission) {
      //  this.permission = permission;
    //}
}*/


public class Product {
    public String productdesc;

    public String quantity;

    public String image;



    public Product() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Product(String productdesc, String image,String quantity) {
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

