package net.smile.bantaengtour.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.smile.bantaengtour.R;
import net.smile.bantaengtour.entity.CultureDetail;
import net.smile.bantaengtour.entity.ObjekWisataDetail;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by GROUNDMOS on 4/28/2017.
 */

public class PopularDetailAdapter extends RecyclerView.Adapter<PopularDetailAdapter.ViewHolder> {

    private Context context;
    private List<ObjekWisataDetail> data;

    public PopularDetailAdapter(Context context, List<ObjekWisataDetail> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custpopulardetail,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(context).load(data.get(position).getUrl()).into(holder.ivCulture);

        final String url = data.get(position).getUrl();
        holder.ivCulture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(url);
                showingDialog(url);
//                new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE)
//                        .setCustomImage(Drawable.createFromPath(url))
//                        .setContentText(data.get(pos))
//                        .show();
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
            ivCulture = (ImageView)itemView.findViewById(R.id.ivCoverPopularDetailDetail);
        }
    }

    public void showingDialog(String url){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutinflater = inflater.inflate(R.layout.custlayoutdialog, null);
        ImageView ivDialog = (ImageView) layoutinflater.findViewById(R.id.ivIamgesDetail);

        AlertDialog.Builder alert = new AlertDialog.Builder(context);

        Glide.with(context).load(url).into(ivDialog);
        alert.setView(layoutinflater);
        alert.create().show();
    }

}
