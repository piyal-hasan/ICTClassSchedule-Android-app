package com.example.piyal.classrutine.batabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.piyal.classrutine.batabase.model.RutineModel;
import com.example.piyal.classrutine.batabase.model.UpdateKeyStorer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by piyal on 5/8/2018.
 */
public class RutineDB extends SQLiteOpenHelper {
    private static String db_name="RUTINE_19";
    private static String table_name="class_rutinetest";
    private static String key_table_name="updateKey";

    private static String key_id="key_id";
    private static String Course_code="Course_code";
    private static String Course_teacher="Course_teacher";
    private static String Class_time="class_time";
    private static String Academic_Year="year";
    private static String day="day";
    private static int v=1;

    private static String updateKeyValue="update_key";
    //create rutine table sql
    private static final String Rutine_sql="CREATE TABLE "
            + table_name +" ("+ key_id + " INTEGER PRIMARY KEY,"
            + Course_code +" TEXT,"
            + Course_teacher +" TEXT,"
            + Class_time +" TEXT,"
            + Academic_Year +" TEXT,"
            + day +" TEXT" + ")";
    //create updatekey table sql
    private static final String update_key_sql="CREATE TABLE "
            + key_table_name +" ("+ key_id + " INTEGER,"
            + updateKeyValue +" TEXT" + ")";

    public RutineDB(Context context) {
        super(context,db_name,null, v);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Rutine_sql);
        sqLiteDatabase.execSQL(update_key_sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + table_name);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + key_table_name);
        onCreate(sqLiteDatabase);
    }
    public int addupdateKey(UpdateKeyStorer key){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues value=new ContentValues();
        value.put(updateKeyValue,key.getUpdateKey());
        value.put(key_id,key.getKey());
        return (int) db.insert(key_table_name,null,value);
    }
    public int updateKeyValue_of_updatekey(UpdateKeyStorer key){
        // updating name in users table
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(updateKeyValue,key.getUpdateKey());
       return db.update(key_table_name, values, key_id + " = ?", new String[]{String.valueOf(getId(key.getUpdateKey()))});
    }
    public int getId(String v){
        SQLiteDatabase db=this.getWritableDatabase();
        String sql="SELECT * FROM "+key_table_name+" WHERE update_key="+v;
        int value = 0;
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                value=Integer.parseInt(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        return value;
    }
    public String getUpdateKeyValue(){
        SQLiteDatabase db=this.getWritableDatabase();
        String sql="SELECT * FROM "+key_table_name+" where "+key_id+"=1";
        String value = "";
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                value=cursor.getString(1);
            }while (cursor.moveToNext());
        }
        return value;
    }
//    update checking function
    public boolean checkUpdate(String checkvalue){
        boolean test=false;
        if(!checkvalue.equals(getUpdateKeyValue())){
            test=true;
        }
        return test;
    }
    public int addRutineData(RutineModel rutineModel){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues v=new ContentValues();
        v.put(Course_code,rutineModel.getCoursecode());
        v.put(Course_teacher,rutineModel.getCourse_teacher());
        v.put(Class_time,rutineModel.getClass_time());
        v.put(Academic_Year,rutineModel.getAcademicYear());
        v.put(day,rutineModel.getDay());
        return (int) db.insert(table_name,null,v);

    }
    public List<RutineModel>getRutineData(){
        List<RutineModel> l=new ArrayList<RutineModel>();
        SQLiteDatabase db=this.getWritableDatabase();
        String sql="SELECT * FROM "+table_name;
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
              RutineModel rm=new RutineModel();
                rm.setCoursecode(cursor.getString(1));
                rm.setCourse_teacher(cursor.getString(2));
                rm.setClass_time(cursor.getString(3));
                rm.setAcademicYear(cursor.getString(4));
                rm.setDay(cursor.getString(5));
                l.add(rm);
            }while (cursor.moveToNext());
        }
        return l;
    }
    public void deletetable(){
        SQLiteDatabase db = this.getWritableDatabase(); //get database
        db.execSQL("DELETE FROM "+table_name); //delete all rows in a table
        db.close();
    }
}
