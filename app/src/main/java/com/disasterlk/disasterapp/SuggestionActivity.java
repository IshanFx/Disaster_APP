package com.disasterlk.disasterapp;

import android.content.Context;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.disasterlk.disasterapp.DAO.Suggestion;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class SuggestionActivity extends AppCompatActivity  implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private LocationManager locationManager;
    static Location mLastLocation;
    static LocationRequest mLocationRequest;
    public static GoogleApiClient mGoogleApiClient;
    public boolean isconnected = false;
    boolean startRealTimeTrack;
    Button btnSuggest;
    TextView txtLatitude,txtLongitude;
    RadioButton btnCurrentLocation;
    Spinner spinDisasterType,spinRiskLevel,spinArea;
    Suggestion suggest = new Suggestion();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disaster_suggest);
        txtLatitude = (TextView) findViewById(R.id.txtLatitude);
        txtLongitude = (TextView) findViewById(R.id.txtLongitude);
        btnCurrentLocation = (RadioButton) findViewById(R.id.rbtnCurrent);
        locationManager =
                (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        buildGoogleApiClient();
        createLocationRequest();
        btnSuggest = (Button) findViewById(R.id.btnSSubmit);
        txtLatitude = (TextView) findViewById(R.id.txtLatitude);
        txtLongitude = (TextView) findViewById(R.id.txtLongitude);
        spinArea = (Spinner) findViewById(R.id.spinnerarea);
        spinDisasterType = (Spinner) findViewById(R.id.spinnerdisaster);
        spinRiskLevel = (Spinner) findViewById(R.id.spinnerrisklevel);

        btnCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtLatitude.setText(String.valueOf(mLastLocation.getLatitude()));
                txtLongitude.setText(String.valueOf(mLastLocation.getLongitude()));
            }
        });
        btnSuggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SuggestAsync().execute();
            }
        });
    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
        Log.d("LocCheck", "API");
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        Log.d("LocCheck", "API2");


    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d("LocCheck", "API3");
        startLocationUpdates();
        this.isconnected = true;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        updateLocation();
    }

    private void updateLocation() {
        Log.d("OwnerInsert", "Start Track 0");
        if (startRealTimeTrack) {
            //callAsynchronousTask();
            Log.d("OwnerInsert", "Start Track");
           // LastLocationInsert loc = new LastLocationInsert();
           // loc.execute();
           /* if (startCycle == 1) {
                smsManage.sendSMS("Latitude:" + String.valueOf(mLastLocation.getLatitude()) + "Longitude:" + String.valueOf(mLastLocation.getLongitude()));
                startCycle += 2;
               Log.d("SMSLogin", String.valueOf(startCycle));
            }*/
        }
    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
        }

    }

    public class SuggestAsync extends AsyncTask<Void,Void,Void>{



        @Override
        protected void onPreExecute() {
            /*suggest.setSLatitude(txtLatitude.getText().toString());
            suggest.setSLongitude(txtLongitude.getText().toString());
            suggest.setSArea(spinArea.getSelectedItem().toString());
            suggest.setSDisasterType(spinDisasterType.getSelectedItem().toString());
            suggest.setSUser("1");*/

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            Map<String, String> parameters = new HashMap<String, String>();
            parameters.put("Al_id", "qwe");
            parameters.put("Area_id", "asd");
            parameters.put("Disaster_id", "asd");
            parameters.put("ALmessage", "asd");
            parameters.put("ALriskLevel", "asd");
            parameters.put("ALsafeLocation", "asd");

             RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                         /*StringRequest request = new StringRequest(Request.Method.POST,"http://192.168.42.200:8085/DisasterService.svc/alerts/save", new Response.Listener<String>() {
                             @Override
                             public void onResponse(String response) {
                                 try{
                                    *//* JSONObject resposeJSON = new JSONObject(response);
                                     if(resposeJSON.names().get(0).equals("caseid")){

                                     }*//*
                                     Log.d("error",response.toString());
                                 }
                                 catch(Exception ex){
                                     Log.d("error",ex.getMessage().toString());
                                 }
                             }
                         }, new Response.ErrorListener() {
                             @Override
                             public void onErrorResponse(VolleyError error) {
                                 Log.d("error",error.getMessage().toString());
                                 Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                             }
                         }) {
                             @Override
                             protected Map<String, String> getParams() throws AuthFailureError {
                                 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                                 Map<String, String> parameters = new HashMap<String, String>();
                                 parameters.put("Al_id", "qwe");
                                 parameters.put("Area_id", "asd");
                                 parameters.put("Disaster_id", "asd");
                                 parameters.put("ALmessage", "asd");
                                 parameters.put("ALriskLevel", "asd");
                                 parameters.put("ALsafeLocation", "asd");
                                 return parameters;
                             }

                             @Override
                             public Map<String, String> getHeaders() throws AuthFailureError {
                                 HashMap<String, String> headers = new HashMap<String, String>();
                                 headers.put("Content-Type", "application/json; charset=utf-8");
                                 return headers;
                             }
                         };
                         request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                         queue.add(request);*/
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    "http://192.168.42.200:8085/DisasterService.svc/alerts/save", new JSONObject(parameters),
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("error", response.toString());

                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("error", error.toString());

                }
            }) {

                /**
                 * Passing some request headers
                 * */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }



            };
            jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(jsonObjReq);
             return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
