package com.example.piyal.classrutine.otherActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.piyal.classrutine.R;
import com.example.piyal.classrutine.batabase.Keystore;
import com.example.piyal.classrutine.batabase.RutineDB;
import com.example.piyal.classrutine.batabase.model.Db_Url;
import com.example.piyal.classrutine.batabase.model.RutineModel;
import com.example.piyal.classrutine.batabase.viewModel.RutineView;

import com.example.piyal.classrutine.register.Createpasswordpage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RutineViewActivity extends AppCompatActivity {
    List list;
    ListView listview;
    List<RutineModel> rl;
    RecyclerView r;
    ArrayAdapter<String> arrayAdapter;
    List<RutineModel> daywiseList;
    final RutineDB rdb=new RutineDB(this);
    String[] day_name_show = {"sat","sun","mon", "tue", "wed"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutine_view);
        setTitle("Today Class Schedule");
        listview= (ListView) findViewById(R.id.rutineView);
        list=new ArrayList();
        arrayAdapter = new ArrayAdapter<String>(getBaseContext(), R.layout.rutine_view, R.id.coursecode,list);
        addList();
        viewBuilder();
    }
    private void viewBuilder() {
        addList();
        listview.setAdapter(arrayAdapter);
    }

    private void addList() {
        rl =new RutineDB(getBaseContext()).getRutineData();

        Keystore key = Keystore.getInstance(this);

        String searchKey = key.get(key.getKeyvalue());

        daywiseList = new ArrayList<>();
        Toast.makeText(getBaseContext(),String.valueOf(rl.size()),Toast.LENGTH_SHORT).show();
        for (int i = 0; i < rl.size(); i++) {
            if((rl.get(i).getAcademicYear().equals(searchKey)||rl.get(i).getCourse_teacher().toLowerCase().equals(searchKey.toLowerCase()))&&rl.get(i).getDay().equals(getDate())){
                String view="Course Code: "+rl.get(i).getCoursecode()+"\n"+"Course Teacher: "+rl.get(i).getCourse_teacher()+"\n"+"" +
                        "Class Time: "+rl.get(i).getClass_time();
                list.add(view);
            }

        }
    }
    private String getDate() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_WEEK);
        return day_name_show[day - 1];
    }

}
