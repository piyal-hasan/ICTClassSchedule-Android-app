package com.example.piyal.classrutine.otherActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.piyal.classrutine.IndexPage;
import com.example.piyal.classrutine.R;
import com.example.piyal.classrutine.batabase.Keystore;
import com.example.piyal.classrutine.batabase.RutineDB;
import com.example.piyal.classrutine.batabase.model.Db_Url;
import com.example.piyal.classrutine.batabase.model.RutineModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OnlineRutineViewActivity extends AppCompatActivity {
    List<String> list;
    ListView listview;
    List<RutineModel> rl;
    RecyclerView r;
    ArrayAdapter<String> arrayAdapter;
    List<RutineModel> daywiseList;
    boolean flag;
    final RutineDB rdb=new RutineDB(this);
    String[] day_name_show = {"sat","sun","mon", "tue", "wed"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutine_view);
        setTitle("Today Class Schedule");
        listview= (ListView) findViewById(R.id.rutineView);

        list=new ArrayList<String>();


        list = (ArrayList<String>) getIntent().getSerializableExtra("routinelist");

      int size=list.size();

        if(size<=0){
            showwarningMsg();
        }
        arrayAdapter = new ArrayAdapter<String>(getBaseContext(), R.layout.cardrutine_view, R.id.coursecode,list);
        viewBuilder();

    }

    private void showwarningMsg() {
        final AlertDialog.Builder member_dialog=new AlertDialog.Builder(this);
        final View member_component=getLayoutInflater().inflate(R.layout.noschedulemsg,null);
        member_dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(getApplicationContext(), MainPage.class));
            }
        });
        member_dialog.setView(member_component);
        AlertDialog dialog=member_dialog.create();
        dialog.show();
    }

    private void viewBuilder() {
        listview.setAdapter(arrayAdapter);
    }

    public void refreshList() {
        arrayAdapter.notifyDataSetChanged();
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
