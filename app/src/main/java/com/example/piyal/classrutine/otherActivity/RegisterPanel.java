package com.example.piyal.classrutine.otherActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.piyal.classrutine.IndexPage;
import com.example.piyal.classrutine.R;
import com.example.piyal.classrutine.batabase.Keystore;
import com.example.piyal.classrutine.batabase.RutineDB;

public class RegisterPanel extends AppCompatActivity {
    Spinner semestersp;
    EditText couresteacher;
    RadioButton teacherrbt,studentrbt;
    TextView info;
    Button savebt;
    String keyValue="";
    boolean flag=false;
    RutineDB rutine_DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerpage);
//        getSupportActionBar().hide();

        couresteacher= (EditText) findViewById(R.id.courseteacher);

        teacherrbt= (RadioButton) findViewById(R.id.teacherrbt);

        studentrbt= (RadioButton) findViewById(R.id.studentrbt);

        savebt=(Button)findViewById(R.id.teacher_namesavebt);

        semestersp= (Spinner) findViewById(R.id.semestersp);

        info= (TextView) findViewById(R.id.info);

        info.setVisibility(View.GONE);

        semestersp.setVisibility(View.GONE);

        couresteacher.setVisibility(View.GONE);

        rutine_DB=new RutineDB(this);

        String[] semester_name={"Select your academic year","1-1","1-2","2-1","2-2","3-1","3-2","4-1","4-2","5-1","5-2"};
        ArrayAdapter<String> spinneradb=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,semester_name);
        spinneradb.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        semestersp.setAdapter(spinneradb);

        semestersp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>0){
//                    Toast.makeText(getBaseContext(),semestersp.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
                    keyValue=semestersp.getSelectedItem().toString();
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        buttonAction();

    }

    private void buttonAction() {
        teacherrbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(teacherrbt.isChecked()){
                    teacherrbt.setTypeface(null, Typeface.BOLD_ITALIC);
                    studentrbt.setTypeface(null,Typeface.NORMAL);
                    savebt.setVisibility(View.VISIBLE);
                    couresteacher.setVisibility(View.VISIBLE);
                    semestersp.setVisibility(View.GONE);
                    info.setVisibility(View.VISIBLE);
                    studentrbt.setChecked(false);
                    flag=true;
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
                    couresteacher.setVisibility(View.GONE);
                    semestersp.setVisibility(View.VISIBLE);
                    info.setVisibility(View.GONE);

                }
            }
        });
        savebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String teacher_name=couresteacher.getText().toString();
                couresteacher.setText("");
                if(flag){
                    if(teacher_name.equals("")){
                        Toast.makeText(getBaseContext(),"data not insert!",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        keystore(teacher_name);
                        Toast.makeText(getBaseContext(),"Save as: "+teacher_name,Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getBaseContext(),IndexPage.class));
                    }
                }
                else{
                    if(!keyValue.equals("")){
                        keystore(keyValue);
                        Toast.makeText(getBaseContext(),"Save as: "+keyValue,Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getBaseContext(),IndexPage.class));
                    }
                    else{
                        Toast.makeText(getBaseContext(),"Please Select your Academic year.",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
    public void keystore(String key){
        Keystore keystore=Keystore.getInstance(getApplicationContext());
        keystore.put(keystore.getKeyvalue(),key);
//        Toast.makeText(getBaseContext(),keystore.get(keystore.getKeyvalue()),Toast.LENGTH_LONG).show();
    }
    }

