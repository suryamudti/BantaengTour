package net.smile.bantaengtour.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.smile.bantaengtour.R;
import net.smile.bantaengtour.entity.Culture;
import net.smile.bantaengtour.entity.CultureDetail;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by GROUNDMOS on 4/28/2017.
 */

public class CultureDetailAdapter extends RecyclerView.Adapter<CultureDetailAdapter.ViewHolder> {

    private Context context;
    private List<CultureDetail> data;

    public CultureDetailAdapter(Context context, List<CultureDetail> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custculturedetail,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context).load(data.get(position).getUrl()).into(holder.ivCulture);

        final String url = data.get(position).getUrl();
        System.out.println(url);
        holder.ivCulture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showingDialog(url);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivCulture;

        public ViewHolder(View itemView) {
            super(itemView);
            ivCulture = (ImageView)itemView.findViewById(R.id.ivCoverCultureDetailDetail);
        }
    }

    public void showingDialog(String url){
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View layoutinflater = inflater.inflate(R.layout.custlayoutdialog, null);
//        ImageView ivDialog = (ImageView) layoutinflater.findViewById(R.id.ivIamgesDetail);
//
//        AlertDialog.Builder alert = new AlertDialog.Builder(context);
//
//        Glide.with(context).load(url).into(ivDialog);
//        alert.setView(layoutinflater);
//        alert.create().show();

        final Dialog dialog = new Dialog(context);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.custlayoutdialog);
        ImageView ivDialog = (ImageView) dialog.findViewById(R.id.ivIamgesDetail);

            Glide.with(context).load(url).into(ivDialog);
            dialog.show();


    }

}
