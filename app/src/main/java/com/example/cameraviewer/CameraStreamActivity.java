package com.example.cameraviewer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

public class CameraStreamActivity extends AppCompatActivity {

    private static RemoteCamera remoteCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_stream);

        remoteCamera = (RemoteCamera) getIntent().getSerializableExtra("camera_object");

        Toast.makeText(getApplicationContext(),
                "Connecting to "+remoteCamera.getAddress(),
                Toast.LENGTH_SHORT).show();

        WebView cameraStreamWebView = (WebView) findViewById(R.id.webView);

        cameraStreamWebView.loadUrl("http://"+remoteCamera.getAddress());
    }
}
