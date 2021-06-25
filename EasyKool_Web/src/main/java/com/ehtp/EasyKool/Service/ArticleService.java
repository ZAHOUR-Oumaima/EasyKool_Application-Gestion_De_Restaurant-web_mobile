package com.ehtp.EasyKool.Service;

import com.ehtp.EasyKool.Model.Article;
import com.google.firebase.database.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class ArticleService {

    // retourne tous les articles qu'on a dans la base de données
    public ArrayList<Article> getArticles(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        Boolean[] test = {false};

        DatabaseReference ref = database.getReference("articles");
        ArrayList<Article> articles = new ArrayList<>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    articles.add(data.getValue(Article.class));
                }
                test[0] = true;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        while(test[0] == false){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return articles;
    }


    public void addArticle(Article article){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("articles");

        //article.setImage("https://assets.afcdn.com/recipe/20200408/109520_w1024h1024c1cx1866cy2800.webp");

        ref.push().setValueAsync(article);

    }


    public Article getArticleByName(String nomArticle){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("articles");

        final Article[] article = new Article[1];
        Boolean[] test = {false};

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    if(data.getValue(Article.class).getNom().equals(nomArticle)){
                        article[0] = data.getValue(Article.class);
                        test[0] = true;
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        while(test[0] == false){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return article[0];
    }


    public void editArticle(String nomArticle, Article newArticle){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("articles");

        // valeur par défaut on doit ajouter if image=null
        // newArticle.setImage("https://assets.afcdn.com/recipe/20200408/109520_w1024h1024c1cx1866cy2800.webp");

        Boolean[] test = {false};

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    if(data.getValue(Article.class).getNom().equals(nomArticle)){
                        String key = data.getKey();

                        DatabaseReference articleRef = ref.child(key);
                        Map<String, Object> articleUpdate = new HashMap<>();
                        articleUpdate.put("description",newArticle.getDescription());
                        articleUpdate.put("image",newArticle.getImage());
                        articleUpdate.put("nom",newArticle.getNom());
                        articleUpdate.put("poids",newArticle.getPoids());
                        articleUpdate.put("type",newArticle.getType());

                        articleRef.updateChildrenAsync(articleUpdate);
                        test[0] = true;
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        while(test[0] == false){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // supprimer un article
    public void deleteArticle(String nomArticle){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("articles");

        Boolean[] test = {false};

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    if(data.getValue(Article.class).getNom().equals(nomArticle)){
                        String key = data.getKey();

                        ref.child(key).removeValueAsync();

                        test[0] = true;
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        while(test[0] == false){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public ArrayList<Article> getArticleByType(String type, ArrayList<Article> articles){
        ArrayList<Article> articlesResult = new ArrayList<>();
        for(Article article: articles){
            if(article.getType().equals(type)){
                articlesResult.add(article);
            }
        }
        return articlesResult;
    }

}
