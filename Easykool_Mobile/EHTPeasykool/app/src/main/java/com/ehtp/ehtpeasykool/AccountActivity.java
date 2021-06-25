package com.ehtp.ehtpeasykool;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ehtp.ehtpeasykool.Databse.Sqldatabase;
import com.ehtp.ehtpeasykool.Fragment.DinnerFragment;
import com.ehtp.ehtpeasykool.Fragment.LunchFragment;
import com.ehtp.ehtpeasykool.Fragment.BreakfastFragment;
import com.ehtp.ehtpeasykool.Model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountActivity extends AppCompatActivity {

    FirebaseUser firebaseUser;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    CircleImageView profile_image;
    TextView username;
    DatabaseReference myRef;
    ImageView calendar_image;
    TextView date;
    Button cartbutoon ;
    String pickadate;
    String name;

    Calendar calendar;
    BottomNavigationView bottomNavigationView;

    private BreakfastFragment breakfastFragment;
    private LunchFragment lunchFragment;
    private DinnerFragment dinnerFragment;

    Sqldatabase sqldatabase;

    SimpleDateFormat simpleDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);


        //sqllite :
       sqldatabase=new Sqldatabase(this);
        sqldatabase.cleanBreakfastCart();
        sqldatabase.cleanLunchCart();
       sqldatabase.cleanDinnerCart();


        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        myRef = database.getReference("users").child(firebaseUser.getUid());


        bottomNavigationView =findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(nav_listener);

        profile_image=findViewById(R.id.image_profile);
        calendar_image=findViewById(R.id.image_calendar);
        simpleDateFormat = new SimpleDateFormat("dd-MM-YYYY");

        date=findViewById(R.id.datepicker);
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,1);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        date.setText(simpleDateFormat.format(calendar.getTime()));
        pickadate=date.getText().toString();

        breakfastFragment=new BreakfastFragment(pickadate);
        lunchFragment=new LunchFragment(pickadate);
        dinnerFragment=new DinnerFragment(pickadate);

        calendar_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(AccountActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                calendar.set(year, month, day);
                                date.setText(simpleDateFormat.format(calendar.getTime()));
                                pickadate=date.getText().toString();
                                breakfastFragment=new BreakfastFragment(pickadate);
                                lunchFragment=new LunchFragment(pickadate);
                                dinnerFragment=new DinnerFragment(pickadate);

                                int itemslected =bottomNavigationView.getSelectedItemId();
                                switch (itemslected){
                                    case R.id.nav_breafast:
                                        setFragment(breakfastFragment);
                                        break;
                                    case R.id.nav_lunch:
                                        setFragment(lunchFragment);
                                        break;

                                    case R.id.nav_dinner:
                                        setFragment(dinnerFragment);
                                        break;
                                }
                               // setFragment(breakfastFragment);
                                //Toast.makeText(AccountActivity.this,pickadate,Toast.LENGTH_SHORT).show();
                               // setFragment(breakfastFragment);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()+24*60*60*1000);
                datePickerDialog.show();
            }
        });


        username=findViewById(R.id.username);
        get_user(username,profile_image);

        //Logout ====================================
        click_image_profile(profile_image);
        setFragment(breakfastFragment);

    }


    private    BottomNavigationView.OnNavigationItemSelectedListener nav_listener =new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){

                case R.id.nav_breafast:
                    setFragment(breakfastFragment);
                    break;
                case R.id.nav_lunch:
                    setFragment(lunchFragment);
                    break;

                case R.id.nav_dinner:
                    setFragment(dinnerFragment);
                    break;

            }
            return true;
        }
    };


    private void setFragment(Fragment Fragment) {
        FragmentTransaction fr=getSupportFragmentManager().beginTransaction();
        fr.replace(R.id.fragment_container,Fragment);
        fr.commit();
    }


    private void click_image_profile(CircleImageView profile_image){
        profile_image.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View view) {

                PopupMenu popup=new PopupMenu(AccountActivity.this,profile_image);
                popup.getMenuInflater().inflate(R.menu.options_menu,popup.getMenu());
                popup.setForceShowIcon(true);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()){

                            case R.id.logout:
                                FirebaseAuth.getInstance().signOut();
                                Intent intent =new Intent(AccountActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                                return  true;
                            case R.id.status:
                                Intent intent2 =new Intent(AccountActivity.this,StatusActivity.class);
                                intent2.putExtra("pickadate",pickadate);
                                intent2.putExtra("username",name);
                                startActivity(intent2);

                        }
                        return false;
                    }
                });

                popup.show();
            }
        });


    }


    public void  get_user(TextView username, CircleImageView profile_image){
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        myRef = database.getReference("users").child(firebaseUser.getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user =snapshot.getValue(User.class);
                name=user.getUsername();
                username.setText(name);
                if (user.getImageURL().equals("default")){
                    profile_image.setImageResource(R.drawable.profile_icon);
                }
                else {
                    Glide.with(AccountActivity.this).load(user.getImageURL()).into(profile_image);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}

