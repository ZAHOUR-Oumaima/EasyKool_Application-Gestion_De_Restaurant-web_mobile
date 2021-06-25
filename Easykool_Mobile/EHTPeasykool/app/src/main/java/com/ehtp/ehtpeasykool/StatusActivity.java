package com.ehtp.ehtpeasykool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.ehtp.ehtpeasykool.Fragment_status.Breakfast_statusFragment;
import com.ehtp.ehtpeasykool.Fragment_status.Dinner_statusFragment;
import com.ehtp.ehtpeasykool.Fragment_status.Lunch_statusFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StatusActivity extends AppCompatActivity {

    String pickadate;
    String username;
    TextView status_date;
    private Breakfast_statusFragment breakfast_statusFragment;
    private Lunch_statusFragment lunch_statusFragment;
    private Dinner_statusFragment dinner_statusFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        pickadate=getIntent().getStringExtra("pickadate");
        username=getIntent().getStringExtra("username");
        status_date=findViewById(R.id.status_date);
        status_date.setText(pickadate);
        breakfast_statusFragment=new Breakfast_statusFragment(pickadate,username);
        lunch_statusFragment=new Lunch_statusFragment(pickadate,username);
        dinner_statusFragment=new Dinner_statusFragment(pickadate,username);

        BottomNavigationView bottomNavigationView =findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(nav_listener);

        setFragment(breakfast_statusFragment);

    }

    private    BottomNavigationView.OnNavigationItemSelectedListener nav_listener =new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){

                case R.id.nav_breafast:
                    setFragment(breakfast_statusFragment);
                    break;
                case R.id.nav_lunch:
                    setFragment(lunch_statusFragment);
                    break;

                case R.id.nav_dinner:
                    setFragment(dinner_statusFragment);
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

}