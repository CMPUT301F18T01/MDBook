package com.example.mdbook;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class ListPatientProblemActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView headerPatientProblem;
    private ListView patientProblemsContainer;

    private ArrayAdapter<String> problemListAdapter;


    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_patient_problem);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        headerPatientProblem = (TextView)findViewById(R.id.headerPatientProblems);

        patientProblemsContainer = (ListView)findViewById(R.id.listPatientProblems);

        String [] problems = new String[]{"Problem 1", "Problem 2", "Problem 3", "Problem 4"};
        ArrayList<String> problemList = new ArrayList<String>();
        problemList.addAll(Arrays.asList(problems));


        problemListAdapter = new ArrayAdapter<String>(this, R.layout.simple_list, problemList);
        patientProblemsContainer.setAdapter(problemListAdapter);

        Intent intent = getIntent();
        String nameOfPatient = intent.getExtras().getString("nameOfPatient");
        headerPatientProblem.setText("List of problems for: " + nameOfPatient);

        navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {

        switch(menuItem.getItemId()){
            case R.id.navLogOut:
//                Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show();
                Intent logOutIntent = new Intent(this, LoginActivity.class);
                startActivity(logOutIntent);
                break;
        }

        return false;
    }
}
