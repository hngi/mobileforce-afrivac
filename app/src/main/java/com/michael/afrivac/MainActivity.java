package com.michael.afrivac;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

/*import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;*/

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.Menu;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
//                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
//                .setDrawerLayout(drawer)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)){
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_home:
                //TODO: Link the HomeActivity.class (replace signup with activity class)
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
                Toast.makeText(MainActivity.this, "Change it to the real activity", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_account:
                //TODO: Link the Account.class(replace login with activity class)
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                Toast.makeText(MainActivity.this, "Open Account Activity", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_destination:
                //TODO: Link the Destination.class(replace destination with activity class)
                startActivity(new Intent(MainActivity.this, LocationActivity.class));
                Toast.makeText(MainActivity.this, "Open Destination Activity", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_hotel:
                //TODO: Link the Hotel.class(replace signup with activity class)
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
                Toast.makeText(MainActivity.this, "Open hotel activity", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_support:
                //TODO: Link the Support.class(replace signup with activity class)
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
                Toast.makeText(MainActivity.this, "Open support page", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_logout:
                //TODO: LoG User out
                Toast.makeText(MainActivity.this, "log user out", Toast.LENGTH_SHORT).show();
                break;
            default:
                return true;
        }
        return true;

    }
}

 /*   public void convertCurrency(View view){
    EditText editText = (EditText) findViewById(R.id.edtText);
    int currency = Integer.parseInt(editText.getText().toString());
    double result;

    private AppBarConfiguration mAppBarConfiguration;

    switch (currency){
    case 1: //Dollar to Naira
    double dollar = 360;
    result = currency * dollar;
    break;
    case 2: //Pounds to Naira
    double pounds = 450;
    result = currency * pounds;
    break;

    default:
    throw new IllegalStateException("Unexpected value: " + currency);
    }

    Toast.makeText(MainActivity.this, Double.toString(result), Toast.LENGTH_LONG).show();
    }*/