package com.github.abdalimran.tourmate.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.abdalimran.tourmate.Models.EventExpenseModel;
import com.github.abdalimran.tourmate.Models.EventModel;
import com.github.abdalimran.tourmate.R;
import com.github.abdalimran.tourmate.RealmDatabase.RealmDB;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventDetailActivity extends AppCompatActivity {

    @BindView(R.id.shw_event_title) TextView shw_event_title;
    @BindView(R.id.shw_event_from) TextView shw_event_from;
    @BindView(R.id.shw_event_to) TextView shw_event_to;
    @BindView(R.id.shw_event_startdate) TextView shw_event_startdate;
    @BindView(R.id.shw_event_enddate) TextView shw_event_endtdate;
    @BindView(R.id.shw_event_budget) TextView shw_event_budget;
    @BindView(R.id.shw_event_balance) TextView shw_event_balance;
    @BindView(R.id.shw_event_notes) TextView shw_event_notes;
    private ListView shw_expense_list;
    private RealmDB realmDB;
    private String eid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle data = getIntent().getExtras();
        eid = data.getString("EventID");
        realmDB=new RealmDB(this);
        shw_expense_list= (ListView) findViewById(R.id.shw_expense_list);

        EventModel em=realmDB.getSingleEvent(eid);
        shw_event_title.setText(em.getEventName());
        shw_event_from.setText(em.getDestinationFrom());
        shw_event_to.setText(em.getDestinationTo());
        shw_event_startdate.setText(em.getStartDate());
        shw_event_endtdate.setText(em.getEndDate());
        shw_event_balance.setText("Current Balance: "+String.valueOf(em.getBalance()));
        shw_event_budget.setText("Budget: "+String.valueOf(em.getBudget()));
        shw_event_notes.setText("Notes: "+em.getNotes());

        final List<EventExpenseModel> arrayList=em.getEventExpenses().subList(0, em.getEventExpenses().size());
        ArrayAdapter<EventExpenseModel> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, arrayList);
        shw_expense_list.setAdapter(adapter);
    }
}
