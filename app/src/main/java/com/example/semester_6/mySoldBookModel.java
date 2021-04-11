package com.example.semester_6;

public class mySoldBookModel
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
    String renttime;
    String wpnumber;
    mySoldBookModel()
    {

    }

    public mySoldBookModel(String bookname, String authorname, String booktype, String rentingprice, String sellingprice, String address, String zipcode, String myUID, String key, String imgUrl, String renttime, String wpnumber) {
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
        this.renttime = renttime;
        this.wpnumber = wpnumber;
    }

    public String getBookname() {
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

    public String getRenttime() {
        return renttime;
    }

    public void setRenttime(String renttime) {
        this.renttime = renttime;
    }

    public String getWpnumber() {
        return wpnumber;
    }

    public void setWpnumber(String wpnumber) {
        this.wpnumber = wpnumber;
    }
}
