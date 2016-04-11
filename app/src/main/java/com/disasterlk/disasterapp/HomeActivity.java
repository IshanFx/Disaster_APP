package com.disasterlk.disasterapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.disasterlk.disasterapp.Adapter.DisasterAdapter;
import com.disasterlk.disasterapp.DAO.Alert;
import com.disasterlk.disasterapp.DAO.Disaster;
import com.disasterlk.disasterapp.Handler.DisasterAPI;
import com.disasterlk.disasterapp.Handler.NetworkHandler;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;



public class HomeActivity extends AppCompatActivity {
    DisasterAdapter disasterAdapter;
    ListView disasterList;
    List<Alert> list;
    private SwipeRefreshLayout swipeContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                new AlertAsync().execute();
            }
        });

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //realMAdapter.removeData();
                new AlertAsync().execute();
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        list = new ArrayList<>();
        Alert alerts  = new Alert();
        alerts.setArea_name("Area");
        alerts.setAMessage("sdad");
        alerts.setADisasterType("asd");
        list.add(alerts);
        disasterAdapter = new DisasterAdapter(this, 1, list);
        disasterList = (ListView) findViewById(android.R.id.list);
        disasterList.setAdapter(disasterAdapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
            new test().execute();
        }
        if(id == R.id.action_suggestion){
            Intent intent = new Intent(this,SuggestionActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    public class AlertAsync extends AsyncTask<Void,Alert,Void> {
        ArrayAdapter<Alert> adapter;
        @Override
        protected void onPreExecute() {

            adapter = (DisasterAdapter) disasterList.getAdapter();
            adapter.clear();
            Log.d("Good", "pre");
        }

        @Override
        protected Void doInBackground(Void... params) {
            try{
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest request = new StringRequest(Request.Method.GET, "http://192.168.42.200:8085/DisasterService.svc/alerts/findUserAlerts", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                try {
                                    JSONArray alertArray = new JSONArray(response);
                                    for(int x=0;x<alertArray.length();x++){
                                        JSONObject crimejsonObject = alertArray.getJSONObject(x);
                                        Alert alert = new Alert();
                                        alert.setArea_name(crimejsonObject.getString("area"));
                                        alert.setAMessage(crimejsonObject.getString("message"));
                                        alert.setARiskLevel(crimejsonObject.getString("risklevel"));
                                        alert.setAlocation(crimejsonObject.getString("safelocation"));
                                        alert.setADisasterType(crimejsonObject.getString("disaster"));
                                        alert.setADate(crimejsonObject.getString("date"));
                                        publishProgress(alert);
                                    }


                                } catch (Exception e) {
                                    Log.d("Dip", e.toString());
                                }

                            } catch (Exception ex) {
                                Log.d("Res", ex.toString());
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 5, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    queue.add(request);

             }
        catch (Exception ex){
            Log.d("Res", ex.toString());
        }


            return null;
        }


        @Override
        protected void onProgressUpdate(Alert... values) {
            adapter.add(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            swipeContainer.setRefreshing(false);
            Log.d("Good", "out");



        }
    }

    public class test extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                         StringRequest request = new StringRequest(Request.Method.GET, "http://192.168.42.200:8085/DisasterService.svc/alerts/findUserAlerts", new Response.Listener<String>() {
                             @Override
                             public void onResponse(String response) {
                                 try{
                                     JSONArray resposeJSON = new JSONArray(response);
                                     Log.d("Check",resposeJSON.toString());
                                 }
                                 catch(Exception ex){

                                 }
                             }
                         }, new Response.ErrorListener() {
                             @Override
                             public void onErrorResponse(VolleyError error) {

                                 Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                             }
                         });
                         request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                         queue.add(request);
           /* JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://192.168.42.200:8085/DisasterService.svc/alerts/findUserAlerts",null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray array = response.getJSONArray("opencase");
                                for (int i = 0; i < array.length(); i++) {

                                    JSONObject jsonObject = array.getJSONObject(i);
                                    //   publishProgress(jsonObject.getString("id"));

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle error
                        }
                    });

            queue.add(jsonObjectRequest);*/
            return null;
        }
    }


}
