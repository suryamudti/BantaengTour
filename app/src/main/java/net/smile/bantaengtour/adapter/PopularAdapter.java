package net.smile.bantaengtour.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.smile.bantaengtour.PetaWisataActivity;
import net.smile.bantaengtour.PopularDetailActivity;
import net.smile.bantaengtour.PopulerActivity;
import net.smile.bantaengtour.R;
import net.smile.bantaengtour.entity.ObjekWisata;
import net.smile.bantaengtour.utility.StoredData;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder>{

    private Context context;
    private List<ObjekWisata> data;

    public PopularAdapter(Context context, List<ObjekWisata> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custpopular,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvJudul.setText(data.get(position).getNamaWisata());
        Glide.with(context).load(data.get(position).getCover()).into(holder.ivWisata);

        holder.btnRute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PetaWisataActivity.class);
                i.putExtra("longitude",data.get(position).getLongitude());
                i.putExtra("latitude",data.get(position).getLatitude());
                context.startActivity(i);
            }
        });

        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PopularDetailActivity.class);
                i.putExtra("gambar",data.get(position).getCover());
                i.putExtra("detail",data.get(position).getInfoDetail());
                i.putExtra("id",data.get(position).getId());
                i.putExtra("nama", data.get(position).getNamaWisata());
                i.putExtra("latitude",data.get(position).getLatitude());
                i.putExtra("longitude",data.get(position).getLongitude());
                i.putExtra("alamat",data.get(position).getAlamat());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivWisata;
        TextView tvJudul;
        Button btnDetail,btnRute;

        public ViewHolder(View itemView) {
            super(itemView);

            tvJudul = (TextView)itemView.findViewById(R.id.tvTittlePopular);
            ivWisata = (ImageView)itemView.findViewById(R.id.ivObjWisata);
            btnDetail = (Button) itemView.findViewById(R.id.btnDetailPopular);
            btnRute = (Button) itemView.findViewById(R.id.btnRutePopular);
        }
    }
}
