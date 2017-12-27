package net.smile.bantaengtour;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import net.smile.bantaengtour.entity.ObjekWisata;
import net.smile.bantaengtour.service.DirectionsJSONParser;
import net.smile.bantaengtour.utility.StoredData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PetaWisataActivity extends AppCompatActivity implements OnMapReadyCallback, TextToSpeech.OnInitListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient mGoogleApiClient;
    Location mLocation;
    GoogleMap maps;
    Marker marks;
    List<ObjekWisata> listDataPopular;
    List<ObjekWisata> listSingleDataPopular;
    ObjekWisata objekWisata;
    SupportMapFragment fm;
    private TextToSpeech tts;
    Polyline line;
    TextView tvDistanceDuration;
    ProgressDialog mProgressDialog;
    final Context context = this;
    LatLng latLng;
    LatLng tujuan;
    double myLat = 0;
    double myLng = 0;

    double intentLat;
    double intentLng;

    String distance = "";
    String duration = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peta_wisata);

        listDataPopular = new ArrayList<ObjekWisata>();
        listSingleDataPopular = new ArrayList<ObjekWisata>();
        tvDistanceDuration = (TextView) findViewById(R.id.tvDistance);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API).build();
        }

        tts = new TextToSpeech(this, this);


        if(getIntent().getStringExtra("latitude")!=null){
            double intentLat = Double.valueOf(getIntent().getStringExtra("latitude"));
            double intentLng = Double.valueOf(getIntent().getStringExtra("longitude"));
            initMap();
        }else{
            loadDataPopular(0);
            initMap();
        }



    }

    public void initMap() {
        fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        fm.getMapAsync(this);

        LocationManager mlocManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        boolean enabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!enabled) {
            showDialogGPS();
        }
    }

    @Override
    public void onMapReady(final GoogleMap map) {
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.setTrafficEnabled(true);
        map.setIndoorEnabled(true);
        map.setBuildingsEnabled(true);
        map.setTrafficEnabled(true);
        map.getUiSettings().setMapToolbarEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);

        for (ObjekWisata ob : listDataPopular) {
            LatLng latLng = new LatLng(Double.valueOf(ob.getLatitude()), Double.valueOf(ob.getLongitude()));
            map.addMarker(new MarkerOptions().position(latLng).title(ob.getNamaWisata()));
        }

        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(-5.558570, 120.193047)));

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);

        map.animateCamera(CameraUpdateFactory.zoomTo(9), 500, null);

