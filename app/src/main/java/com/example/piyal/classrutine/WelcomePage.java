package com.example.piyal.classrutine;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.piyal.classrutine.batabase.FlagClass;
import com.example.piyal.classrutine.batabase.Keystore;
import com.example.piyal.classrutine.batabase.RutineDB;
import com.example.piyal.classrutine.batabase.model.Db_Url;
import com.example.piyal.classrutine.batabase.model.RutineModel;
import com.example.piyal.classrutine.batabase.model.UpdateKeyStorer;
import com.example.piyal.classrutine.otherActivity.Home;
import com.example.piyal.classrutine.otherActivity.RegisterPanel;
import com.example.piyal.classrutine.otherActivity.RutineViewActivity;
import com.example.piyal.classrutine.register.Createpasswordpage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.piyal.classrutine.R.menu.refreshmenu;

public class WelcomePage extends AppCompatActivity {
    private String password;
    private String isupdate;
    FlagClass flag;
    Keystore key;
    RutineDB rutineDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        key = Keystore.getInstance(this);
        flag=new FlagClass();
        rutineDB=new RutineDB(this);
        dataOnlyUpdateCheck();
        password = key.get(key.getKeyvalue());
        if(checkConnection(this)){

            if(rutineDB.checkUpdate(flag.getUpdate_key())&&!password.equals("")){
                dataLoad();
                dataUpdateflagUpdate();
            }
        }
        if(password.equals("")){
            if(checkConnection(this)){

                dataLoad();

                dataUpdateCheck();
            }
            else{
                Toast.makeText(this,"Connection is not Avaiable.Please Connect to the internet for data Loading",Toast.LENGTH_LONG).show();
            }

        }
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(password.equals("")){
                    startActivity(new Intent(getApplicationContext(),RegisterPanel.class));
                    finish();
                }
                else {
                    startActivity(new Intent(getApplicationContext(),Home.class));
                    finish();
                    }
                finish();
                }

        },3000);
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
    public void dataOnlyUpdateCheck(){
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


                }

        );


        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }


    public void dataUpdateCheck(){
        Db_Url url=new Db_Url();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,url.getStatus_Url(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            JSONObject status = array.getJSONObject(0);
                            if(!status.getString("status").equals("")){
                                UpdateKeyStorer key=new UpdateKeyStorer(1,status.getString("status"));
                                rutineDB.addupdateKey(key);
                                Toast.makeText(getApplicationContext(),rutineDB.getUpdateKeyValue(),Toast.LENGTH_LONG).show();
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
    public void dataUpdateflagUpdate(){
        Db_Url url=new Db_Url();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,url.getStatus_Url(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            JSONObject status = array.getJSONObject(0);
                            if(!status.getString("status").equals("")){
                                UpdateKeyStorer key=new UpdateKeyStorer(1,status.getString("status"));
                                rutineDB.updateKeyValue_of_updatekey(key);
                                Toast.makeText(getApplicationContext(),rutineDB.getUpdateKeyValue(),Toast.LENGTH_LONG).show();

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
    //data load in loacldatabase
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
}
