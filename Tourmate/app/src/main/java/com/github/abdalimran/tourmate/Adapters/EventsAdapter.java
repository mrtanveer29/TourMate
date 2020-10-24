package com.github.abdalimran.tourmate.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.github.abdalimran.tourmate.Models.EventModel;
import com.github.abdalimran.tourmate.R;

import java.util.List;

public class EventsAdapter extends ArrayAdapter<EventModel> {

    private class ViewHolder {
        private TextView event_title;
        private TextView event_from;
        private TextView event_to;
        private TextView event_startdate;
        private TextView event_enddate;
        private TextView event_budget;
    }

    private final Context context;
    private List<EventModel> events;

    public EventsAdapter(Context context, List<EventModel> events) {
        super(context, R.layout.card_row, events);
        this.context = context;
        this.events = events;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder viewHolder;

        if(view==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view= inflater.inflate(R.layout.card_row, null, true);

            viewHolder = new ViewHolder();

            viewHolder.event_title = (TextView)view.findViewById(R.id.shw_event_title);
            viewHolder.event_from = (TextView)view.findViewById(R.id.shw_event_from);
            viewHolder.event_to = (TextView)view.findViewById(R.id.shw_event_to);
            viewHolder.event_startdate = (TextView)view.findViewById(R.id.shw_event_startdate);
            viewHolder.event_enddate = (TextView)view.findViewById(R.id.shw_event_enddate);
            viewHolder.event_budget = (TextView)view.findViewById(R.id.shw_event_budget);

            view.setTag(viewHolder);

        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.event_title.setText(events.get(position).getEventName());
        viewHolder.event_from.setText(events.get(position).getDestinationFrom());
        viewHolder.event_to.setText(events.get(position).getDestinationTo());
        viewHolder.event_startdate.setText(events.get(position).getStartDate());
        viewHolder.event_enddate.setText(events.get(position).getEndDate());
        viewHolder.event_budget.setText("Estimated Budget: "+events.get(position).getBudget());

        return view;
    }
}