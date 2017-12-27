package net.smile.bantaengtour;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.smile.bantaengtour.adapter.CultureAdapter;
import net.smile.bantaengtour.adapter.CultureDetailAdapter;
import net.smile.bantaengtour.entity.Culture;
import net.smile.bantaengtour.entity.CultureDetail;
import net.smile.bantaengtour.utility.StoredData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CultureDetailActivity extends AppCompatActivity {

    List<CultureDetail> listDataCultures;
    public CultureDetailAdapter adapter;
    RecyclerView recyclerView;

    ImageView ivCover;
    TextView tvNama, tvDetail;

    String cover, detail,  nama;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culture_detail);

        ivCover = (ImageView)findViewById(R.id.ivCoverCultureDetail);
        tvDetail = (TextView) findViewById(R.id.tvDetailCultureDetail);
        Typeface face = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Black.ttf");
//        tvDetail.setTypeface(face);

        tvNama = (TextView) findViewById(R.id.tvNamaCultureDetail);
        tvNama.setTypeface(face);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerCultureDetail);

        cover = getIntent().getStringExtra("gambar");
        detail = getIntent().getStringExtra("detail");
        nama = getIntent().getStringExtra("nama");
        id = getIntent().getIntExtra("id",0);

        System.out.println(id);
        getSupportActionBar().setTitle(nama);

        Glide.with(getBaseContext()).load(cover).into(ivCover);
        tvDetail.setText(detail);
        tvNama.setText(nama);

        listDataCultures = new ArrayList<CultureDetail>();
        loadDataCulture(id);
        LinearLayoutManager manager = new LinearLayoutManager(getBaseContext(),LinearLayoutManager.HORIZONTAL,false);

        recyclerView.setLayoutManager(manager);

        adapter = new CultureDetailAdapter(recyclerView.getContext(),listDataCultures);
        recyclerView.setAdapter(adapter);


    }

    public void loadDataCulture(final int id){
//        progress.setVisibility(View.VISIBLE);
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {

            @Override
            protected Void doInBackground(Integer... params) {

                OkHttpClient client = new OkHttpClient();
                Request req = new Request.Builder()
                        .url(StoredData.IP+"/bantaengtour/getCultureDetail.php?id="+id)
                        .build();
                try {

                    Response response = client.newCall(req).execute();
                    JSONArray array = new JSONArray(response.body().string());

                    for (int i=0; i<array.length(); i++){
                        JSONObject object = array.getJSONObject(i);

                        CultureDetail data = new CultureDetail(
                                object.getInt("id"), object.getString("url_gambar"));

                        listDataCultures.add(data);
                    }

                } catch (IOException e) {

                    e.printStackTrace();
                } catch (JSONException e) {

                    System.out.println("End of Content");
                }
                return null;

            }

            @Override
            protected void onPostExecute(Void aVoid){
                adapter.notifyDataSetChanged();
//                progress.setVisibility(View.GONE);
            }
        };

        task.execute(id);

    }


}
