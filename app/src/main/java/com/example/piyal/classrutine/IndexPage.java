package com.example.piyal.classrutine;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.piyal.classrutine.batabase.Keystore;
import com.example.piyal.classrutine.batabase.RutineDB;
import com.example.piyal.classrutine.batabase.model.Db_Url;
import com.example.piyal.classrutine.batabase.model.RutineModel;
import com.example.piyal.classrutine.otherActivity.Home;
import com.example.piyal.classrutine.otherActivity.MainPage;
import com.example.piyal.classrutine.otherActivity.OnlineRutineViewActivity;
import com.example.piyal.classrutine.otherActivity.RegisterPanel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class IndexPage extends AppCompatActivity {
    Keystore key;
    private String password;
    ArrayList<RutineModel> list;
    boolean flag;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_page);
//        getSupportActionBar().hide();
        key = Keystore.getInstance(this);
        password = key.get(key.getKeyvalue());

        flag=false;

        list=new ArrayList<RutineModel>();

        if(checkConnection(this)&&!password.equals("")){
            addList();
            flag=true;
        }

        intent=new Intent(IndexPage.this,MainPage.class);
        intent.putExtra("routinelist",list);
        intent.putExtra("flag",flag);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(password.equals("")){
                    startActivity(new Intent(getApplicationContext(),RegisterPanel.class));
                    finish();
                }
                else {
                    startActivity(intent);
                    finish();
                }
                finish();
            }

        },5000);
    }
//    check internet connection
public  boolean checkConnection(Context context) {

    final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

    NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

    if (activeNetworkInfo != null) { // connected to the internet
//        Toast.makeText(context, activeNetworkInfo.getTypeName(), Toast.LENGTH_SHORT).show();

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

//    dataAdd to the list
private void addList() {
    Db_Url url=new Db_Url();
    StringRequest stringRequest = new StringRequest(Request.Method.GET,url.getGET_ALLDATA_URL(),
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Keystore key = Keystore.getInstance(getApplicationContext());

                        String searchKey = key.get(key.getKeyvalue());

                        //converting the string to json array object
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            //getting product object from json array
                            JSONObject routine = array.getJSONObject(i);
                            RutineModel r=new RutineModel();
                            if(routine.getString("edu_year").equals(searchKey)||routine.getString("course_teacher").toLowerCase().equals(searchKey.toLowerCase())){
                                r.setCoursecode(routine.getString("course_code"));
                                r.setCourse_teacher(routine.getString("course_teacher"));
                                r.setClass_time(routine.getString("class_time"));
                                r.setAcademicYear(routine.getString("edu_year"));
                                r.setDay(routine.getString("day"));
                                list.add(r);
                            }
                        }

                        if(list.isEmpty()){
                            Toast.makeText(getApplicationContext(),"Class Schedule Not Available!",Toast.LENGTH_LONG).show();
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
