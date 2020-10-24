package com.github.abdalimran.tourmate.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.abdalimran.tourmate.Adapters.EventsAdapter;
import com.github.abdalimran.tourmate.Models.EventModel;
import com.github.abdalimran.tourmate.R;
import com.github.abdalimran.tourmate.RealmDatabase.RealmDB;

import java.util.List;

import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private DrawerLayout hidedrawerLayout;
    private ActionBarDrawerToggle toggle;
    private TextView navHeaderName;
    private TextView navHeaderEmail;
    private de.hdodenhof.circleimageview.CircleImageView profile_image;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private static final int REQUEST_LOGIN = 1;
    private RealmDB realmDB;
    private RealmResults realmResults;
    private ListView eventList;
    private EventsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setResult(RESULT_OK, getIntent());

        drawerLayout= (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView= (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View header=navigationView.getHeaderView(0);
        navHeaderName= (TextView) header.findViewById(R.id.nav_header_name);
        navHeaderEmail= (TextView) header.findViewById(R.id.nav_header_email);
        profile_image= (de.hdodenhof.circleimageview.CircleImageView) header.findViewById(R.id.profile_image);
        sharedPreferences = getSharedPreferences("TourmateData", MODE_PRIVATE);
        editor=sharedPreferences.edit();

        String hname=sharedPreferences.getString("name", "");
        String hemail=sharedPreferences.getString("email", "");
        String image=sharedPreferences.getString("image", "");

        if(hname!=null && hemail!=null){
            navHeaderName.setText(hname);
            navHeaderEmail.setText(hemail);
        }
        if(!image.equals("")) {
            Bitmap imageB = decodeToBase64(image);
            profile_image.setImageBitmap(imageB);
        }
    }

    void initView(){
        realmDB = new RealmDB(this);
        realmResults= realmDB.getAllEvents();

        final List<EventModel> arrayList=realmResults.subList(0, realmResults.size());

        eventList=(ListView)findViewById(R.id.eventList);
        adapter = new EventsAdapter(this,arrayList);
        eventList.setAdapter(adapter);
        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),EventDetailActivity.class);
                intent.putExtra("EventID", arrayList.get(position).getId());
                startActivity(intent);
            }
        });

        registerForContextMenu(eventList);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        EventModel eventDetail = (EventModel) eventList.getAdapter().getItem(info.position);
        String eid = eventDetail.getId();

        switch (item.getItemId()) {
            case R.id.menu_edit:
                Intent intent=new Intent(getApplicationContext(),UpdateEventActivity.class);
                intent.putExtra("EventID", eid);
                startActivity(intent);
                return true;
            case R.id.menu_delete:
                realmDB.deleteTourEvent(eid);
                realmDB.closeRealmDB();
                initView();
                return true;
            case R.id.menu_add_expense:
                Intent intent2=new Intent(getApplicationContext(),ExpenseInputActivity.class);
                intent2.putExtra("EventID", eid);
                startActivity(intent2);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(0,0);
        initView();
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_LOGIN) {
            if (resultCode == RESULT_OK) {
                this.finish();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_edit_profile:
                Intent intent = new Intent(this, EditProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                overridePendingTransition(0,0);
                startActivity(intent);
                break;
            case R.id.weather:
                Intent intent1= new Intent(this, WeatherUpdates.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                overridePendingTransition(0,0);
                startActivity(intent1);
                break;
            case R.id.nearby:
                Intent intent2= new Intent(this, NPMainActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                overridePendingTransition(0,0);
                startActivity(intent2);
                break;
            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out my app at: https://play.google.com/store/apps/details?id=com.google.android.apps.plus");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;
            case R.id.nav_logout:
                editor.putBoolean("autologin",false);
                editor.commit();
                Intent in = new Intent(this, LoginActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                overridePendingTransition(0,0);
                startActivityForResult(in,REQUEST_LOGIN);
                break;
        }

        hidedrawerLayout= (DrawerLayout) findViewById(R.id.drawerLayout);
        if (hidedrawerLayout.isDrawerOpen(GravityCompat.START))
            hidedrawerLayout.closeDrawer(GravityCompat.START);

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)==true)
            return true;
        return super.onOptionsItemSelected(item);
    }

    public static Bitmap decodeToBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public void AddEvent(View view) {
        Intent intent=new Intent(this,AddEventActivity.class);
        startActivity(intent);
    }
}
