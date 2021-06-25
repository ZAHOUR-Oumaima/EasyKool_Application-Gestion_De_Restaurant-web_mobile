package com.ehtp.ehtpeasykool.CartActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ehtp.ehtpeasykool.Adapter.CartAdapter;
import com.ehtp.ehtpeasykool.Databse.Sqldatabase;
import com.ehtp.ehtpeasykool.Model.Order;
import com.ehtp.ehtpeasykool.Model.Request;
import com.ehtp.ehtpeasykool.Model.User;
import com.ehtp.ehtpeasykool.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CartBreakfastActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    CartAdapter cartAdapter;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference reservation;
    DatabaseReference users;
    private FirebaseAuth mAuth;
    String name;
    String pickadate;
    Button btn_reserver,btn_clean;
    List<Order> cart=new ArrayList<>();
    List<Order> filerdOrders=new ArrayList<>();
    SimpleDateFormat simpleDateFormat;
    TextView day,textView_points;
    int points;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_breakfast);

        database=FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        if(getIntent()!=null){
            pickadate=getIntent().getStringExtra("pickadate");

        Calendar calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd-MM-YYYY");
        String date =simpleDateFormat.format(calendar.getTime());

        reservation=database.getReference("Reservation").child(pickadate);
        users=database.getReference("users");

        recyclerView=findViewById(R.id.recyclerview_cart);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        btn_reserver=findViewById(R.id.btnreserver);
        btn_clean=findViewById(R.id.btncleancart);
        textView_points=findViewById(R.id.points);

        day=findViewById(R.id.cart_date);
        day.setText(pickadate);
        FirebaseUser firebaseUser=mAuth.getCurrentUser();
        String userId=firebaseUser.getUid();


        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user =snapshot.child(userId).getValue(User.class);
                name=user.getUsername();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btn_reserver.setBackgroundResource(R.drawable.button);
        btn_reserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (points > 2)
                    Toast.makeText(CartBreakfastActivity.this, "you have exceeded the point limit ", Toast.LENGTH_SHORT).show();
                else if(points==0)
                    Toast.makeText(CartBreakfastActivity.this, "there is nothing to reserve  ", Toast.LENGTH_SHORT).show();

                else {

                    Request request = new Request(filerdOrders);
                    reservation.child(name).child("Breakfast").setValue(request);
                    Toast.makeText(CartBreakfastActivity.this, "reservation successful", Toast.LENGTH_SHORT).show();
                    new Sqldatabase(getBaseContext()).cleanBreakfastCart();
                    finish();

                }

            }
        });

        btn_clean.setBackgroundResource(R.drawable.button);
        btn_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Sqldatabase(CartBreakfastActivity.this).cleanBreakfastCart();
                finish();
            }
        });

        LoadlistFood();
        filerdOrders=filterordersbydate(cart, pickadate);

            for (Order order:filerdOrders){
                int i=Integer.parseInt(order.getPoints());
                int j=Integer.parseInt(order.getQuantity());
                points+=(i*j);
            }
            textView_points.setText(String.valueOf(points));


    }
    }

    private void LoadlistFood() {

        cart=new Sqldatabase(this).getBreakfastCarts();

        cartAdapter =new CartAdapter(filterordersbydate(cart,pickadate),this);
        recyclerView.setAdapter(cartAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    public List<Order> filterordersbydate(List<Order> list,String date){
        List<Order> list2=new ArrayList<>();
        for(Order order:list){
            if(order.getDate().equals(date))
                list2.add(order);
        }

        return  list2;
    }
}


//already reserved :
/*
                reservation.child(name).child("Breakfast").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getChildren()==null) {
                            Request request = new Request(filterordersbydate(cart, pickadate));
                            reservation.child(name).child("Breakfast").setValue(request);
                            Toast.makeText(CartBreakfastActivity.this, "reservation successful", Toast.LENGTH_SHORT).show();
                            new Sqldatabase(getBaseContext()).cleanCart();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(CartBreakfastActivity.this);
                            builder.setMessage("You have already reserved ,do you want to replace old reservation ?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            Request request = new Request(filterordersbydate(cart, pickadate));
                                           // reservation.child(name).child("Breakfast").removeValue();
                                            reservation.child(name).child("Breakfast").setValue(request);
                                            Toast.makeText(CartBreakfastActivity.this, "reservation replaced", Toast.LENGTH_SHORT).show();
                                            new Sqldatabase(getBaseContext()).cleanCart();
                                            dialog.cancel();
                                           // finish();
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //  Action for 'NO' Button
                                            //   Toast.makeText(CartBreakfastActivity.this,"reservation replaced",Toast.LENGTH_SHORT).show();

                                            dialog.cancel();

                                        }
                                    }).show();


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

*/