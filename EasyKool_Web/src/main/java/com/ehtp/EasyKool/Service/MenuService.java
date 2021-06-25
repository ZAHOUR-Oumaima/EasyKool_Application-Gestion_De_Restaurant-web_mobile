package com.ehtp.EasyKool.Service;

import com.ehtp.EasyKool.Model.Article;
import com.google.firebase.database.*;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

@Service
public class MenuService {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("menu");


    // ajouter des plats a un menu à un jour donnée
    /*public void addMenu(String date, ArrayList<Article> articles){
        for(Article article: articles){
            ref.child(date).child(article.getType().toLowerCase()).push().setValueAsync(article);
        }
    }*/
    public void addMenuBreakfast(String date, ArrayList<Article> articles){
        int i=0;
        for(Article article: articles){
            if(article.getType().toLowerCase().equals("breakfast")){
                ref.child(date).child("breakfast").child(String.valueOf(i)).setValueAsync(article);
                i++;
            }
        }
    }

    public void addMenuLunch(String date, ArrayList<Article> articles){
        int i=0;
        for(Article article: articles){
            if(article.getType().toLowerCase().equals("lunch")){
                ref.child(date).child("lunch").child(String.valueOf(i)).setValueAsync(article);
                i++;
            }
        }
    }

    public void addMenuDinner(String date, ArrayList<Article> articles){
        int i=0;
        for(Article article: articles){
            if(article.getType().toLowerCase().equals("dinner")){
                ref.child(date).child("dinner").child(String.valueOf(i)).setValueAsync(article);
                i++;
            }
        }
    }


    public String getDateByDay(String indexDay){
        Calendar calendar = Calendar.getInstance();
        ArrayList<String> daysOfWeek=new ArrayList<>() ;
        SimpleDateFormat simpledateformat;
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        simpledateformat=new SimpleDateFormat("dd-MM-yyyy");
        for(int i=0;i<7;i++) {
            daysOfWeek.add(simpledateformat.format( calendar.getTime()));
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        return daysOfWeek.get(Integer.parseInt(indexDay));
    }


    public ArrayList<Article> getMenu(String date, String type){
        Boolean[] test = {false};
        ArrayList<Article> articles = new ArrayList<>();
        ref.child(date).child(type).addValueEventListener(new ValueEventListener() {
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



}
