package net.smile.bantaengtour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import net.smile.bantaengtour.adapter.MenuAdapter;
import net.smile.bantaengtour.utility.StoredData;

public class MainActivity extends AppCompatActivity {

    private int[] mThumbIds = {
            R.drawable.map, R.drawable.popular,
            R.drawable.budayas, R.drawable.abouts
    };

    private String[] mTitle ={
            "Peta Wisata","Populer","Budaya","About"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0);

        //setting IP
        StoredData.IP ="http://192.168.43.161";

        GridView gridMenu = (GridView)findViewById(R.id.gridMenu);

        gridMenu.setAdapter(new MenuAdapter(this,mTitle, mThumbIds));

        gridMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                switch (position){
                    case 0 :
                        startActivity(new Intent(MainActivity.this,PetaWisataActivity.class));
                        break;
                    case 1 :
                        startActivity(new Intent(MainActivity.this,PopulerActivity.class));
                        break;
                    case 2 :
                        startActivity(new Intent(MainActivity.this,CultureActivity.class));
                        break;
                    case 3 :
                        startActivity(new Intent(MainActivity.this,AboutActivity.class));
                        break;
                }
            }
        });

    }
}