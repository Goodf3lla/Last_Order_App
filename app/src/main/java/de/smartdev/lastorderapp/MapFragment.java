package de.smartdev.lastorderapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.client.Firebase;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback,LocationListener{

    public MapFragment() {
        // Required empty public constructor
    }

    private OnFragmentInteractionListener mListener;
    private static final String ARG_PARAM1 = "param1";

    private MapView mMapView;
    private Bundle mBundle;
    private HashMap<String,String> marker2keysHash=new HashMap<>();
    private ArrayList<Marker> markers=new ArrayList<>();

    public static MapFragment newInstance(int param1) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_map, container, false);
        MapsInitializer.initialize(getActivity());





        mMapView = (MapView) inflatedView.findViewById(R.id.map);
        Button btn_go2Profil = (Button) inflatedView.findViewById(R.id.map_btn_go2Profil);
        Button btn_fltr_perm = (Button) inflatedView.findViewById(R.id.map_btn_fltr_perm);
        Button btn_fltr_temp = (Button) inflatedView.findViewById(R.id.map_btn_fltr_temp);
        Button bnt_fltr_nrby = (Button) inflatedView.findViewById(R.id.map_btn_fltr_nrby);

        mMapView.onCreate(mBundle);
        mMapView.getMapAsync(this);
        bnt_fltr_nrby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: filter the markers for nearby offers
            }
        });
        btn_fltr_temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: filter the markers for temporary offers
            }
        });
        btn_fltr_perm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: filter the markers for permanent offers
            }
        });
        btn_go2Profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: fix bug in navigation drawer (section 1 still highlighted after change of fragment)
                Fragment fragment = FourthFragment.newInstance(4);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            }
        });
        return inflatedView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = savedInstanceState;
    }

    @Override
    public void onMapReady(final GoogleMap map) {
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission(1);
        }
        map.setMyLocationEnabled(true);
        //TODO:delete the static code &
        ArrayList<LatLng> gps_data = new ArrayList<>();
        ArrayList<String> name=new ArrayList<>();
        ArrayList<String> key=new ArrayList<>();
        ArrayList<String> text=new ArrayList<>();
        LatLng MELBOURNE = new LatLng(48.1480514, 11.5711917);
        gps_data.add(MELBOURNE);
        LatLng SYDNEY = new LatLng(48.1468345, 11.5794744);
        gps_data.add(SYDNEY);
        name.add("Bar");
        name.add("Restaurant");
        text.add("Happy Hour von 17-18 Uhr.");
        text.add("Alle Fleischgerichte zum halben Preis.");
        key.add("Melbourne");
        key.add("Leopoldstr");
        //TODO: change null to data from firebase
        //addMarkers2Map(map, null, null, null, null);
        addMarkers2Map(map, gps_data, name, text, key);
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                String clickedMarkerID=marker.getId();
                String value=marker2keysHash.get(clickedMarkerID);
                final Intent i = new Intent(getContext(), DtlOffer.class);
                i.putExtra("ReferenceID",value);
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(i);}
                });
            }
        });
    }

    //-!The Parameter String[] "key" is for referencing a unique ID!-
    private void addMarkers2Map(GoogleMap map,ArrayList<LatLng> gps_data,ArrayList<String> name,ArrayList<String> text,ArrayList<String> key) {
        map.clear();
        if (text==null ||gps_data==null || name==null){
            Log.w("addMarkers2Map: ","Placing Markers failed, missformed Marker detected.");
            return;
        }
        else{
            for (int i=0;i<text.size();i++){
               markers.add(i, map.addMarker(new MarkerOptions()
                       .position(gps_data.get(i))
                       .title(name.get(i))
                       .snippet(text.get(i))));
                //Log.i("markerID: "+markers.get(i).getId(),"matches key: "+key.get(i));
                marker2keysHash.put(markers.get(i).getId(),key.get(i));
            }
        }
    }

    public void requestLocationPermission(int requestCode) {
        MainActivity mainy=new MainActivity();
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Display a dialog with rationale.
            PermissionUtils.RationaleDialog
                    .newInstance(requestCode, false).show(
                    getActivity().getSupportFragmentManager(), "dialog");
        } else {
            // Location permission has not been granted yet, request it.
            PermissionUtils.requestPermission(mainy, requestCode,
                    Manifest.permission.ACCESS_FINE_LOCATION, false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng myPosition=new LatLng(location.getLatitude(),location.getLongitude());

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}