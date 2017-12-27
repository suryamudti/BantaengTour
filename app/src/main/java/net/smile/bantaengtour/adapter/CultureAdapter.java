package net.smile.bantaengtour.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.smile.bantaengtour.CultureDetailActivity;
import net.smile.bantaengtour.PopularDetailActivity;
import net.smile.bantaengtour.R;
import net.smile.bantaengtour.entity.Culture;

import java.util.List;

public class CultureAdapter extends RecyclerView.Adapter<CultureAdapter.ViewHolder>{

    private Context context;
    private List<Culture> data;

    public CultureAdapter(Context context, List<Culture> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custculture,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {
        holder.tvJudul.setText(data.get(position).getNamaCilture());
        Glide.with(context).load(data.get(position).getCover()).into(holder.ivCulture);

        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, CultureDetailActivity.class);
                i.putExtra("gambar",data.get(position).getCover());
                i.putExtra("detail",data.get(position).getDetailCulture());
                i.putExtra("id",data.get(position).getId());
                i.putExtra("nama", data.get(position).getNamaCilture());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivCulture;
        TextView tvJudul;
        Button btnDetail;

        public ViewHolder(View itemView) {
            super(itemView);
            tvJudul = (TextView)itemView.findViewById(R.id.tvTittleCulture);
            ivCulture = (ImageView)itemView.findViewById(R.id.ivObjCulture);
            btnDetail = (Button) itemView.findViewById(R.id.btnDetailCulture);
        }
    }

}
