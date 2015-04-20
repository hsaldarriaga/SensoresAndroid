package com.movil.saldarriaga.sensores;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity {

    private Sensores sensores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensores = new Sensores(this);
        ListView lv = (ListView)findViewById(R.id.listView);
        lv.setAdapter(sensores);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensores.OnResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensores.OnStop();
    }
}
