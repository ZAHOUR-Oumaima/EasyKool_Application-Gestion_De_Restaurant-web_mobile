package com.ehtp.ehtpeasykool.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ehtp.ehtpeasykool.Adapter.Adapter;
import com.ehtp.ehtpeasykool.CartActivities.CartDinnerActivity;
import com.ehtp.ehtpeasykool.FoodDetails.foodDinnerDetailsActivity;
import com.ehtp.ehtpeasykool.Model.Article;
import com.ehtp.ehtpeasykool.R;
import com.ehtp.ehtpeasykool.FoodDetails.foodBreakfastDetailsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DinnerFragment extends Fragment implements Adapter.ListItemClickListener {

    DatabaseReference mbase;
    Adapter adapter;
    ArrayList<Article> Articles;
    FloatingActionButton cartbutoon;
    String pickadate;

    public DinnerFragment(String pickadate) {
        this.pickadate = pickadate;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_dinner,container, false );

        mbase = FirebaseDatabase.getInstance().getReference("menu").child(pickadate).child("dinner");

        RecyclerView recyclerView=view.findViewById(R.id.recycler_view);

        Articles=new ArrayList<>();
        cartbutoon=view.findViewById(R.id.button_cart);

        adapter=new Adapter(Articles, this, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mbase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Articles.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Article article=dataSnapshot.getValue(Article.class);
                    Articles.add(article);

                }
                adapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        cartbutoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), CartDinnerActivity.class);
                intent.putExtra("pickadate",pickadate);
                startActivity(intent);
            }
        });

        return view;
    }

    //click item

    @Override
    public void onListItemClick(int position) {
        //Toast.makeText(getContext(), Articles.get(position).getNom(), LENGTH_SHORT).show();
        Intent intent=new Intent(getActivity(), foodDinnerDetailsActivity.class);
        intent.putExtra("ArticleId", String.valueOf(position));
        intent.putExtra("pickadate",pickadate);
        intent.putExtra("repas","dinner");
        intent.putExtra("Articlename",Articles.get(position).getNom());
        startActivity(intent);


    }
}