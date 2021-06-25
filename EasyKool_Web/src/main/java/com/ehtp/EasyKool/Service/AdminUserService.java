package com.ehtp.EasyKool.Service;

import com.ehtp.EasyKool.Model.AdminUser;
import com.google.firebase.database.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AdminUserService {

    // verifier si l'utilisateur existe dans la base de donnée
    public Boolean verifyUser(AdminUser user){
        final Boolean[] test = {false};
        Boolean existe = false;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("AdminUsers");

        ArrayList<AdminUser> users = new ArrayList<>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    users.add(data.getValue(AdminUser.class));
                    test[0] = true;
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

        for(AdminUser admin: users){
            if(admin.getUsername().equals(user.getUsername()) && admin.getPassword().equals(user.getPassword())){
                existe = true;
            }
        }

        return existe;
    }

}
