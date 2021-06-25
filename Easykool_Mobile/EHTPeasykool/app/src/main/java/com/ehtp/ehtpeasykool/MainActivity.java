package com.ehtp.ehtpeasykool;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ehtp.ehtpeasykool.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    String userId;


    EditText mail,password;
    Button login;
    TextView forgetpassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mail=findViewById(R.id.id_email);
        password=findViewById(R.id.id_password);
        login=findViewById(R.id.id_login_btn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s_mail=mail.getText().toString();
                String s_password=password.getText().toString();


                if(TextUtils.isEmpty(s_mail) || TextUtils.isEmpty(s_password)){
                    Toast.makeText(com.ehtp.ehtpeasykool.MainActivity.this,"all fields are required !", Toast.LENGTH_SHORT).show();
                }
                else if (s_password.length()<6){
                    Toast.makeText(com.ehtp.ehtpeasykool.MainActivity.this,"password must be at least 6 caracters",Toast.LENGTH_SHORT).show();

                }
                else {
                    login(s_mail, s_password);
                }
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent=new Intent(MainActivity.this,AccountActivity.class);
            startActivity(intent);
            finish();
        }
    }



    public  void login(String s_mail,String s_password){

        mAuth.signInWithEmailAndPassword(s_mail,s_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //setUsers_id();
                    firebaseUser=mAuth.getCurrentUser();
                    assert firebaseUser!=null;
                    userId=firebaseUser.getUid();
                    reference= FirebaseDatabase.getInstance().getReference("users").child(userId);
                    writeNewUser(userId,s_mail);

            /*        reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Boolean etat=false;
                            for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                                User user =dataSnapshot.getValue(User.class);
                                if (user.getMail().equals(s_mail)){
                                    etat=true;
                                    break;
                                }
                            }
                            if (etat){

                                Intent intent=new Intent(MainActivity.this,AccountActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();

                            }
                            else {

                                writeNewUser(userId,s_mail);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });*/


                }

                else {
                    Toast.makeText(MainActivity.this,"Authentication failed",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void writeNewUser(String userId, String s_mail) {

        reference= FirebaseDatabase.getInstance().getReference("users").child(userId);
        reference.setValue(new User(userId,s_mail.substring(0,s_mail.indexOf("@")),s_mail,"default")).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Intent intent=new Intent(MainActivity.this,AccountActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                }

            }
        });

    }

}

