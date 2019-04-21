package com.example.cameraviewer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.content.Intent;

public class AddCameraActivity extends AppCompatActivity {

    private CamViewDbHelper addCamDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_camera);
    }

    public void addCamera(View view) {
        addCamDbHelper = new CamViewDbHelper(this);

        EditText insertName = (EditText) findViewById(R.id.newName);
        EditText insertAddress = (EditText) findViewById(R.id.newAddress);

        addCamDbHelper.insertNewCamera(insertName.getText().toString(), insertAddress.getText().toString());

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
