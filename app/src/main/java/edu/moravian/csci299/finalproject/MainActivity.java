package edu.moravian.csci299.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, BudgetTabActivity.class);
        startActivity(intent);

    }
}