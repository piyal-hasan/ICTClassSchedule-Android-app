package com.example.piyal.classrutine.otherActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.piyal.classrutine.R;
import com.example.piyal.classrutine.batabase.FlagClass;
import com.example.piyal.classrutine.batabase.RutineDB;
import com.example.piyal.classrutine.batabase.model.Db_Url;
import com.example.piyal.classrutine.batabase.model.RutineModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Testing extends AppCompatActivity {
    TextView data,key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        data= (TextView) findViewById(R.id.data);
        key= (TextView) findViewById(R.id.key);
        dataUpdateCheck();
        dataLoad();

    }
    public void dataUpdateCheck(){
        Db_Url url=new Db_Url();
        final FlagClass updateflag=new FlagClass();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url.getStatus_Url(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            JSONObject status = array.getJSONObject(0);
//                            if(!status.getString("status").equals("")){
//                                updateflag.setUpdate_key(status.getString("status"));
//                            }
                            key.setText(status.getString("status"));
                            Toast.makeText(getApplicationContext(),status.getString("status"),Toast.LENGTH_LONG).show();

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
                            data.setText(rDB.toString());

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
}
