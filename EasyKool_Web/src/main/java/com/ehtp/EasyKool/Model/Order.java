package com.ehtp.EasyKool.Model;

public class Order {

    private String articleid;
    private String articlename;
    private String quantity;
    private String points;
    private String date;

    public Order(String articleid, String articlename, String quantity, String points, String date) {
        this.articleid = articleid;
        this.articlename = articlename;
        this.quantity = quantity;
        this.points = points;
        this.date = date;
    }

    public String getArticleid() {
        return articleid;
    }

    public void setArticleid(String articleid) {
        this.articleid = articleid;
    }

    public String getArticlename() {
        return articlename;
    }

    public void setArticlename(String articlename) {
        this.articlename = articlename;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "{" +

                ", articlename='" + articlename + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }
}
