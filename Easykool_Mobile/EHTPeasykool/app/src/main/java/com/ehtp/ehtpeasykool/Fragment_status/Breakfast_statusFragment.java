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


public class Breakfast_statusFragment extends Fragment {

    List<Status> Orders_status_breakfast=new ArrayList<>();
    String pickadate;
    String username;

    public Breakfast_statusFragment(String pickadate, String username) {
        this.pickadate = pickadate;
        this.username = username;
    }

    RecyclerView recyclerView_breakfast;
    StatusAdapter statusAdapter_breakfast;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference reservation_breakfast;


    public Breakfast_statusFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_breakfast_status, container, false);
        reservation_breakfast=database.getReference("Reservation").child(pickadate).child(username).child("Breakfast").child("articles");
        recyclerView_breakfast=view.findViewById(R.id.status_recyclerview_breakfast);
        recyclerView_breakfast.setLayoutManager(new LinearLayoutManager(getContext()));

        reservation_breakfast.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Orders_status_breakfast.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Order order=dataSnapshot.getValue(Order.class);
                    Status status=new Status(order.getArticlename(),order.getPoints(),order.getQuantity());
                    Orders_status_breakfast.add(status);

                }
                statusAdapter_breakfast.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        statusAdapter_breakfast=new StatusAdapter(Orders_status_breakfast);
        recyclerView_breakfast.setAdapter(statusAdapter_breakfast);
        recyclerView_breakfast.setItemAnimator(new DefaultItemAnimator());


        // Inflate the layout for this fragment
        return view;
    }
}