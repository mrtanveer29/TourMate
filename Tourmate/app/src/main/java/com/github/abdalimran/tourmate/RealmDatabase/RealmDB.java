package com.github.abdalimran.tourmate.RealmDatabase;

import android.content.Context;

import com.github.abdalimran.tourmate.Models.EventExpenseModel;
import com.github.abdalimran.tourmate.Models.EventModel;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class RealmDB{

    private Realm tourmateRealm;

    public RealmDB(Context context) {
        tourmateRealm = Realm.getInstance(new RealmConfiguration.Builder(context)
                .name("tourmateDB.realm")
                .deleteRealmIfMigrationNeeded()
                .build());
    }

    public void addTourEvent(EventModel e) {
        tourmateRealm.beginTransaction();
        EventModel event = tourmateRealm.createObject(EventModel.class);
        event.setId(e.getId());
        event.setEventName(e.getEventName());
        event.setDestinationFrom(e.getDestinationFrom());
        event.setDestinationTo(e.getDestinationTo());
        event.setStartDate(e.getStartDate());
        event.setEndDate(e.getEndDate());
        event.setBudget(e.getBudget());
        event.setBalance(e.getBudget());
        event.setNotes(e.getNotes());
        event.setEventMoments(e.getEventMoments());
        tourmateRealm.commitTransaction();
    }

    public void updateTourEvent(EventModel e) {
        EventModel event = tourmateRealm.where(EventModel.class).equalTo("id",e.getId()).findFirst();
        tourmateRealm.beginTransaction();
        event.setEventName(e.getEventName());
        event.setDestinationFrom(e.getDestinationFrom());
        event.setDestinationTo(e.getDestinationTo());
        event.setStartDate(e.getStartDate());
        event.setEndDate(e.getEndDate());
        event.setBudget(e.getBudget());
        event.setNotes(e.getNotes());
        event.setEventMoments(e.getEventMoments());
        tourmateRealm.commitTransaction();
    }

    public void deleteTourEvent(String id) {
        EventModel result = tourmateRealm.where(EventModel.class).equalTo("id",id).findFirst();
        tourmateRealm.beginTransaction();
        result.deleteFromRealm();
        tourmateRealm.commitTransaction();
    }

    public void addEventExpense(String emid, EventExpenseModel e) {
        EventModel result = tourmateRealm.where(EventModel.class).equalTo("id",emid).findFirst();
        tourmateRealm.beginTransaction();
        result.addEventExpense(e);
        result.setBalance(result.getBudget()-e.getExpCost());
        tourmateRealm.commitTransaction();
    }

    public EventModel getSingleEvent(String id){
        EventModel event = tourmateRealm.where(EventModel.class).equalTo("id",id).findFirst();
        return event;
    }

    public RealmResults<EventModel> getAllEvents(){
        RealmResults<EventModel> query = tourmateRealm.where(EventModel.class).findAll();
        return query;
    }

    public void closeRealmDB() {
        tourmateRealm.close();
    }
}
