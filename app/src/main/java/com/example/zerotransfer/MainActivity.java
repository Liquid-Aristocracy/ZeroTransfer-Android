package com.example.zerotransfer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton sendfile = (FloatingActionButton) findViewById(R.id.SendButton);
        sendfile.bringToFront();
        sendfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FileAccessorActivity.class);
                startActivityForResult(intent, 2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2 && resultCode == RESULT_OK){
            Bundle bundle = data.getExtras();
            String path = null;
            if (bundle != null) {
                path = bundle.getString("path");
                Toast.makeText(this, path, Toast.LENGTH_LONG).show();
            }
        }
    }

}