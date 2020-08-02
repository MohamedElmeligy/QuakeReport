package com.example.android.quakereport;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.graphics.drawable.GradientDrawable;

/**
 * Created by no one on 3/9/2017.
 */

//This is a custom Adapter which display the Info of the Earthquakes
public class InfoAdapter extends ArrayAdapter<Info> {

    public InfoAdapter(Context context,  ArrayList<Info> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listview = convertView;
        //this is an Info value that stores the position of the view in the Adapter
        Info CurrentPlace = getItem(position);

        if(listview == null){
            listview = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        //hold the view of the magnitude
        TextView magnitude = (TextView)listview.findViewById(R.id.magnitude);
        //set the correct magnitude in the correct view by calling the CurrentPlace then setting the text
        magnitude.setText(CurrentPlace.getMagnitude());

        GradientDrawable magnitudeCircle = (GradientDrawable) magnitude.getBackground();
        int magnitudeColor = getMagnitudeColor(CurrentPlace.getMagnitude());
        magnitudeCircle.setColor(magnitudeColor);


        String location ;
        String nearFrom;
        String[] part = CurrentPlace.getPlace().split("of");;

        if(CurrentPlace.getPlace().contains("of")){
            nearFrom = part[0] + "of";
            TextView near = (TextView)listview.findViewById(R.id.location_offset);
            near.setText(nearFrom);
            location = part[1];
        }else{
            TextView near = (TextView)listview.findViewById(R.id.location_offset);
            near.setText("Near the");
            location = CurrentPlace.getPlace();
        }


        //hold the view of the place
        TextView place = (TextView)listview.findViewById(R.id.primary_location);
        //set the correct place in the correct view by calling the CurrentPlace then setting the text
        place.setText(location);

        //instantiating date object to format the date
        Date dateObject = new Date(CurrentPlace.getDate());

        //hold the view of the date
        TextView date = (TextView)listview.findViewById(R.id.date);
        //formatting the date and storing in a string
        String formatted = DateFormat(dateObject);
        //setting the text
        date.setText(formatted);

        //hold the view of the date
        TextView time = (TextView)listview.findViewById(R.id.time);
        //formatting the time and storing in a string
        String formattedTIME = TimeFormat(dateObject);
        //setting the text
        time.setText(formattedTIME);

        return listview;
    }

    private String DateFormat(Date dateObject){
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    private String TimeFormat(Date dateObject){
        SimpleDateFormat timeFormat = new SimpleDateFormat("H:mm a");
        return timeFormat.format(dateObject);
    }

    private int getMagnitudeColor(String Value){

        float magnitudeColor = (int) Math.floor(Float.parseFloat(Value));

        switch ((int) magnitudeColor){
            case 0 :
            case 1 :
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude1);
                break;

            case 2 :
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude2);
                break;

            case 3 :
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude3);
                break;

            case 4 :
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude4);
                break;

            case 5 :
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude5);
                break;

            case 6 :
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude6);
                break;

            case 7 :
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude7);
                break;

            case 8 :
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude8);
                break;

            case 9 :
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude9);
                break;

            default:
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude10);
                break;
        }
        return (int) magnitudeColor;
    }
}
