package com.example.mastsilp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer=findViewById(R.id.drawer_layout);

        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,
       R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
       if(savedInstanceState==null){
        /*to make 1st screen products listed by default*/
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new ProductslistedbymeFragment()).commit();

        navigationView.setCheckedItem(R.id.nav_productslistedbyme);}

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){

            case R.id.nav_productslistedbyme:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProductslistedbymeFragment()).commit();
                break;

            case R.id.nav_mycredits:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MycreditsFragment()).commit();
                break;

            case R.id.nav_nearbyhubs:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new NearbyhubsFragment()).commit();
                break;

            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SettingsFragment()).commit();
                break;

            case R.id.nav_help:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HelpFragment()).commit();
                break;

            case R.id.nav_exit:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this,LoginActivity.class));
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            finish();
        }
    }
}
