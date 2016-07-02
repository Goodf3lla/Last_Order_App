package de.smartdev.lastorderapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Splash extends AppCompatActivity {








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        int RequestLocationId = 0;
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this,"1",Toast.LENGTH_LONG);
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, RequestLocationId);
        }
        else{Toast.makeText(this,"2",Toast.LENGTH_LONG);}
        Button btn=(Button) findViewById(R.id.btn_splash);
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.ACCESS_FINE_LOCATION))
        {
           Toast.makeText(this, "test",Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, RequestLocationId);
        }




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent i;
                i = new Intent(Splash.this, MainActivity.class);
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(i);
                    }
                });
            }
        });








    }

}
