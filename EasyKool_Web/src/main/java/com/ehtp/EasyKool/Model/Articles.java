package com.ehtp.EasyKool.Model;

import java.util.ArrayList;
import java.util.List;

public class Articles {

    private ArrayList<String> articles;

    public Articles() { }

    public Articles(ArrayList<String> articles) {
        this.articles = articles;
    }

    public ArrayList<String> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<String> articles) {
        this.articles = articles;
    }
}
