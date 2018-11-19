package com.example.mdbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import android.view.Menu;
<<<<<<< HEAD
import android.widget.PopupMenu;
=======
>>>>>>> 255f55b7ed46af6bdfd59299ae1d497079e6e2f6

public class ListProblemActivity extends AppCompatActivity {

    String problems[] = {"Problem A", "Problem B", "Problem C", "Problem D"};
    EditText searchBar;
    Button addProblemButton;
    Button search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_problem);
        addProblemButton = findViewById(R.id.problemButton);
        // When a user click on s
        addProblemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If the save button is clicked, direct the user to the add problem page
                addProblem();
            }
        });

        ArrayAdapter problemAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, problems);
        ListView problemListView = findViewById(R.id.problemList);
        problemListView.setAdapter(problemAdapter);

<<<<<<< HEAD
//        problemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                //spot is the item clicked
//                mCustomFragment = CustomFragment.newInstance(position);
//            }
//        });
=======
        problemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int spot, long l) {
                //spot is the item clicked
                Intent editRecord = new Intent(getApplicationContext(), //Jame's activity.class);
                editRecord.putExtra("Problem", spot);
                startActivity(editRecord);
            }
        });
>>>>>>> 255f55b7ed46af6bdfd59299ae1d497079e6e2f6

        // When a user clicked search button
        search = findViewById(R.id.go);
        searchBar = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = searchBar.getText().toString();
                // If the search bar is empty, tell the user to enter a keyword
                if (keyword.equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(), "Please enter a search keyword.", Toast.LENGTH_SHORT).show();
                }
                // If the search bar is not empty, search
                // TODO: Retrieve Search Result
            }
        });

    }




<<<<<<< HEAD

=======
    //Menu on the action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    //On Click the menu on the action bar
    @Override
    public boolean onOptionItemSelected(MenuItem item){
        if(item.getItemId() == R.id.settings){
            Toast.makeText(this, "View Account Details", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ViewAccountDetailActivity.class);
            startActivity(intent);
        }
        return true;
    }
>>>>>>> 255f55b7ed46af6bdfd59299ae1d497079e6e2f6

    public void addProblem(){
        Intent intent = new Intent(this, AddProblemActivity.class);
        startActivity(intent);
    }
}
