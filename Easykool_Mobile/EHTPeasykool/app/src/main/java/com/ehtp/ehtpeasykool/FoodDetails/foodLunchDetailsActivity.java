package com.ehtp.ehtpeasykool.FoodDetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.ehtp.ehtpeasykool.Databse.Sqldatabase;
import com.ehtp.ehtpeasykool.Model.Article;
import com.ehtp.ehtpeasykool.Model.Order;
import com.ehtp.ehtpeasykool.Model.RateFood;
import com.ehtp.ehtpeasykool.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import java.util.Arrays;

public class foodLunchDetailsActivity extends AppCompatActivity  implements RatingDialogListener {

    TextView food_name,food_description,food_price;
    ImageView food_image;
    String ArticleId;
    FirebaseDatabase database;
    DatabaseReference Articles;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btncart;
    ElegantNumberButton numberButton;

    FloatingActionButton btnrating;
    RatingBar ratingBar;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    DatabaseReference Rating;
    DatabaseReference getRating;

    Article article;
    String pickadate;
    String repas;
    String Articlename;

    int rate=0,total=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        database=FirebaseDatabase.getInstance();
        food_name=findViewById(R.id.food_name);
        food_description=findViewById(R.id.food_description);
        food_price=findViewById(R.id.food_price);
        food_image=findViewById(R.id.food_image);
        numberButton=findViewById(R.id.number_button);
        btncart=findViewById(R.id.btn_cart);
        collapsingToolbarLayout=findViewById(R.id.collapseActionView);

        btncart=findViewById(R.id.btn_cart);
        btnrating=findViewById(R.id.btn_rating);
        ratingBar=findViewById(R.id.ratingbar);


        if(getIntent()!=null){
            ArticleId=getIntent().getStringExtra("ArticleId");
            pickadate=getIntent().getStringExtra("pickadate");
            Articlename=getIntent().getStringExtra("Articlename");


        }
        Articles=database.getReference("menu").child(pickadate).child("lunch");

        if(!ArticleId.isEmpty())
            showdetails(ArticleId);



        btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Sqldatabase(getBaseContext()).addToLunchCart(new Order(
                        ArticleId,
                        food_name.getText().toString(),
                        numberButton.getNumber(),
                        food_price.getText().toString(),
                        pickadate
                ));
                Toast.makeText(foodLunchDetailsActivity.this, "Added to cart ", Toast.LENGTH_SHORT).show();
            }
        });
        btnrating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRating();
            }
        });

        getFoodrating(Articlename);

    }
    private void getFoodrating(String articlename) {
        //Toast.makeText(foodBreakfastDetailsActivity.this, articlename, Toast.LENGTH_SHORT).show();
        getRating=database.getReference("Rating").child(articlename);
        getRating.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    RateFood rateFood=dataSnapshot.getValue(RateFood.class);
                    rate += Integer.valueOf(rateFood.getRateValue());
                    total++;

                }
                if(total != 0){
                    float average=rate/total;
                    ratingBar.setRating(average);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showRating() {
        new AppRatingDialog.Builder()
                .setPositiveButtonText("Rate")
                .setNegativeButtonText("cancel")
                .setNeutralButtonText("Later")
                .setDefaultRating(1)
                .setNoteDescriptions(Arrays.asList("1","2","3","4","5"))
                .setTitle("Rate this food")
                .setDescription("give your feedback")
                .setHint("Please write your comment here ...")
                .setNoteDescriptions(Arrays.asList("Very Bad", "Not good", "Quite ok", "Very Good", "Excellent !!!"))
                .setNoteDescriptionTextColor(R.color.a6)
                .setCommentBackgroundColor(R.color.a4)
                .setStarColor(R.color.a3)
                .setCommentTextColor(R.color.white)
                .create(foodLunchDetailsActivity.this)
                .show();
    }



    private void showdetails(String articleId) {
        Articles.child(articleId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                article=snapshot.getValue(Article.class);
                //set image:
                Glide.with(getBaseContext()).load(article.getImage()).into(food_image);
                food_name.setText(article.getNom());
                food_price.setText(article.getPoids());
                food_description.setText(article.getDescription());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onNeutralButtonClicked() {

    }

    @Override
    public void onPositiveButtonClicked(int value, String comment) {
        mAuth = FirebaseAuth.getInstance();
        firebaseUser=mAuth.getCurrentUser();
        Rating=database.getReference("Rating").child(food_name.getText().toString());
        RateFood rateFood=new RateFood(String.valueOf(value),comment);
        Rating.child(firebaseUser.getUid()).setValue(rateFood).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                    Toast.makeText(foodLunchDetailsActivity.this, "you give a feedback,thanks! ", Toast.LENGTH_SHORT).show();


            }
        });
    }
}