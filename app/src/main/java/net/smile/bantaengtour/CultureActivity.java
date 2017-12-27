package net.smile.bantaengtour;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import net.smile.bantaengtour.adapter.CultureAdapter;
import net.smile.bantaengtour.entity.Culture;
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

public class CultureActivity extends AppCompatActivity {

    List<Culture> listDataCultures;
    public CultureAdapter adapter;
    RecyclerView recyclerView;
    WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culture);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerCulture);
        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) findViewById(R.id.main_swipe_culture);

        listDataCultures = new ArrayList<Culture>();
        new Task().execute();
        LinearLayoutManager manager = new LinearLayoutManager(getBaseContext());

        recyclerView.setLayoutManager(manager);

        adapter = new CultureAdapter(recyclerView.getContext(),listDataCultures);
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
            listDataCultures.clear();
            OkHttpClient client = new OkHttpClient();
            Request req = new Request.Builder()
                    .url(StoredData.IP+"/bantaengtour/getCulture.php")
                    .build();
            try {

                Response response = client.newCall(req).execute();
                JSONArray array = new JSONArray(response.body().string());

                for (int i=0; i<array.length(); i++){
                    JSONObject object = array.getJSONObject(i);

                    Culture data = new Culture(
                            object.getInt("id"), object.getString("nama_budaya"),
                            object.getString("detail_budaya"), object.getString("cover"));

                    listDataCultures.add(data);
                }

            } catch (IOException e) {

                e.printStackTrace();
            } catch (JSONException e) {

                System.out.println("End of Content");
            }
            return null;
        }

        @Override protected void onPostExecute(String[] result) {
            // Call setRefreshing(false) when the list has been refreshed.
            mWaveSwipeRefreshLayout.setRefreshing(false);
            super.onPostExecute(result);
            adapter.notifyDataSetChanged();
        }
    }
}
