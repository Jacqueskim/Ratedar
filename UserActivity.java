package com.example.firebasedemoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ListView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        /* For storing single value*/
//        FirebaseDatabase.getInstance().getReference().child("ECE150").child("Lecture 7").setValue("SH1430");

        /* Use HashMap for storing multiple values */

         HashMap<String, Object> map = new HashMap<>();
        map.put("Title", "Group1");
        map.put("Description", "Rating post for the first blog");
        map.put("Rating", 1);
//        map.put("Name", "ECE150");
//        map.put("Email", "ece150@ucsb.edu");
        FirebaseDatabase.getInstance().getReference().child("ECE150").child("Multiple Values").updateChildren(map);
    }

    public void logout_user(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, StartActivity.class));
        Toast.makeText(this, "Logged out", Toast.LENGTH_LONG).show();
        finish();
    }

    public void database_entry(View view) {
        EditText firstname = findViewById(R.id.firstname);
        EditText lastname = findViewById(R.id.lastname);
        String txtfirst = firstname.getText().toString();
        String txtlast = lastname.getText().toString();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("ECE150").child("USER DATA").push();
        //Hash map: Data structure that implements an associative array
        HashMap<String, Object> map = new HashMap<>();
        map.put("First Name", txtfirst);
        map.put("Last Name", txtlast);

        userRef.setValue(map);

        Toast.makeText(UserActivity.this, "WE are here", Toast.LENGTH_SHORT).show();

//        FirebaseDatabase.getInstance().getReference().child("ECE150").child("USER DATA").updateChildren(map);
    }

    public void retrieve_entry(View view) {

        Toast.makeText(UserActivity.this, "WE are at Retrieve", Toast.LENGTH_SHORT).show();

        ListView listView =  findViewById(R.id.listView);

        ArrayList<String> list = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.list_item, list);
        listView.setAdapter(adapter);

        DatabaseReference reference =  FirebaseDatabase.getInstance().getReference().child("ECE150").child("USER DATA");
        //Read all the data in sub branch 'USER NAME'
        reference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        list.clear();
            for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
            list.add(snapshot.getValue().toString());
        }
            adapter.notifyDataSetChanged();
        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {

         }
        });

    }
}