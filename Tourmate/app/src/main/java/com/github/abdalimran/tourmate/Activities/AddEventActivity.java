package com.github.abdalimran.tourmate.Activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.github.abdalimran.tourmate.Models.EventModel;
import com.github.abdalimran.tourmate.R;
import com.github.abdalimran.tourmate.RealmDatabase.RealmDB;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddEventActivity extends AppCompatActivity{

    @BindView(R.id.event_name) EditText eventName;
    @BindView(R.id.from) EditText destFrom;
    @BindView(R.id.to) EditText destTo;
    @BindView(R.id.start_date) TextView startDate;
    @BindView(R.id.end_date) TextView endDate;
    @BindView(R.id.add_budget) EditText budget;
    @BindView(R.id.notes) EditText notes;

    private DatePickerDialog dialog;
    private RealmDB realmDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addevent);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        dialog =new DatePickerDialog();
        realmDB=new RealmDB(this);
    }

    public void pickStartDate(View view) {
        dialog.setFlag(dialog.FLAG_START_DATE);
        dialog.show(getSupportFragmentManager(), "datePicker");
    }

    public void pickEndDate(View view) {
        dialog.setFlag(dialog.FLAG_END_DATE);
        dialog.show(getSupportFragmentManager(), "datePicker");
    }

    public void addEvent(View view) {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
        String uniqueid = ft.format(dNow);
        String seventName = eventName.getText().toString();
        String sdestFrom = destFrom.getText().toString();
        String sdestTo = destTo.getText().toString();
        String sstartDate = startDate.getText().toString();
        String sendDate = endDate.getText().toString();
        double sbudget = Double.parseDouble(budget.getText().toString());
        String snotes = notes.getText().toString();

        EventModel event=new EventModel();
        event.setId(uniqueid);
        event.setEventName(seventName);
        event.setDestinationFrom(sdestFrom);
        event.setDestinationTo(sdestTo);
        event.setStartDate(sstartDate);
        event.setEndDate(sendDate);
        event.setBudget(sbudget);
        event.setNotes(snotes);

        realmDB = new RealmDB(getApplicationContext());
        realmDB.addTourEvent(event);
        realmDB.closeRealmDB();

        this.finish();
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
    }


    public class DatePickerDialog extends DialogFragment implements android.app.DatePickerDialog.OnDateSetListener {
        public static final int FLAG_START_DATE = 0;
        public static final int FLAG_END_DATE = 1;

        private int flag = 0;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            return new android.app.DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void setFlag(int i) {
            flag = i;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth);
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            if (flag == FLAG_START_DATE) {
                startDate.setText(format.format(calendar.getTime()));
            } else if (flag == FLAG_END_DATE) {
                endDate.setText(format.format(calendar.getTime()));
            }
        }
    }

}
