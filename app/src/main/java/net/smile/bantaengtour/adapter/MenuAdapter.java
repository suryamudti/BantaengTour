package net.smile.bantaengtour.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import net.smile.bantaengtour.R;

/**
 * Created by GROUNDMOS on 4/17/2017.
 */

public class MenuAdapter extends BaseAdapter {

    Context mContext;
    String[] tittle;
    int[] images;
    private static LayoutInflater inflater = null;

    public MenuAdapter(Context mContext, String[] tittle, int[] images) {
        this.mContext = mContext;
        this.images = images;
        this.tittle = tittle;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return tittle.length;

    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MenuHolder holder = new MenuHolder();
        View view;

        view = inflater.inflate(R.layout.custmenugrid,null);
        holder.iv = (ImageView)view.findViewById(R.id.imgMenu);
        holder.tv = (TextView)view.findViewById(R.id.tvMenu);

        holder.iv.setImageResource(images[position]);
        holder.tv.setText(tittle[position]);
        return view;
    }

    public class MenuHolder{
        ImageView iv;
        TextView tv;
    }

}
