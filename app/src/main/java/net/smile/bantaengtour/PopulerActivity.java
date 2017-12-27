package net.smile.bantaengtour;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.smile.bantaengtour.adapter.PopularAdapter;
import net.smile.bantaengtour.entity.ObjekWisata;
import net.smile.bantaengtour.utility.StoredData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PopulerActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    List<ObjekWisata> listDataPopular;
    public PopularAdapter adapter;
    WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_populer);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerPopular);
        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) findViewById(R.id.main_swipe);

        listDataPopular = new ArrayList<ObjekWisata>();
        new Task().execute();
        LinearLayoutManager manager = new LinearLayoutManager(getBaseContext());

        recyclerView.setLayoutManager(manager);

        adapter = new PopularAdapter(recyclerView.getContext(),listDataPopular);
        recyclerView.setAdapter(adapter);

        mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
                // Do work to refresh the list here.
                new Task().execute();
            }
        });
    }

    private class Task extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... voids) {
            listDataPopular.clear();
            OkHttpClient client = new OkHttpClient();
            Request req = new Request.Builder()
                    .url(StoredData.IP+"/bantaengtour/getPopular.php")
                    .build();
            try {

                Response response = client.newCall(req).execute();
                JSONArray array = new JSONArray(response.body().string());

                for (int i=0; i<array.length(); i++){
                    JSONObject object = array.getJSONObject(i);

                    ObjekWisata data = new ObjekWisata(
                            object.getInt("id"), object.getString("nama_wisata"),
                            object.getString("detail"), object.getString("gambar_cover"),
                            object.getString("latitude"), object.getString("longitude"),
                            object.getString("alamat"));

                    listDataPopular.add(data);
                }

            } catch (IOException e) {

                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
                System.out.println("End of Content");
            }
            return null;
//            return new String[0];
        }

        @Override protected void onPostExecute(String[] result) {
            // Call setRefreshing(false) when the list has been refreshed.
            mWaveSwipeRefreshLayout.setRefreshing(false);
            super.onPostExecute(result);
            adapter.notifyDataSetChanged();
        }
    }

}
