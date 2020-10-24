package com.github.abdalimran.tourmate.Models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class EventModel extends RealmObject {
    @PrimaryKey
    private String id;
    private String eventName;
    private String destinationFrom;
    private String destinationTo;
    private String startDate;
    private String endDate;
    private double budget=0.00;
    private double balance=0.00;
    private String notes;
    private RealmList<ImageModel> eventMoments;
    private RealmList<EventExpenseModel> eventExpenses;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDestinationFrom() {
        return destinationFrom;
    }

    public void setDestinationFrom(String destinationFrom) {
        this.destinationFrom = destinationFrom;
    }

    public String getDestinationTo() {
        return destinationTo;
    }

    public void setDestinationTo(String destinationTo) {
        this.destinationTo = destinationTo;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public RealmList<ImageModel> getEventMoments() {
        return eventMoments;
    }

    public void setEventMoments(RealmList<ImageModel> eventMoments) {
        this.eventMoments = eventMoments;
    }

    public RealmList<EventExpenseModel> getEventExpenses() {
        return eventExpenses;
    }

    public void setEventExpenses(RealmList<EventExpenseModel> eventExpenses) {
        this.eventExpenses = eventExpenses;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void addEventExpense(EventExpenseModel e) {
        this.eventExpenses.add(e);
    }

    @Override
    public String toString() {
        return "Event Name: "+eventName+"\n"+
               "From: "+destinationFrom+"\n"+
               "To: "+destinationTo+"\n"+
               "Start Date: "+startDate+"\n"+
               "End Date: "+endDate+"\n"+
               "Event Notes: "+notes;
    }
}
