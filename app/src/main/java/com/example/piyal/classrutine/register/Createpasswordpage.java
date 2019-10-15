package com.example.piyal.classrutine.register;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.piyal.classrutine.R;
import com.example.piyal.classrutine.WelcomePage;
import com.example.piyal.classrutine.batabase.Keystore;
import com.example.piyal.classrutine.batabase.RutineDB;
import com.example.piyal.classrutine.batabase.model.Db_Url;
import com.example.piyal.classrutine.batabase.model.RutineModel;
import com.example.piyal.classrutine.otherActivity.Controller;
import com.example.piyal.classrutine.otherActivity.RutineViewActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Createpasswordpage extends AppCompatActivity {

    EditText pass,confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createpasswordpage);
        addItemTolist();

    }
    //data add dialog method
    private void addItemTolist() {
        final RutineDB rutineDB=new RutineDB(this);
        final AlertDialog.Builder member_dialog=new AlertDialog.Builder(this);
        final View member_component=getLayoutInflater().inflate(R.layout.registerpage,null);
        final LinearLayout techechersection=member_component.findViewById(R.id.teachersection);
        final EditText couresteacher=(EditText)member_component.findViewById(R.id.courseteacher);
        final RadioButton teacherrbt=member_component.findViewById(R.id.teacherrbt);
        final RadioButton studentrbt=member_component.findViewById(R.id.studentrbt);
        final Button savebt=(Button)member_component.findViewById(R.id.teacher_namesavebt);
        final RutineModel rm=new RutineModel();
        final Spinner sp=(Spinner)member_component.findViewById(R.id.smsp);
        String[] day_name={"1-1","1-2","2-1","2-2","3-1","3-2","4-1","4-2","5-1","5-2"};
        final ArrayAdapter<String> spabd;
        spabd=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,day_name);
        spabd.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sp.setAdapter(spabd);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                rm.setDay(sp.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        teacherrbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(teacherrbt.isChecked()){
                    teacherrbt.setTypeface(null, Typeface.BOLD_ITALIC);
                    studentrbt.setTypeface(null,Typeface.NORMAL);
                    //techechersection.setVisibility(View.INVISIBLE);
                    sp.setVisibility(View.GONE);
                    savebt.setVisibility(View.VISIBLE);
                    couresteacher.setVisibility(View.VISIBLE);
                    studentrbt.setChecked(false);
                }
            }
        });
        studentrbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(studentrbt.isChecked()){

                    studentrbt.setTypeface(null,Typeface.BOLD_ITALIC);
                    teacherrbt.setTypeface(null,Typeface.NORMAL);

                    teacherrbt.setChecked(false);
                    //techechersection.setVisibility(View.GONE);
//                    savebt.setVisibility(View.GONE);
                    couresteacher.setVisibility(View.GONE);
                    String year=couresteacher.getText().toString();
                    couresteacher.setText("");
                    if(year.equals("")){
                        Toast.makeText(getBaseContext(),"No data entered!",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if(!year.equals("")){
                            keystore(year);
                            Toast.makeText(getBaseContext(),"Save: "+year,Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getBaseContext(),"Please enter the data",Toast.LENGTH_SHORT).show();
                        }
                    }
                    Toast.makeText(getBaseContext(),"press Next Button",Toast.LENGTH_SHORT).show();
                }
            }
        });
        savebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String teacher_name=couresteacher.getText().toString();
                couresteacher.setText("");
                if(teacher_name.equals("")){
                    Toast.makeText(getBaseContext(),"No data entered!",Toast.LENGTH_SHORT).show();
                }
                else {
                    if(!teacher_name.equals("")){
                         keystore(teacher_name);
                        Toast.makeText(getBaseContext(),"Save: "+teacher_name,Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getBaseContext(),"Please enter the data",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        member_dialog.setNegativeButton("Next", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(rutineDB.getRutineData().isEmpty()){
                    dataLoad();
                    Toast.makeText(getBaseContext(),"DataLoading",Toast.LENGTH_LONG).show();
                }
                else{
                    startActivity(new Intent(getApplicationContext(),RutineViewActivity.class));
                }
                startActivity(new Intent(getApplicationContext(),RutineViewActivity.class));
                dialogInterface.cancel();
            }
        });
        member_dialog.setView(member_component);
        AlertDialog dialog=member_dialog.create();
        dialog.show();

    }
    public void keystore(String key){
        Keystore keystore=Keystore.getInstance(Createpasswordpage.this);
        keystore.put(keystore.getKeyvalue(),key);
    }
    private void dataLoad() {
        Db_Url url=new Db_Url();
        final RutineDB rDB=new RutineDB(getBaseContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url.getGET_ALLDATA_URL(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {
                                //getting product object from json array
                                JSONObject rutine = array.getJSONObject(i);
                                RutineModel r=new RutineModel();
                                r.setCoursecode(rutine.getString("course_code"));
                                r.setCourse_teacher(rutine.getString("course_teacher"));
                                r.setClass_time(rutine.getString("class_time"));
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
