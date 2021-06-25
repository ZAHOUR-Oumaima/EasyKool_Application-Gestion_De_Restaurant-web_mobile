package com.ehtp.EasyKool.Service;

import com.google.firebase.database.*;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class OrderService{

    static int total;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Reservation");


    // supprimer une réservation après sa validation
    public void orderComplete(String date, String nameStudent, String type){
        ref.child(date).child(nameStudent).child(type).removeValueAsync();
    }

    // Calculer le nbr de réservation aujourd'hui
    public int calculNbrReservation(){

        Boolean[] test = {false};

        final int[] nbrReservation = new int[1];

        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String currentDate = format.format(date);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    if(data.getKey().equals(currentDate)){
                        nbrReservation[0] = (int) data.getChildrenCount();
                        break;
                    }
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

        return nbrReservation[0];

    }


}