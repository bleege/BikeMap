package com.bradleege;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.PathOverlay;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
/*
        mapView.setBuiltInZoomControls(true);
        mapView.setClickable(true);
*/
        mapView.setMultiTouchControls(true);
        mapView.getController().setZoom(15);
        mapView.getController().setCenter(new GeoPoint(43.05277119900874, -89.42244529724121));


        // Load The GeoJSON For Display On Map
        String json = loadJsonData();
        List<GeoPoint> points = convertGeoJSONToGeoPoints(json);

        // Build and Display the Path on the map
        PathOverlay myPath = new PathOverlay(Color.RED, this);
        for (GeoPoint gp : points)
        {
            myPath.addPoint(gp);
        }
        mapView.getOverlays().add(myPath);
    }

    private String loadJsonData()
    {
        StringBuilder builder = new StringBuilder();

        try
        {
            InputStream is = getResources().getAssets().open("map.geojson");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null)
            {
                builder.append(line);
            }
        }
        catch (IOException e)
        {
            Log.e(BikeMap.class.getName(), "Error Reading JSON Data", e);
        }
        return builder.toString();
    }

    private List<GeoPoint> convertGeoJSONToGeoPoints(String geoJson)
    {
        ArrayList<GeoPoint> points = new ArrayList<GeoPoint>();

        try
        {
            JSONObject object = new JSONObject(geoJson);
            JSONArray features = object.getJSONArray("features");
            JSONArray coordinates = features.getJSONObject(0).getJSONObject("geometry").getJSONArray("coordinates");

            for (int lc = 0; lc < coordinates.length(); lc++)
            {
                JSONArray c = coordinates.getJSONArray(lc);
                GeoPoint gp = new GeoPoint(c.getDouble(1), c.getDouble(0));
                points.add(gp);
            }

        }
        catch (JSONException e)
        {
            Log.e(BikeMap.class.getName(), "Error Converting GeoJSON To GeoPoints", e);
        }

        return points;
    }

}