//        map.animateCamera(CameraUpdateFactory.zoomTo(14),200,null);

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if (line != null) {
                    line.remove();
                }
                LatLng position = marker.getPosition();

                final double lat = position.latitude;
                final double lng = position.longitude;


                getDataLatLng(0, lat, lng);

            }
        });
        this.maps = map;
    }

    private void showDalogInfo(final ObjekWisata objekWisata) {
        final Dialog dialog = new Dialog(context);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.select);
        ImageView ivDialog = (ImageView) dialog.findViewById(R.id.ivDialogs);
        Button btn_rute = (Button) dialog.findViewById(R.id.btn_rute);
        Button btn_detail = (Button) dialog.findViewById(R.id.btn_detail);
        dialog.setTitle(objekWisata.getNamaWisata());
        Glide.with(getBaseContext()).load(objekWisata.getCover()).into(ivDialog);


        btn_rute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (maps.isMyLocationEnabled() || maps != null) {
                    double targetLat = Double.valueOf(objekWisata.getLatitude());
                    double targetLng = Double.valueOf(objekWisata.getLongitude());

//                    double myLat = maps.getMyLocation().getLatitude();
//                    double myLng = maps.getMyLocation().getLongitude();

                    latLng = new LatLng(myLat, myLng);
                    tujuan = new LatLng(targetLat, targetLng);
                    String url = getDirectionsUrl(latLng, tujuan);

                    // Start downloading json data from Google Directions API
                    DownloadTask downloadTask = new DownloadTask();
                    downloadTask.execute(url);
//                marker.hideInfoWindow();
                    dialog.dismiss();

                } else {
                    Toast.makeText(PetaWisataActivity.this, "silahkan aktifkan fitur my location anda terlebih dahulu !!", Toast.LENGTH_SHORT).show();
                }


            }
        });

        btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDetailObjWisata(0,Double.valueOf(objekWisata.getLatitude()),Double.valueOf(objekWisata.getLongitude()));
            }
        });

        dialog.show();
    }

    private void showDialogGPS() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Enable GPS");
        builder.setMessage("Fitur Lokasi anda belum aktif harap mengaktifkan !");
        builder.setInverseBackgroundForced(true);
        builder.setPositiveButton("Aktifkan", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });
        builder.setNegativeButton("Abaikan", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void loadDataPopular(final int id) {

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {

            @Override
            protected Void doInBackground(Integer... params) {

                OkHttpClient client = new OkHttpClient();
                Request req = new Request.Builder()
                        .url(StoredData.IP + "/bantaengtour/getPopular.php")
                        .build();
                try {

                    Response response = client.newCall(req).execute();
                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        ObjekWisata data = new ObjekWisata(
                                object.getInt("id"), object.getString("nama_wisata"),
                                object.getString("detail"), object.getString("gambar_cover"),
                                object.getString("latitude"), object.getString("longitude"),
                                object.getString("alamat"));

                        listDataPopular.add(data);
                    }

                } catch (IOException e) {

                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("End of Content");
                }
                return null;

            }

            @Override
            protected void onPostExecute(Void aVoid) {
                initMap();

            }
        };

        task.execute(id);

    }

    public void getDataLatLng(final int id, final double lat, final double lng) {


        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {

            @Override
            protected Void doInBackground(Integer... params) {

                OkHttpClient client = new OkHttpClient();
                Request req = new Request.Builder()
                        .url(StoredData.IP + "/bantaengtour/getPopularByLatLng.php?lat=" + lat + "&lng=" + lng)
                        .build();
                try {
                    Response response = client.newCall(req).execute();
                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        ObjekWisata data = new ObjekWisata(
                                object.getInt("id"), object.getString("nama_wisata"),
                                object.getString("detail"), object.getString("gambar_cover"),
                                object.getString("latitude"), object.getString("longitude"),
                                object.getString("alamat"));

                        listSingleDataPopular.add(data);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("End of Content");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                objekWisata = new ObjekWisata();
                objekWisata = listSingleDataPopular.get(0);
                showDalogInfo(objekWisata);
                listSingleDataPopular.clear();
            }
        };

        task.execute(id);

    }

    public void loadDetailObjWisata(final int id, final double lat, final double lng) {


        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {

            @Override
            protected Void doInBackground(Integer... params) {

                OkHttpClient client = new OkHttpClient();
                Request req = new Request.Builder()
                        .url(StoredData.IP + "/bantaengtour/getPopularByLatLng.php?lat=" + lat + "&lng=" + lng)
                        .build();
                try {

                    Response response = client.newCall(req).execute();
                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        ObjekWisata data = new ObjekWisata(
                                object.getInt("id"), object.getString("nama_wisata"),
                                object.getString("detail"), object.getString("gambar_cover"),
                                object.getString("latitude"), object.getString("longitude"),
                                object.getString("alamat"));

                        listSingleDataPopular.add(data);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("End of Content");
                }
                return null;

            }

            @Override
            protected void onPostExecute(Void aVoid) {
                objekWisata = new ObjekWisata();
                objekWisata = listSingleDataPopular.get(0);
                listSingleDataPopular.clear();
                Intent i = new Intent(getBaseContext(), PopularDetailActivity.class);
                i.putExtra("gambar",objekWisata.getCover());
                i.putExtra("detail",objekWisata.getInfoDetail());
                i.putExtra("id",objekWisata.getId());
                i.putExtra("nama", objekWisata.getNamaWisata());
                i.putExtra("latitude",objekWisata.getLatitude());
                i.putExtra("longitude",objekWisata.getLongitude());
                i.putExtra("alamat",objekWisata.getAlamat());
                startActivity(i);
                

            }
        };

        task.execute(id);

    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        return url;
    }

    public void onDestroy() {
        // Don't forget to shutdown!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }


    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onInit(int status) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if(mLocation != null){
                myLat = mLocation.getLatitude();
                myLng = mLocation.getLongitude();
                System.out.println("lat :"+myLat+"\nlng :"+myLng);
            }
            return;
        }
//        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//        if(mLocation != null){
//            myLat = mLocation.getLatitude();
//            myLng = mLocation.getLongitude();
//            System.out.println("lat :"+myLat+"\nlng :"+myLng);
//        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    /** A class to download data from Google Directions URL */
    private class DownloadTask extends AsyncTask<String, Void, String>{

        // Downloading data in non-ui thread
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(PetaWisataActivity.this);
            mProgressDialog.setTitle("Download Data, Mohon Tunggu!");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }

            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
            mProgressDialog.dismiss();
        }
    }

    /** A class to parse the Google Directions in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {


            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){



                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
//            distance = "";
//            duration = "";

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    if (j==0) {
                        distance = (String)point.get("distance");
                        continue;
                    } else if (j==1) {
                        duration=(String)point.get("duration");
                        continue;
                    }

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(7);
                lineOptions.color(Color.RED);

            }

            try{
                tvDistanceDuration.setText("Distance : "+distance+", Duration : "+duration);
                // Drawing polyline in the Google Map for the i-th route
                line = maps.addPolyline(lineOptions);
                speakOut();
            }catch(NullPointerException e){
                e.printStackTrace();
                Toast.makeText(getBaseContext(), "Signal GPS Kurang Akurat !", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void drawPolyLine(){

    }

    private void speakOut() {
        String text = tvDistanceDuration.getText().toString();
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

}
