package com.example.mark.scan;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by Mtech on 2017/3/13.
 */

public class MyApplication extends Application {

    private ArrayList<String> someVariable;

    public ArrayList<String> getSomeVariable() {
        return someVariable;
    }

    public void setSomeVariable(String someVariable) {
        this.someVariable.add(0,someVariable);
    }
}