package com.ehtp.ehtpeasykool.Model;

public class Article {
    private String description,image,nom, poids,type;

    public Article(String description,String image,String nom,String poids ,String type ) {
        this.nom = nom;
        this.type = type;
        this.description = description;
        this.image = image;
        this.poids = poids;
    }

    public Article() {
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPoids(String poids) {
        this.poids = poids;
    }

    public String getNom() {
        return nom;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getPoids() {
        return poids;
    }

}
