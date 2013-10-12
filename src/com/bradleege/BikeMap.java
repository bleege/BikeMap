package com.bradleege;

import android.app.Activity;
import android.os.Bundle;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

public class BikeMap extends Activity
{
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Interact With the MapView
        MapView mapView = (MapView)findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        mapView.setClickable(true);
        mapView.setMultiTouchControls(true);
        mapView.getController().setZoom(5);
        mapView.getController().setCenter(new GeoPoint(44.123, -89.456));
    }
}
