package de.smartdev.lastorderapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class DtlOffer extends AppCompatActivity implements OnMapReadyCallback {

    ArrayList<String> offer = new ArrayList<>();
    String restaurantName;
    String offer_Text;
    String offer_Adresse;
    String offer_Phone;
    double offer_Latitude;
    double offer_Longitude;
    LatLng offer_LatLng;
    TextView view_Name;
    TextView view_Text;
    TextView view_Adresse;
    TextView view_Phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dtl_offer);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.dtl_map);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        String reference = intent.getStringExtra("ReferenceID");
        Log.i("ReferenceID: ", reference);        //this the the ID of the clicked offer
        offer = getOffer(reference);

        restaurantName = offer.get(0);
        offer_Text = offer.get(1);
        offer_Adresse = offer.get(2);
        offer_Phone = offer.get(3);
        String temp_lat = offer.get(4);
        offer_Latitude = Double.parseDouble(temp_lat);
        String temp_long = offer.get(5);
        offer_Longitude = Double.parseDouble(temp_long);
        offer_LatLng = new LatLng(offer_Latitude, offer_Longitude);

        view_Name = (TextView) findViewById(R.id.dtl_text_offerName);
        view_Text = (TextView) findViewById(R.id.dtl_text_offerText);
        view_Adresse = (TextView) findViewById(R.id.dtl_text_offerAdresse);
        view_Phone = (TextView) findViewById(R.id.dtl_text_offerPhone);

        view_Name.setText(restaurantName);
        view_Text.setText(offer_Text);
        view_Adresse.setText(offer_Adresse);
        view_Phone.setText(offer_Phone);


    }

    protected ArrayList<String> getOffer(String referenceID) {
        ArrayList<String> tempOffer = new ArrayList<>();
        //TODO: change from mock to firebase getData(referenceID)
        //Mock-Start:
        tempOffer.add(0, "Restaurantname");
        tempOffer.add(1, "Rumpsteak\nmit Offenkartoffeln und Sour Cream.\nDazu cremiges Wirsing-Zucchini-Gemüse.");
        tempOffer.add(2, "Musterstraße 21");
        tempOffer.add(3, "017691438675");//Telefonnummer
        tempOffer.add(4, "48.1468345"); //Latitude
        tempOffer.add(5, "11.5794744");  //Longitude
        return tempOffer;
    }

    protected LatLng getOffer_LatLng(String referenceID) {
        offer = getOffer(referenceID);
        String temp_lat = offer.get(4);
        offer_Latitude = Double.parseDouble(temp_lat);
        String temp_long = offer.get(5);
        offer_Longitude = Double.parseDouble(temp_long);
        offer_LatLng = new LatLng(offer_Latitude, offer_Longitude);
        return offer_LatLng;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
        Intent intent=getIntent();
        String reference=intent.getStringExtra("ReferenceID");
        offer_LatLng=getOffer_LatLng(reference);
        googleMap.addMarker(new MarkerOptions().title("Angebot").position(offer_LatLng).snippet("Rumpsteak im Angebot"));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(offer_LatLng, 13));
    }
}
