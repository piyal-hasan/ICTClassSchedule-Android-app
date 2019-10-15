package com.example.piyal.classrutine.otherActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.piyal.classrutine.R;
import com.example.piyal.classrutine.batabase.model.RutineModel;

import java.util.ArrayList;

public class AllSchedule extends AppCompatActivity {
    ArrayList<RutineModel> alldata;
    ArrayList<String> daywisedata;
    String[] day_name_show = {"sat","sun","mon", "tue", "wed"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_schedule);
        alldata=new ArrayList<>();
        alldata = (ArrayList<RutineModel>) getIntent().getSerializableExtra("routinelist");
    }
    public void  allScheduleAction(View v){
        daywisedata=new ArrayList<>();
        switch (v.getId()){
            case R.id.satbtn:{
                dataAddTolist(day_name_show[0]);
                Intent t_int=new Intent(getApplicationContext(),OnlineRutineViewActivity.class);
                t_int.putExtra("routinelist",daywisedata);
                startActivity(t_int);
                break;
            }
            case R.id.sunbtn:{
                dataAddTolist(day_name_show[1]);
                Intent t_int=new Intent(getApplicationContext(),OnlineRutineViewActivity.class);
                t_int.putExtra("routinelist",daywisedata);
                startActivity(t_int);
                break;
            }
            case R.id.monbtn:{
                dataAddTolist(day_name_show[2]);
                Intent t_int=new Intent(getApplicationContext(),OnlineRutineViewActivity.class);
                t_int.putExtra("routinelist",daywisedata);
                startActivity(t_int);
                break;
            }
            case R.id.tuebtn:{
                dataAddTolist(day_name_show[3]);
                Intent t_int=new Intent(getApplicationContext(),OnlineRutineViewActivity.class);
                t_int.putExtra("routinelist",daywisedata);
                startActivity(t_int);
                break;
            }
            case R.id.wedbtn:{
                dataAddTolist(day_name_show[4]);
                Intent t_int=new Intent(getApplicationContext(),OnlineRutineViewActivity.class);
                t_int.putExtra("routinelist",daywisedata);
                startActivity(t_int);
                break;
            }
            case R.id.otherbtn:{
                Toast.makeText(getApplicationContext(),"other",Toast.LENGTH_SHORT).show();
                break;
            }
        }


    }

    private void dataAddTolist(String selectedday) {
            for(int i=0;i<alldata.size();i++){
                if(alldata.get(i).getDay().equals(selectedday)){
                    String view = "Course Code: " + alldata.get(i).getCoursecode() + "\n" +
                            "Course Teacher: " + alldata.get(i).getCourse_teacher() + "\n" + "" + "Class Time: "+
                            "\nday: "+alldata.get(i).getDay();
                    daywisedata.add(view);
                }
            }

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
