package com.github.abdalimran.tourmate.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.abdalimran.tourmate.Adapters.NPMainCustomAdapter;
import com.github.abdalimran.tourmate.R;

public class NPMainActivity extends AppCompatActivity {

    private ListView listviewplaces;
    String[] places_name = {
            "ATM Booth",
            "Banks",
            "Bus Stands",
            "Cafes",
            "Shops & Stores",
            "Restaurants",
            "Hotels",
            "Hospitals",
            "Pharmacies",
            "Parks",
            "Police",
            "Shopping Mall"
    };

    String[] places_id = {
            "atm",
            "bank",
            "bus_station",
            "cafe",
            "department_store",
            "food",
            "hotel",
            "hospital",
            "pharmacy",
            "park",
            "police",
            "shopping_mall"
    };

    int[] imageId = {
            R.drawable.atm,
            R.drawable.bank,
            R.drawable.bus,
            R.drawable.cafe,
            R.drawable.shop,
            R.drawable.food,
            R.drawable.hotel,
            R.drawable.hospital,
            R.drawable.pharmacy,
            R.drawable.park,
            R.drawable.police,
            R.drawable.mall
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_places);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NPMainCustomAdapter adapter = new NPMainCustomAdapter(this, places_name, imageId);
        listviewplaces = (ListView) findViewById(R.id.listviewplaces);
        listviewplaces.setAdapter(adapter);
        listviewplaces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ptype=places_id[position];

                Intent intent = new Intent(getApplicationContext(), NPResponseActivity.class);
                Bundle bundledata=new Bundle();
                bundledata.putString("ptype",ptype);
                bundledata.putInt("ppos",position);
                bundledata.putIntArray("ipid",imageId);
                intent.putExtras(bundledata);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
    }
}
