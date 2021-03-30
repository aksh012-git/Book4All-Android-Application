package com.example.semester_6;

import android.net.Uri;

public class HomeFragmentModel
{
    String bookname;
    String authorname;
    String booktype;
    String rentingprice;
    String sellingprice;
    String address;
    String zipcode;
    String myUID;
    String key;
    String imgUrl;
    HomeFragmentModel()
    {

    }

    public HomeFragmentModel(String bookname, String authorname, String booktype, String rentingprice, String sellingprice, String address, String zipcode, String myUID, String key, String imgUrl) {
        this.bookname = bookname;
        this.authorname = authorname;
        this.booktype = booktype;
        this.rentingprice = rentingprice;
        this.sellingprice = sellingprice;
        this.address = address;
        this.zipcode = zipcode;
        this.myUID = myUID;
        this.key = key;
        this.imgUrl = imgUrl;
    }

    public String getBookname() {
        String y = bookname.substring(0,1);
        String x = bookname.substring(1,bookname.length());
        bookname = y.toUpperCase()+x;
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    public String getBooktype() {
        return booktype;
    }

    public void setBooktype(String booktype) {
        this.booktype = booktype;
    }

    public String getRentingprice() {
        return rentingprice;
    }

    public void setRentingprice(String rentingprice) {
        this.rentingprice = rentingprice;
    }

    public String getSellingprice() {
        return sellingprice;
    }

    public void setSellingprice(String sellingprice) {
        this.sellingprice = sellingprice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getMyUID() {
        return myUID;
    }

    public void setMyUID(String myUID) {
        this.myUID = myUID;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
