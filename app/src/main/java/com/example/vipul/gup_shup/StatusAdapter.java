package com.example.vipul.gup_shup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VIPUL on 07-05-2016.
 */
public class StatusAdapter extends ArrayAdapter {
    List list = new ArrayList();
    public StatusAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void add(Status object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row;
        row = convertView;
        StatusHolder statusHolder;
        if(row == null) {
            LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout,parent,false);
            statusHolder = new StatusHolder();
            statusHolder.tx_username = (TextView) row.findViewById(R.id.usernameRowLayoutTextView);
            statusHolder.tx_status =  (TextView) row.findViewById(R.id.statusRowLayoutTextView);
            row.setTag(statusHolder);

        }
        else {
            statusHolder = (StatusHolder) row.getTag();
        }
        Status status = (Status)this.getItem(position);
        statusHolder.tx_username.setText(status.getUsername());
        statusHolder.tx_status.setText(status.getStatus());
        return row;
    }

    static class StatusHolder {
        TextView tx_username;
        TextView tx_status;
    }
}
