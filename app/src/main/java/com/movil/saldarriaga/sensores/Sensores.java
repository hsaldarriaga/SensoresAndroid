package com.movil.saldarriaga.sensores;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hass-pc on 18/04/2015.
 */
public class Sensores extends BaseAdapter implements SensorEventListener{

    private final Context c;
    private SensorManager mgn;
    private List<Sensor> sensores;
    private List<SensorValue> values;
    LayoutInflater inflater;
    long time;
    public Sensores(Context c) {
        this.c = c;
        inflater = LayoutInflater.from(c);
        mgn = (SensorManager)c.getSystemService(Context.SENSOR_SERVICE);
        sensores = mgn.getSensorList(Sensor.TYPE_ALL);
        values = new ArrayList<>();
        for (int i = 0; i < sensores.size(); i++) {
            values.add(new SensorValue());
        }
    }

    @Override
    public int getCount() {
        return sensores.size();
    }

    @Override
    public Object getItem(int position) {
        return sensores.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Sensor s = sensores.get(position);
        PlaceHolder holder;
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.sensoritem, parent, false);
            holder = new PlaceHolder();
            holder.Name = (TextView)convertView.findViewById(R.id.Name);
            holder.Type = (TextView)convertView.findViewById(R.id.Type);
            holder.Vendor = (TextView)convertView.findViewById(R.id.Vendor);
            holder.Version = (TextView)convertView.findViewById(R.id.Version);
            holder.Value = (TextView)convertView.findViewById(R.id.Value);
            convertView.setTag(holder);
        } else {
            holder = (PlaceHolder)convertView.getTag();
        }
        holder.Name.setText(s.getName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH)
            holder.Type.setText(s.getStringType());
        else
            holder.Type.setText(s.getType()+"");
        holder.Vendor.setText(s.getVendor());
        holder.Version.setText(s.getVersion()+"");
        SensorValue v = values.get(position);
        holder.Value.setText(v.value+"");
        return convertView;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int index = sensores.indexOf(event.sensor);
        SensorValue v = values.get(index);
        v.value = event.values;
        long t = System.currentTimeMillis() - time;
        if (t > 2000) {
            this.notifyDataSetChanged();
            time = System.currentTimeMillis();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void OnResume()
    {
        for (Sensor s : sensores)
        {
            mgn.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void OnStop()
    {
        mgn.unregisterListener(this);
    }

    static private class PlaceHolder {
        public TextView Name, Type, Vendor, Version, Value;
    }
    static private class SensorValue {
        public float [] value;
    }
}
