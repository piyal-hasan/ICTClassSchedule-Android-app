package com.example.piyal.classrutine.otherActivity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.piyal.classrutine.R;
import com.example.piyal.classrutine.batabase.Keystore;
import com.example.piyal.classrutine.batabase.RutineDB;
import com.example.piyal.classrutine.batabase.model.RutineModel;
import com.example.piyal.classrutine.batabase.viewModel.RutineView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Controller extends AppCompatActivity {
    List<RutineModel> rutineModelList;
    RecyclerView r;
    RutineView rutineView;
    List<RutineModel> daywiseList;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Keystore keystore;
    String[] day_name_show={"SUNDAY","MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.controller);
        setTitle("Class Rutine");
        firebaseDatabase=FirebaseDatabase.getInstance();
        rutineModelList=new ArrayList<>();
        keystore=new Keystore(this);
        r= (RecyclerView) findViewById(R.id.recyclerview);
        addlist();
        viewBuilder();
    }

    private void addlist() {
        String mainclass=keystore.get(keystore.getKeyvalue());
        databaseReference=firebaseDatabase.getReference(mainclass);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                RutineModel model=dataSnapshot.getValue(RutineModel.class);
                rutineModelList.add(model);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    //data add dialog method
    private void addItemTolist() {
        final AlertDialog.Builder member_dialog=new AlertDialog.Builder(this);
        final View member_component=getLayoutInflater().inflate(R.layout.rutineadd_dialog,null);
        final EditText couresecode=(EditText)member_component.findViewById(R.id.coursecode);
        final EditText courseteacher=(EditText)member_component.findViewById(R.id.courseteacher);
        final EditText classtime=(EditText)member_component.findViewById(R.id.classtime);
        Button savebt=(Button)member_component.findViewById(R.id.rutineaddbt);
        final RutineModel rm=new RutineModel();
        final Spinner sp=(Spinner)member_component.findViewById(R.id.day);
        String[] day_name={"SUNDAY","MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY"};
        ArrayAdapter<String> spabd;
        spabd=new ArrayAdapter<String>(Controller.this,android.R.layout.simple_spinner_dropdown_item,day_name);
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
        savebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!couresecode.getText().equals("")&&!courseteacher.getText().equals("")&&!classtime.getText().equals("")&&!sp.getSelectedItem().toString().equals("DAY")){
                    rm.setCoursecode(couresecode.getText().toString());
                    rm.setClass_time(classtime.getText().toString());
                    rm.setCourse_teacher(courseteacher.getText().toString());
                    //RutineDB rdb=new RutineDB(Controller.this);

                        Toast.makeText(getBaseContext(),"Save !",Toast.LENGTH_SHORT).show();
                        //rutineModelList.clear();
                        //addlist(rutineView.notifyDataSetChanged();
                        viewBuilder();
                    }
                else {
                    Toast.makeText(getBaseContext(),"Fill up fields",Toast.LENGTH_SHORT).show();
                }
            }
        });
        member_dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        member_dialog.setView(member_component);
        AlertDialog dialog=member_dialog.create();
        dialog.show();
    }
    private void viewBuilder() {
        //rutineView.notifyDataSetChanged();
        addlist();
        r.setHasFixedSize(true);
        r.setLayoutManager(new LinearLayoutManager(this));
        rutineView=new RutineView(this,rutineModelList);
        r.setAdapter(rutineView);

    }
    private String showdate() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_WEEK);
        return day_name_show[day-1];
    }
}
