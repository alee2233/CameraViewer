package com.example.cameraviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<RemoteCamera> cameraArrayList;
    private CamViewDbHelper mainDbHelper;
    private ArrayList<String> cameraNameAndAddressList;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainDbHelper = new CamViewDbHelper(this);

        setCameraLists();

        final ListView camListView = (ListView) findViewById(R.id.cam_list_view);
        arrayAdapter = new ArrayAdapter<String>(this,R.layout.list_item,cameraNameAndAddressList);
        camListView.setAdapter(arrayAdapter);

        camListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                PopupMenu popup = new PopupMenu(MainActivity.this, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.listview_menu, popup.getMenu());
                popup.show();

                final int SELECTED_LIST_ITEM = i;

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.action_connect) {

                            // Pass selected RemoteCamera object to CameraStreamActivity
                            Intent cameraStreamActivityIntent = new Intent(MainActivity.this, CameraStreamActivity.class);
                            cameraStreamActivityIntent.putExtra("camera_object",cameraArrayList.get(SELECTED_LIST_ITEM));
                            startActivity(cameraStreamActivityIntent);

                            return true;
                        }
                        if (menuItem.getItemId() == R.id.action_delete) {
                            Toast.makeText(getApplicationContext(),
                                    "Deleting "+cameraArrayList.get(SELECTED_LIST_ITEM).getName(),
                                    Toast.LENGTH_SHORT).show();

                            mainDbHelper.deleteCamera(cameraArrayList.get(SELECTED_LIST_ITEM));

                            setCameraLists();

                            //arrayAdapter.notifyDataSetChanged();
                            arrayAdapter = new ArrayAdapter<String>(MainActivity.this,R.layout.list_item,cameraNameAndAddressList);
                            camListView.setAdapter(arrayAdapter);

                            return true;
                        }
                        return false;
                    }
                });
            }
        });

        // Floating Action should be to add cameras
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddCameraActivity.class);
                startActivity(intent);

            }
        });
    }

    public void setCameraLists() {
        cameraArrayList = mainDbHelper.readAllCameras();

        String cameraNameAndAddress;
        cameraNameAndAddressList = new ArrayList<String>();
        for (RemoteCamera rc : cameraArrayList) {
            cameraNameAndAddress = rc.getName()+"\n"+rc.getAddress();
            cameraNameAndAddressList.add(cameraNameAndAddress);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
