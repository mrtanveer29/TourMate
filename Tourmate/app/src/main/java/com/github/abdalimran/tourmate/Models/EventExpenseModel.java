package com.github.abdalimran.tourmate.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class EventExpenseModel extends RealmObject {
    @PrimaryKey
    private String expID;
    private String expTitle;
    private double expCost;
    private String expDetail;

    public String getExpID() {
        return expID;
    }

    public void setExpID(String expID) {
        this.expID = expID;
    }

    public String getExpDetail() {
        return expDetail;
    }

    public void setExpDetail(String expDetail) {
        this.expDetail = expDetail;
    }

    public String getExpTitle() {
        return expTitle;
    }

    public void setExpTitle(String expTitle) {
        this.expTitle = expTitle;
    }

    public double getExpCost() {
        return expCost;
    }

    public void setExpCost(double expCost) {
        this.expCost = expCost;
    }

    @Override
    public String toString() {
        return "Expense Title: "+getExpTitle()+"\n"+
                "Expense Cost: "+getExpCost()+"\n"+
                "Expende Detail: "+getExpDetail();
    }
}
