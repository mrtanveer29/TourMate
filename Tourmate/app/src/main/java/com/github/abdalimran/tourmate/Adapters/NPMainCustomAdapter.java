package com.github.abdalimran.tourmate.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.abdalimran.tourmate.R;

public class NPMainCustomAdapter extends BaseAdapter {

    private class ViewHolder {
        ImageView imageView;
        TextView textView;
    }

    private Context mContext;
    private final String[] places_name;
    private final int[] Imageid;

    public NPMainCustomAdapter(Context c, String[] places_name, int[] Imageid ) {
        mContext = c;
        this.Imageid = Imageid;
        this.places_name = places_name;
    }

    @Override
    public int getCount() {
        return places_name.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if(view==null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view= inflater.inflate(R.layout.np_main_itemsingle, null, true);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.item_image);
            viewHolder.textView = (TextView) view.findViewById(R.id.item_text);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.imageView.setImageResource(Imageid[position]);
        viewHolder.textView.setText(places_name[position]);
        return view;
    }
}
