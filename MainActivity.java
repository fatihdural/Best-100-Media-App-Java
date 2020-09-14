package com.example.myretrofit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FragmentManager manager;
    Spinner spinner;
    recyclerFragment reFrag;
    ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = findViewById(R.id.spinnerMediaType);
        final ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.Type, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        adapter.notifyDataSetChanged();
        spinner.setAdapter((SpinnerAdapter) adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if( i > 0){
                    String whichType = spinner.getSelectedItem().toString();
                    dialog = new ProgressDialog(MainActivity.this);
                    reFrag = new recyclerFragment(dialog, whichType);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
        final LinearLayout frame = findViewById(R.id.mainLinearLayout);
        Button bt = findViewById(R.id.goMediaButton);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setTitle("Getting JSON data");
                dialog.setMessage("Please wait...");
                dialog.show();
                frame.setVisibility(View.INVISIBLE);
                manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                Fragment recyclerFrag = reFrag;
                transaction.add(R.id.mainFrame, recyclerFrag, "recyclerFrag");
                transaction.commit();
            }
        });
    }
}
