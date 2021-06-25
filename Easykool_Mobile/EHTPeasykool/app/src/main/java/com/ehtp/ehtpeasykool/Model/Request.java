package com.ehtp.ehtpeasykool.Model;

import java.util.List;

public class Request {

    private List<Order> articles;

    public List<Order> getArticles() {
        return articles;
    }

    public void setArticles(List<Order> articles) {
        this.articles = articles;
    }

    public Request(List<Order> articles) {
        this.articles = articles;
    }
}
