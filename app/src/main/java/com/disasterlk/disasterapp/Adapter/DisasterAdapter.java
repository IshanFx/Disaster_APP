package com.disasterlk.disasterapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.disasterlk.disasterapp.DAO.Alert;
import com.disasterlk.disasterapp.R;

import java.util.List;

/**
 * Created by IshanFx on 4/7/2016.
 */
public class DisasterAdapter  extends ArrayAdapter<Alert> {

    Context context;
    int layoutResourceId;
    List<Alert> objects;

    public DisasterAdapter(Context context, int layoutResourceId,List<Alert> objects) {
        super(context, layoutResourceId, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Alert alert = objects.get(position);

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.listview_item, null);
        TextView txtmessage = (TextView) view.findViewById(R.id.txtmessagelist);
        TextView txtarea = (TextView) view.findViewById(R.id.txtareanamelist);
        TextView txtrisklevel = (TextView) view.findViewById(R.id.txtrisklist);
        TextView txtdisaster = (TextView) view.findViewById(R.id.txtdisasterlist);
        TextView txtdate = (TextView) view.findViewById(R.id.txtdatelist);
        txtmessage.setText(alert.getAMessage());
        txtarea.setText(alert.getArea_name());
        txtrisklevel.setText(alert.getARiskLevel());
        txtdisaster.setText(alert.getADisasterType());
        txtdate.setText(alert.getADate());
      /*  TextView txt = (TextView) view.findViewById(R.id.txtTitle);
        TextView txtDate = (TextView) view.findViewById(R.id.txtDate);
        TextView txtType  = (TextView) view.findViewById(R.id.txtType);
        ImageView img = (ImageView) view.findViewById(R.id.imgIcon);
        int res = 0;
        txt.setText("Time: "+DT[1].toString());
        txtDate.setText("Date: "+DT[0].toString());
        switch (alert.getType()){
            case "E":
                caseName="Evidence";
                res = context.getResources().getIdentifier("camera", "drawable", context.getPackageName());
                break;
            case "R":
                caseName="Robbery";
                res = context.getResources().getIdentifier("marker", "drawable", context.getPackageName());
                break;
            case "K":
                caseName="Kidnap";
                res = context.getResources().getIdentifier("run", "drawable", context.getPackageName());
                break;
        }
        txtType.setText(caseName);
        img.setImageResource(res);*/
        return view;
    }

}

