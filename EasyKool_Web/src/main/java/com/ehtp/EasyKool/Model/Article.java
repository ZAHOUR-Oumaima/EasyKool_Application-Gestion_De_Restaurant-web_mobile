package com.ehtp.EasyKool.Model;

public class Article {
    private String description, image, nom, poids, type;

    public Article(){}

    public Article(String description, String image, String nom, String poids, String type) {
        this.description = description;
        this.image = image;
        this.nom = nom;
        this.poids = poids;
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPoids() { return poids; }

    public void setPoids(String poids) {
        this.poids = poids;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
