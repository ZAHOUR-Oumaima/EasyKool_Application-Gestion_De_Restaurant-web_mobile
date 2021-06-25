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


public class Lunch_statusFragment extends Fragment {

    List<Status> Orders_status_lunch=new ArrayList<>();
    String pickadate;
    String username;
    RecyclerView recyclerView_lunch;
    StatusAdapter statusAdapter_lunch;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference reservation_lunch;

    public Lunch_statusFragment( String pickadate, String username) {
        this.pickadate = pickadate;
        this.username = username;
    }

    public Lunch_statusFragment() {}
        // Required empty public constructor

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_lunch_status, container, false);

            reservation_lunch=database.getReference("Reservation").child(pickadate).child(username).child("lunch").child("articles");
            recyclerView_lunch=view.findViewById(R.id.status_recyclerview_lunch);
            recyclerView_lunch.setLayoutManager(new LinearLayoutManager(getContext()));

            reservation_lunch.addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                Orders_status_lunch.clear();
                                                                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                                                                    Order order=dataSnapshot.getValue(Order.class);
                                                                    Status status=new Status(order.getArticlename(),order.getPoints(),order.getQuantity());
                                                                    Orders_status_lunch.add(status);

                                                                }
                                                                statusAdapter_lunch.notifyDataSetChanged();
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError error) {

                                                            }
                                                        });

            // Inflate the layout for this fragment

            statusAdapter_lunch=new StatusAdapter(Orders_status_lunch);
            recyclerView_lunch.setAdapter(statusAdapter_lunch);
            recyclerView_lunch.setItemAnimator(new DefaultItemAnimator());

        return view;
    }
}