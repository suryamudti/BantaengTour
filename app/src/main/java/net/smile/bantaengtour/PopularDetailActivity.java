package net.smile.bantaengtour;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.smile.bantaengtour.adapter.CultureDetailAdapter;
import net.smile.bantaengtour.adapter.PopularDetailAdapter;
import net.smile.bantaengtour.entity.CultureDetail;
import net.smile.bantaengtour.entity.ObjekWisataDetail;
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

public class PopularDetailActivity extends AppCompatActivity {

    List<ObjekWisataDetail> listDataPopular;
    public PopularDetailAdapter adapter;
    RecyclerView recyclerView;

    ImageView ivCover;
    TextView tvAlamat, tvNama, tvDetail;

    String cover, detail, alamat, nama, latitude, longitude;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_detail);

        ivCover = (ImageView)findViewById(R.id.ivCoverPopularDetail);
        tvAlamat = (TextView) findViewById(R.id.tvAlamatObjWisataPopularDetail);
        tvDetail = (TextView) findViewById(R.id.tvDetailObjWisataPopularDetail);
        tvNama = (TextView) findViewById(R.id.tvNamaObjWisataPopularDetail);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerPopularDetail);

        cover = getIntent().getStringExtra("gambar");
        detail = getIntent().getStringExtra("detail");
        alamat = getIntent().getStringExtra("alamat");
        latitude = getIntent().getStringExtra("latitude");
        longitude = getIntent().getStringExtra("longitude");
        nama = getIntent().getStringExtra("nama");
        id = getIntent().getIntExtra("id",0);

        getSupportActionBar().setTitle(nama);

        Glide.with(getBaseContext()).load(cover).into(ivCover);
        Typeface face = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Black.ttf");

        tvAlamat.setText(alamat);
        tvDetail.setText(detail);
        tvNama.setText(nama);
        tvNama.setTypeface(face);

        listDataPopular= new ArrayList<ObjekWisataDetail>();

        loadDataPopular(id);

        LinearLayoutManager manager = new LinearLayoutManager(getBaseContext(),LinearLayoutManager.HORIZONTAL,false);

        recyclerView.setLayoutManager(manager);

        adapter = new PopularDetailAdapter(recyclerView.getContext(),listDataPopular);
        recyclerView.setAdapter(adapter);

    }

    public void loadDataPopular(final int id){

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {

            @Override
            protected Void doInBackground(Integer... params) {

                OkHttpClient client = new OkHttpClient();
                Request req = new Request.Builder()
                        .url(StoredData.IP+"/bantaengtour/getPopularDetail.php?id="+id)
                        .build();
                try {

                    Response response = client.newCall(req).execute();
                    JSONArray array = new JSONArray(response.body().string());

                    for (int i=0; i<array.length(); i++){
                        JSONObject object = array.getJSONObject(i);

                        ObjekWisataDetail data = new ObjekWisataDetail(
                                object.getInt("id"), object.getString("url_gambar"));

                        listDataPopular.add(data);
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
