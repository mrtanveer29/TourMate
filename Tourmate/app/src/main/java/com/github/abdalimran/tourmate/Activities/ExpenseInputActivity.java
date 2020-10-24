package com.github.abdalimran.tourmate.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.github.abdalimran.tourmate.Models.EventExpenseModel;
import com.github.abdalimran.tourmate.R;
import com.github.abdalimran.tourmate.RealmDatabase.RealmDB;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExpenseInputActivity extends AppCompatActivity {

    @BindView(R.id.expense_title) EditText expense_title;
    @BindView(R.id.expense_cost) EditText expense_cost;
    @BindView(R.id.expense_detail) EditText expense_detail;
    private RealmDB realmDB;
    private String eid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_input);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        realmDB=new RealmDB(this);

        Bundle data = getIntent().getExtras();
        eid = data.getString("EventID");
    }

    public void addExpense(View view) {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
        String uniqueid = ft.format(dNow);
        String eTitle=expense_title.getText().toString();
        double eCost=Double.parseDouble(expense_cost.getText().toString());
        String eDetail=expense_detail.getText().toString();

        EventExpenseModel eventExpenseModel=new EventExpenseModel();
        eventExpenseModel.setExpID(uniqueid);
        eventExpenseModel.setExpTitle(eTitle);
        eventExpenseModel.setExpCost(eCost);
        eventExpenseModel.setExpDetail(eDetail);

        realmDB = new RealmDB(getApplicationContext());
        realmDB.addEventExpense(eid,eventExpenseModel);
        realmDB.closeRealmDB();
        this.finish();
    }
}
