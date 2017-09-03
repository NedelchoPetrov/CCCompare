package com.example.nedo.cccompare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Nedo on 27/08/2017.
 */

public class CustomAdapter extends BaseExpandableListAdapter {

    private Context c;
    private ArrayList<Currency> currencyList;
    private LayoutInflater inflater;

    public CustomAdapter(Context c, ArrayList<Currency> currencyList){
        this.c = c;
        this.currencyList = currencyList;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        return null;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = inflater.inflate(R.layout.child_item, null);
        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return currencyList.get(groupPosition);
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.group_item, null);
        }

        //Get currency
        Currency curr = (Currency) getGroup(groupPosition);

        //Set objects to work with:
        ImageView img1 = (ImageView) convertView.findViewById(R.id.imageView1);
        TextView text1 = (TextView) convertView.findViewById(R.id.textView1);
        ImageView img2 = (ImageView) convertView.findViewById(R.id.imageView2);
        TextView text2 = (TextView) convertView.findViewById(R.id.textView2);


        //setting 'img1'(Currency icon)
        String icon = curr.icon;
        new DownloadImageTask(img1).execute("https://www.cryptocompare.com" + icon);


        //setting 'text1'(Currency name)
        String name = curr.name;
        text1.setText(name);

        //setting 'img2'(Price up or down)
        boolean priceUp = curr.priceUp;

        if (priceUp){
            img2.setImageResource(R.drawable.priceup);
        }else{
            img2.setImageResource(R.drawable.pricedown);
        }


        //setting 'text2' (currentPrice)

        double currentPrice = curr.currentPrice;
        String str = Double.toString(currentPrice);
        text2.setText(str);

        //Set team row background color
        //convertView.setBackgroundColor(Color.LTGRAY);

        return convertView;
    }

    @Override
    public int getGroupCount() {
        return currencyList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
