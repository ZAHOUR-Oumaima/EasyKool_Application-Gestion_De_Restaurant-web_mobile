package com.ehtp.ehtpeasykool.Fragment_status;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ehtp.ehtpeasykool.Adapter.StatusAdapter;
import com.ehtp.ehtpeasykool.Model.Order;
import com.ehtp.ehtpeasykool.Model.Status;
import com.ehtp.ehtpeasykool.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Dinner_statusFragment extends Fragment {
    List<Status> Orders_status_dinner=new ArrayList<>();
    String pickadate;
    String username;

    public Dinner_statusFragment(String pickadate, String username) {
        this.pickadate = pickadate;
        this.username = username;
    }

    RecyclerView recyclerView_dinner;
    StatusAdapter statusAdapter_dinner;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference reservation_dinner;


    public Dinner_statusFragment() { }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_dinner_status, container, false);

        reservation_dinner=database.getReference("Reservation").child(pickadate).child(username).child("dinner").child("articles");
        recyclerView_dinner=view.findViewById(R.id.status_recyclerview_dinner);
        recyclerView_dinner.setLayoutManager(new LinearLayoutManager(getContext()));

        reservation_dinner.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Orders_status_dinner.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Order order=dataSnapshot.getValue(Order.class);
                    Status status=new Status(order.getArticlename(),order.getPoints(),order.getQuantity());
                    Orders_status_dinner.add(status);

                }
                statusAdapter_dinner.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Inflate the layout for this fragment

        statusAdapter_dinner=new StatusAdapter(Orders_status_dinner);
        recyclerView_dinner.setAdapter(statusAdapter_dinner);
        recyclerView_dinner.setItemAnimator(new DefaultItemAnimator());
        
        return  view;
    }
}