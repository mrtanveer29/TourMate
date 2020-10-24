package com.github.abdalimran.tourmate.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.github.abdalimran.tourmate.Adapters.NPResponseCustomAdapter;
import com.github.abdalimran.tourmate.Constants.Constants;
import com.github.abdalimran.tourmate.Interfaces.Map_API_Interface;
import com.github.abdalimran.tourmate.MapPojoModels.NearbyPlacesResponse;
import com.github.abdalimran.tourmate.MapPojoModels.Result;
import com.github.abdalimran.tourmate.Models.GPSTracker;
import com.github.abdalimran.tourmate.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NPResponseActivity extends AppCompatActivity{

    private ListView placesList;
    private GPSTracker gps;
    String type="food";
    int[] ipos;
    int ppos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_places_response);
        placesList = (ListView) findViewById(R.id.listnearbyplaces);
        gps=new GPSTracker(this);
        Bundle receivedata = getIntent().getExtras();
        type = receivedata.getString("ptype");
        ipos = receivedata.getIntArray("ipid");
        ppos = receivedata.getInt("ppos");

        final ProgressDialog progressDialog = new ProgressDialog(NPResponseActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Searching...");
        progressDialog.show();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                getNearbyPlaces();
            }
        };
        new Thread(runnable).start();

        new android.os.Handler().postDelayed(
             new Runnable() {
                 public void run() {
                     if(true) {
                         progressDialog.dismiss();
                     }
                 }
        },1200);
    }

    private void getNearbyPlaces() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL_MAP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Map_API_Interface service = retrofit.create(Map_API_Interface.class);

        final String location=gps.getLatitude()+","+gps.getLongitude();

        Call<NearbyPlacesResponse> call = service.getNearbyPlaces(location,"distance",type);
        call.enqueue(new Callback<NearbyPlacesResponse>() {
            @Override
            public void onResponse(Call<NearbyPlacesResponse> call, Response<NearbyPlacesResponse> response) {
                try {

                    ArrayList<Result> plist = new ArrayList();
                    for(int i=0; i<response.body().getResults().size(); i++)
                        plist.add(response.body().getResults().get(i));

                    NPResponseCustomAdapter adapter=new NPResponseCustomAdapter(getApplicationContext(),plist,ipos,ppos);
                    placesList.setAdapter(adapter);
                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<NearbyPlacesResponse> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
    }
}
