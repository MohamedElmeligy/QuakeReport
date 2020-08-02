package com.example.android.quakereport;

/**
 * Created by no one on 3/7/2017.
 */

//This is a custom class
//which is storing information about Earthquakes
public class Info {

    private String mMagnitude;
    private String mPlace;
    private long mDate;
    private String mUrl;

    public Info(String magnitude , String place , long date , String url){
        mMagnitude = magnitude;
        mPlace = place;
        mDate = date;
        mUrl = url;
    }


    public String getMagnitude(){
        return mMagnitude;
    }

    public String getPlace(){
        return  mPlace;
    }

    public long getDate(){
        return mDate;
    }

    public String getURL(){
        return mUrl;
    }

}
