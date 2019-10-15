package com.example.piyal.classrutine.otherActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.wifi.WifiManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.piyal.classrutine.IndexPage;
import com.example.piyal.classrutine.R;
import com.example.piyal.classrutine.batabase.RutineDB;
import com.example.piyal.classrutine.batabase.model.RutineModel;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainPage extends AppCompatActivity {
    TextView datename,month;

    ArrayList<RutineModel> routine;

    ArrayList<String> todayRoutinelist;

    boolean flag;

    Intent intent;

    String[] day_name_show = {"sat","sun","mon", "tue", "wed","thu","fri"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);


        month= (TextView) findViewById(R.id.month);

        datename=(TextView) findViewById(R.id.date);

        routine=new ArrayList<RutineModel>();

        todayRoutinelist=new ArrayList<>();

        Date date=new Date();

        Calendar mCalendar = Calendar.getInstance();

        String monthname = mCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());

        String day          = (String) DateFormat.format("dd",   date); // 20
        String monthString  = (String) DateFormat.format("MMM",  date); // Jun


        month.setText(monthString.toUpperCase()+" SCHEDULE");

        datename.setText(day.toUpperCase());

        routine = (ArrayList<RutineModel>) getIntent().getSerializableExtra("routinelist");

        flag=getIntent().getExtras().getBoolean("flag");

        int size=routine.size();

        intent=new Intent(MainPage.this,AllSchedule.class);

        intent.putExtra("routinelist",routine);

        if(!flag){
            showwarningMsg();
        }

            findViewById(R.id.todaybtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    todayRoutine();
                    Intent t_int=new Intent(getApplicationContext(),OnlineRutineViewActivity.class);
                    t_int.putExtra("routinelist",todayRoutinelist);
                    startActivity(t_int);
                }
            });
            findViewById(R.id.allrutinebtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(intent);

                }
            });
            findViewById(R.id.exambtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(intent);
                }
            });
            findViewById(R.id.otherbtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(intent);
                }
            });



    }

    private void todayRoutine() {
      for(int i=0;i<routine.size();i++){
          if(routine.get(i).getDay().equals(getDate())){
              String view = "Course Code: " + routine.get(i).getCoursecode() + "\n" +
                      "Course Teacher: " + routine.get(i).getCourse_teacher() + "\n" + "" + "Class Time: "+
                      "\nday: "+routine.get(i).getDay();
              todayRoutinelist.add(view);
          }
      }
    }

    //data add dialog method
    private void showwarningMsg() {
        final AlertDialog.Builder member_dialog=new AlertDialog.Builder(this);
        final View member_component=getLayoutInflater().inflate(R.layout.internetconnectionwarningmsg,null);
        final TextView msg= (TextView)member_component.findViewById(R.id.msg);
        final Switch wifi = (Switch)member_component.findViewById(R.id.wifibtn);
        final Switch mobiledata = (Switch)member_component.findViewById(R.id.databtn);
        wifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    msg.setText("WiFi is ON");
                    WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                    wifi.setWifiEnabled(true);
                    // The toggle is enabled
                } else {
                    msg.setText("WiFi is OFF");
                    WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                    wifi.setWifiEnabled(true);
                }
            }
        });
        mobiledata.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    msg.setText("");
                    msg.setText("Mobile Data is ON");
                   setMobileDataState(true);
                } else {
                    msg.setText("");
                    msg.setText("Mobile Data is Off");
                    setMobileDataState(false);
                }
            }
        });
        member_dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
              startActivity(new Intent(getApplicationContext(), IndexPage.class));
            }
        });
        member_dialog.setView(member_component);
        AlertDialog dialog=member_dialog.create();
        dialog.show();
    }
    public void setMobileDataState(boolean mobileDataEnabled)
    {
        try
        {
            TelephonyManager telephonyService = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

            Method setMobileDataEnabledMethod = telephonyService.getClass().getDeclaredMethod("setDataEnabled", boolean.class);

            if (null != setMobileDataEnabledMethod)
            {
                setMobileDataEnabledMethod.invoke(telephonyService, mobileDataEnabled);
            }
        }
        catch (Exception ex)
        {
            Log.e("error", "Error setting mobile data state", ex);
        }
    }
    private String getDate() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_WEEK);
        return day_name_show[day - 1];
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

}
