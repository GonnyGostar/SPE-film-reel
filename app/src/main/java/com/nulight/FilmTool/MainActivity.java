package com.nulight.FilmTool;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.nulight.FilmTool.film.FilmReel;
import com.nulight.FilmTool.film.Flowchart;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {
    public FilmReel formula1Reel;
    public FilmReel formula2Reel;
    public FilmReel formula3Reel;
    public FilmReel formula4Reel;
    public Flowchart flowchart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        // To remember night mode
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean defaultValue = true;
        boolean night = sharedPref.getBoolean(getString(R.string.nightmode), defaultValue);
        if (night) {
            super.onCreate(savedInstanceState);
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES);
        }
        else {
            super.onCreate(savedInstanceState);
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        }

        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_form1, R.id.navigation_form2, R.id.navigation_form3, R.id.navigation_flowchart)
                .build();

        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowTitleEnabled(true);
        ab.setTitle(R.string.nulight);



        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);




        System.out.println("MAIN ACTIVITY CREATED");
        //Create instancves of film reel object once per activity rather than on fragment loading.
        try {
            this.flowchart = new Flowchart(this.getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        this.formula1Reel = new FilmReel(this.getApplicationContext());
        this.formula2Reel = new FilmReel(this.getApplicationContext());
        this.formula3Reel = new FilmReel(this.getApplicationContext());
        this.formula4Reel = new FilmReel(this.getApplicationContext());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sandwich_menu, menu);
        return true;
    }

    //ToDo: Implement switch case for different menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.settings:
                // User chose preferences
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.help:
                // User chose help
                break;
        }
        return true;
    }
}
