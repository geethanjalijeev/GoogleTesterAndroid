package com.example.geethanjalijeevanatham.googletester;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import com.neno0o.ubersdk.Endpoints.Models.Products.Products;
import com.neno0o.ubersdk.Endpoints.Models.Requests.EstimateRequest.UberEstimateBody;
import com.neno0o.ubersdk.Endpoints.Models.Requests.EstimateRequest.UberEstimateRequest;
import com.neno0o.ubersdk.Endpoints.Models.Times.Times;
import com.neno0o.ubersdk.Uber;

import org.json.JSONObject;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    ArrayList<LatLng> MarkerPoints;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    private static final int REQUEST_CODE_AUTOCOMPLETE1 = 2;
    LatLng sydney = new LatLng(-34, 151);
    LatLng sydney1 = new LatLng(-34, 151);
    public static final String TAG = "GoogleTester";
    SupportMapFragment mapFragment;
    int flag = 0;
    TextView text1;
    TextView text2;
    ViewGroup.LayoutParams paramsMap;
    String param1,param2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        // Initializing
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        MarkerPoints = new ArrayList<>();

        text1 = (TextView) findViewById(R.id.textt1);
        text2 = (TextView) findViewById(R.id.textt2);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        paramsMap = mapFragment.getView().getLayoutParams();
        paramsMap.height = 1800;
        mapFragment.getView().setLayoutParams(paramsMap);
        mapFragment.getMapAsync(this);

       /* SessionConfiguration config = new SessionConfiguration.Builder()
                .setClientId("YOUR_CLIENT_ID") //This is necessary
                .setRedirectUri("YOUR_REDIRECT_URI") //This is necessary if you'll be using implicit grant
                .setEnvironment(Environment.SANDBOX) //Useful for testing your app in the sandbox environment
                .setScopes(Arrays.asList(Scope.PROFILE, Scope.RIDE_WIDGETS)) //Your scopes for authentication here
                .build();

//This is a convenience method and will set the default config to be used in other components without passing it directly.
        UberSdk.initialize(config); */



       /* Uber.getInstance().init("_IggVqi_2mhU_LoJWKJYJh6MCgmmb2XH",
                "CKsUW7QUFXCQ8TXAh0qreYzYl0P3U7IwOL_hJZGo",
                "Avg42mWBedhT6WcnEz-uInvKdkKWkPh-A8zBD1TD",
                "http://localhost");
        Uber.getInstance().getUberAPIService().getProducts(41.891620, -87.607291, new Callback<Products>() {
            @Override
            public void success(Products products, Response response) {
                Log.d("Product", products.getProducts().get(0).getProductId());
                Log.d("Products", String.valueOf(products.getProducts().size()));
                UberEstimateBody uberEstimateBody = new UberEstimateBody(products.getProducts().get(0).getProductId(),
                        41.891620,
                        -87.607291,
                        41.878886,
                        -87.635882);

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });



        Uber.getInstance().getUberAPIService().getTimeEstimates(41.891620, -87.607291, new Callback<Times>() {
            @Override
            public void success(Times times, Response response) {
                Log.d("Times", String.valueOf(times.getTimes().size()));
            }

            @Override
            public void failure(RetrofitError error) {

            }
        }); */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.androidmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, item.getTitle(), Toast.LENGTH_LONG).show();
        return super.onOptionsItemSelected(item);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

       // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        //Initialize Google Play Services
        if(flag==0){
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    buildGoogleApiClient();
                    mMap.setMyLocationEnabled(true);
                }
            }
            else {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else if(flag==1){
            if (MarkerPoints.size() > 1) {
                MarkerPoints.clear();
                mMap.clear();
            }

            MarkerPoints.add(sydney);
            MarkerOptions options = new MarkerOptions();
            options.position(sydney);
            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

            MarkerPoints.add(sydney1);
            MarkerOptions options1 = new MarkerOptions();
            options1.position(sydney1);
            options1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            mMap.addMarker(options);
            mMap.addMarker(options1);

            // Checks, whether start and end locations are captured
                //LatLng origin = MarkerPoints.get(0);
               // LatLng dest = MarkerPoints.get(1);


                // Getting URL to the Google Directions API
                String url = getUrl(param1, param2);
                //Log.d("onMapClick", url.toString());
                FetchUrl FetchUrl = new FetchUrl();

                // Start downloading json data from Google Directions API
                FetchUrl.execute(url);

              LatLng south;
              LatLng north;

              if(sydney.longitude > sydney1.longitude){
                  north = sydney;
                  south = sydney1;
              }
              else{
                  north = sydney1;
                  south = sydney;
              }
                //move map camera
            LatLngBounds b1 = new LatLngBounds(south, north);
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(b1, 10));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
            paramsMap = mapFragment.getView().getLayoutParams();
            paramsMap.height = 1130;
            mapFragment.getView().setLayoutParams(paramsMap);
            //mapFragment.getMapAsync(this);


        }


        // Setting onclick event listener for the map
      /*  mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {

                // Already two locations
                if (MarkerPoints.size() > 1) {
                    MarkerPoints.clear();
                    mMap.clear();
                }

                // Adding new item to the ArrayList
                MarkerPoints.add(point);

                // Creating MarkerOptions
                MarkerOptions options = new MarkerOptions();

                // Setting the position of the marker
                options.position(point);

                /**
                 * For the start location, the color of marker is GREEN and
                 * for the end location, the color of marker is RED.
                 */
               /* if (MarkerPoints.size() == 1) {
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                } else if (MarkerPoints.size() == 2) {
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                }


                // Add new marker to the Google Map Android API V2
                mMap.addMarker(options);

                // Checks, whether start and end locations are captured
                if (MarkerPoints.size() >= 2) {
                    LatLng origin = MarkerPoints.get(0);
                    LatLng dest = MarkerPoints.get(1);

                    // Getting URL to the Google Directions API
                    String url = getUrl(origin, dest);
                    Log.d("onMapClick", url.toString());
                    FetchUrl FetchUrl = new FetchUrl();

                    // Start downloading json data from Google Directions API
                    FetchUrl.execute(url);
                    //move map camera
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(origin));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
                }

            }
        }); */

    }

    public void startsearch(View view){
            openAutocompleteActivity();
    }

    public void stopsearch(View view){
        openAutocompleteActivity1();
    }

    public void startroute(View view){
        flag = 1;
        mapFragment.getMapAsync(this);
    }

    private void openAutocompleteActivity() {
        try {
            // The autocomplete activity requires Google Play Services to be available. The intent
            // builder checks this and throws an exception if it is not the case.
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .build(this);
            startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
        } catch (GooglePlayServicesRepairableException e) {
            // Indicates that Google Play Services is either not installed or not up to date. Prompt
            // the user to correct the issue.
            GoogleApiAvailability.getInstance().getErrorDialog(this, e.getConnectionStatusCode(),
                    0 /* requestCode */).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            // Indicates that Google Play Services is not available and the problem is not easily
            // resolvable.
            String message = "Google Play Services is not available: " +
                    GoogleApiAvailability.getInstance().getErrorString(e.errorCode);

            /*Log.e(TAG, message);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show(); */
        }
    }

    private void openAutocompleteActivity1() {
        try {
            // The autocomplete activity requires Google Play Services to be available. The intent
            // builder checks this and throws an exception if it is not the case.
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .build(this);
            startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE1);
        } catch (GooglePlayServicesRepairableException e) {
            // Indicates that Google Play Services is either not installed or not up to date. Prompt
            // the user to correct the issue.
            GoogleApiAvailability.getInstance().getErrorDialog(this, e.getConnectionStatusCode(),
                    0 /* requestCode */).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            // Indicates that Google Play Services is not available and the problem is not easily
            // resolvable.
            String message = "Google Play Services is not available: " +
                    GoogleApiAvailability.getInstance().getErrorString(e.errorCode);

            /*Log.e(TAG, message);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show(); */
        }
    }

    /**
     * Called after the autocomplete activity has finished to return its result.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that the result was from the autocomplete widget.
        if (requestCode == REQUEST_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                // Get the user's selected place from the Intent.
                Place place = PlaceAutocomplete.getPlace(this, data);
                //Log.i(TAG, "Place Selected: " + place.getName()+ "  " + place.getLatLng());
                //Log.d(TAG, "Error .... ");
                // Format the place's details and display them in the TextView.
                //mPlaceDetailsText.setText(formatPlaceDetails(getResources(), place.getName(),
                //place.getId(), place.getAddress(), place.getPhoneNumber(),
                //place.getWebsiteUri()));

                sydney = place.getLatLng();
                text1.setText(place.getName());
                param1 = place.getName().toString()+","+place.getAddress().toString();
                //mapFragment.getMapAsync(this);


             /*  PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient, "ChIJGVshXgp67ocR6Els7jrt2Nc");
                placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(PlaceBuffer places) {
                        if(places.getStatus().isSuccess()){
                            final Place p1 = places.get(0);
                            LatLng l1 = p1.getLatLng();
                            double lat = l1.latitude;
                            double lng = l1.longitude;
                            Log.d(TAG,"lat = "+lat+" lng = "+lng);
                        }
                        else{
                            Log.d(TAG,"Error .... ");
                        }
                        Log.d(TAG,"Error .... ");
                    }
                }); */
                /*Place p1 = buffer.get(0);
                LatLng l1 = p1.getLatLng();
                double lat = l1.latitude;
                double lng = l1.longitude;
                Log.d(TAG,"lat = "+lat+" lng = "+lng); */


                // Display attributions if required.
                CharSequence attributions = place.getAttributions();
                if (!TextUtils.isEmpty(attributions)) {
                    //mPlaceAttribution.setText(Html.fromHtml(attributions.toString()));
                } else {
                    //mPlaceAttribution.setText("");
                }
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.e(TAG, "Error: Status = " + status.toString());
            } else if (resultCode == RESULT_CANCELED) {
                // Indicates that the activity closed before a selection was made. For example if
                // the user pressed the back button.
            }
        }
        else if (requestCode == REQUEST_CODE_AUTOCOMPLETE1) {
            if (resultCode == RESULT_OK) {
                // Get the user's selected place from the Intent.
                Place place1 = PlaceAutocomplete.getPlace(this, data);
                //Log.i(TAG, "Place Selected: " + place.getName()+ "  " + place.getLatLng());
                //Log.d(TAG, "Error .... ");
                // Format the place's details and display them in the TextView.
                //mPlaceDetailsText.setText(formatPlaceDetails(getResources(), place.getName(),
                //place.getId(), place.getAddress(), place.getPhoneNumber(),
                //place.getWebsiteUri()));

                sydney1 = place1.getLatLng();
                text2.setText(place1.getName());
                param2 = place1.getName().toString()+","+place1.getAddress().toString();
                //mapFragment.getMapAsync(this);


             /*  PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient, "ChIJGVshXgp67ocR6Els7jrt2Nc");
                placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(PlaceBuffer places) {
                        if(places.getStatus().isSuccess()){
                            final Place p1 = places.get(0);
                            LatLng l1 = p1.getLatLng();
                            double lat = l1.latitude;
                            double lng = l1.longitude;
                            Log.d(TAG,"lat = "+lat+" lng = "+lng);
                        }
                        else{
                            Log.d(TAG,"Error .... ");
                        }
                        Log.d(TAG,"Error .... ");
                    }
                }); */
                /*Place p1 = buffer.get(0);
                LatLng l1 = p1.getLatLng();
                double lat = l1.latitude;
                double lng = l1.longitude;
                Log.d(TAG,"lat = "+lat+" lng = "+lng); */


                // Display attributions if required.
                CharSequence attributions = place1.getAttributions();
                if (!TextUtils.isEmpty(attributions)) {
                    //mPlaceAttribution.setText(Html.fromHtml(attributions.toString()));
                } else {
                    //mPlaceAttribution.setText("");
                }
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status1 = PlaceAutocomplete.getStatus(this, data);
                Log.e(TAG, "Error: Status = " + status1.toString());
            } else if (resultCode == RESULT_CANCELED) {
                // Indicates that the activity closed before a selection was made. For example if
                // the user pressed the back button.
            }
        }
    }

    private String getUrl(String name, String addr) {

        // Origin of route
        //String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_origin = "origin=" + param1;
        str_origin = str_origin.replaceAll(" ","+");

        // Destination of route
        //String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String str_dest = "destination=" + param2;
        str_dest = str_dest.replaceAll(" ","+");


        // Sensor enabled
        String sensor = "sensor=false";

        String mode = "mode=transit";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;
        Log.d(TAG,parameters.toString());
        //parameters="origin=133+Beedle+Drive,Ames,IA+50014,USA&destination=Kildee+Hall,Ames,IA,United+States&mode=transit&key=AIzaSyAKVr5NAFpPbpo6sci5J7bqTk0CSiv2KFU";
        //Log.d(TAG,parameters.toString());
        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }

    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            Log.d("downloadUrl", data.toString());
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches data from url passed
    private class FetchUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }

    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                Log.d("ParserTask",jsonData[0].toString());
                DataParser parser = new DataParser();
                Log.d("ParserTask", parser.toString());

                // Starts parsing data
                routes = parser.parse(jObject);
                Log.d("ParserTask","Executing routes");
                Log.d("ParserTask",routes.toString());

            } catch (Exception e) {
                Log.d("ParserTask",e.toString());
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.RED);

                Log.d("onPostExecute","onPostExecute lineoptions decoded");

            }

            // Drawing polyline in the Google Map for the i-th route
            if(lineOptions != null) {
                mMap.addPolyline(lineOptions);
            }
            else {
                Log.d("onPostExecute","without Polylines drawn");
            }
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }
}