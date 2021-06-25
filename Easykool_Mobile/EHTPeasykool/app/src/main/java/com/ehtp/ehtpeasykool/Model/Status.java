package com.ehtp.ehtpeasykool.Model;

public class Status {
    String articlename;
    String articlepoints;
    String quantity;

    public String getArticlename() {
        return articlename;
    }

    public void setArticlename(String articlename) {
        this.articlename = articlename;
    }

    public String getArticlepoints() {
        return articlepoints;
    }

    public void setArticlepoints(String articlepoints) {
        this.articlepoints = articlepoints;
    }

    public String getEtat() {
        return quantity;
    }

    public void setEtat(String etat) {
        this.quantity = etat;
    }

    public Status(String articlename, String articlepoints, String quantity) {
        this.articlename = articlename;
        this.articlepoints = articlepoints;
        this.quantity = quantity;
    }
}
