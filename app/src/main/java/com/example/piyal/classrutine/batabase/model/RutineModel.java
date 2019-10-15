package com.example.piyal.classrutine.batabase.model;

import java.io.Serializable;

/**
 * Created by piyal on 5/8/2018.
 */
public class RutineModel implements Serializable {

    public boolean isUpdateflag() {
        return updateflag;
    }

    public void setUpdateflag(boolean updateflag) {
        this.updateflag = updateflag;
    }

    public String getAcademic_year() {
        return academic_year;
    }

    public void setAcademic_year(String academic_year) {
        this.academic_year = academic_year;
    }

    private boolean updateflag;
    private int rutine_key;

    public RutineModel(boolean updateflag, int rutine_key, String coursecode, String course_teacher, String class_time, String day, String academic_year) {
        this.updateflag = updateflag;
        this.rutine_key = rutine_key;
        Coursecode = coursecode;
        Course_teacher = course_teacher;
        Class_time = class_time;
        Day = day;
        this.academic_year = academic_year;
    }

    private String Coursecode;
    private String Course_teacher;
    private String Class_time;
    private String Day;

    public String getAcademicYear() {
        return academic_year;
    }

    public void setAcademicYear(String academic_year) {
        this.academic_year = academic_year;
    }

    private String academic_year;
    @Override
    public String toString() {
        return "RutineModel{" +
                "rutine_key=" + rutine_key +
                ", Coursecode='" + Coursecode + '\'' +
                ", Course_teacher='" + Course_teacher + '\'' +
                ", Class_time='" + Class_time + '\'' +
                ", Day='" + Day + '\'' +
                '}';
    }
    public RutineModel(String coursecode, String course_teacher, String class_time, String day) {
        Coursecode = coursecode;
        Course_teacher = course_teacher;
        Class_time = class_time;
        Day = day;
    }

    public int getRutine_key() {
        return rutine_key;
    }

    public void setRutine_key(int rutine_key) {
        this.rutine_key = rutine_key;
    }

    public String getCoursecode() {
        return Coursecode;
    }

    public void setCoursecode(String coursecode) {
        Coursecode = coursecode;
    }

    public String getCourse_teacher() {
        return Course_teacher;
    }

    public void setCourse_teacher(String course_teacher) {
        Course_teacher = course_teacher;
    }

    public String getClass_time() {
        return Class_time;
    }

    public void setClass_time(String class_time) {
        Class_time = class_time;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public RutineModel(int rutine_key, String coursecode, String course_teacher, String class_time, String day) {

        this.rutine_key = rutine_key;
        Coursecode = coursecode;
        Course_teacher = course_teacher;
        Class_time = class_time;
        Day = day;
    }

    public RutineModel() {

    }
}
