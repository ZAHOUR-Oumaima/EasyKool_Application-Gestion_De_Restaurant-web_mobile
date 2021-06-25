package com.ehtp.EasyKool.Service;

import com.ehtp.EasyKool.Model.Article;
import com.ehtp.EasyKool.Model.Order;
import com.ehtp.EasyKool.Model.Request;
import com.google.firebase.database.*;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Service
public class AdminService {



    // retourner tous les réservation pour la date d'aujourd'hui
    public ArrayList<Request> getOrders(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        Boolean[] test = {false};

        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String currentDate = format.format(date);

        ArrayList<Request> requests = new ArrayList<>();

        DatabaseReference ref = database.getReference("Reservation").child(currentDate);



        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    Request request = new Request();
                    String name;
                    name = data.getKey();
                    request.setUsername(name);
                    ArrayList<Order> breakfast = new ArrayList<>();
                    ArrayList<Order> lunch = new ArrayList<>();
                    ArrayList<Order> dinner = new ArrayList<>();
                    breakfast = (ArrayList<Order>) data.child("Breakfast").child("articles").getValue();
                    lunch = (ArrayList<Order>) data.child("lunch").child("articles").getValue();
                    dinner = (ArrayList<Order>) data.child("dinner").child("articles").getValue();
                    request.setBreakfast(breakfast);
                    request.setDinner(dinner);
                    request.setLunch(lunch);

                    requests.add(request);
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

        return requests;

    }


}
