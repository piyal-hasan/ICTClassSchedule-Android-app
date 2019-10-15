package com.example.piyal.classrutine.otherActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.piyal.classrutine.R;
import com.example.piyal.classrutine.batabase.FlagClass;
import com.example.piyal.classrutine.batabase.Keystore;
import com.example.piyal.classrutine.batabase.RutineDB;
import com.example.piyal.classrutine.batabase.model.Db_Url;

import com.example.piyal.classrutine.batabase.model.RutineModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Home extends AppCompatActivity {
    ProgressBar progress;
    RutineModel rutinemodel;
    String[] check;
    FlagClass flag;
    Keystore key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        flag=new FlagClass();
        key= Keystore.getInstance(this);
        dataUpdateCheck();
        findViewById(R.id.todayrutinebtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),OnlineRutineViewActivity.class));
            }
        });
        findViewById(R.id.semsesterbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

            }
        });
        findViewById(R.id.allrutinebtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.refreshmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.register:{
                Toast.makeText(getApplicationContext(),"singout",Toast.LENGTH_SHORT).show();
                key.clear();
                startActivity(new Intent(getBaseContext(),RegisterPanel.class));
                return true;
            }
            case R.id.aboutapp:{
                Toast.makeText(getApplicationContext(),"app",Toast.LENGTH_SHORT).show();
                return true;
            }
            case R.id.aboutme:{
                Toast.makeText(getApplicationContext(),"aboutme",Toast.LENGTH_SHORT).show();
                return true;
            }
            default:{
                return super.onOptionsItemSelected(item);
            }
        }

    }
    public void keystore(String upadetkey, String key){
        Keystore keystore=Keystore.getInstance(getApplicationContext());
        keystore.put(keystore.getUpadetkey(),key);
    }
    public  boolean checkConnection(Context context) {

        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

        if (activeNetworkInfo != null) { // connected to the internet
            Toast.makeText(context, activeNetworkInfo.getTypeName(), Toast.LENGTH_SHORT).show();

            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                return true;
            } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                return true;
            }
        }
        return false;
    }
    public void dataUpdateCheck(){
        Db_Url url=new Db_Url();
        final RutineDB rutineDB=new RutineDB(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url.getStatus_Url(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            JSONObject status = array.getJSONObject(0);
                            if(!status.getString("status").equals("")){
                              flag.setUpdate_key(status.getString("status"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
    private void dataLoad() {
        Db_Url url=new Db_Url();
        final RutineDB rDB=new RutineDB(getBaseContext());
        rDB.deletetable();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url.getGET_ALLDATA_URL(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
//                            downloadprocessdialog();
                            for (int i = 0; i < array.length(); i++) {
                                //getting product object from json array
                                JSONObject rutine = array.getJSONObject(i);
                                RutineModel r=new RutineModel();
                                r.setCoursecode(rutine.getString("course_code"));
                                r.setCourse_teacher(rutine.getString("course_teacher"));
                                r.setClass_time(rutine.getString("class_time"));
                                r.setAcademicYear(rutine.getString("edu_year"));
                                r.setDay(rutine.getString("day"));
                                rDB.addRutineData(r);
                            }
                            progress.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                          Toast.makeText(getApplicationContext(),error.getStackTrace().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
    private void alertdialog() {
        final RutineDB rutineDB=new RutineDB(this);
        final AlertDialog.Builder member_dialog=new AlertDialog.Builder(this);
        final View member_component=getLayoutInflater().inflate(R.layout.updaterutinedialog,null);
        member_dialog.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                    rutineDB.deletetable();
                    dataLoad();
                    dialogInterface.cancel();
            }

        });
        member_dialog.setView(member_component);
        AlertDialog dialog=member_dialog.create();
        dialog.show();

    }



}
