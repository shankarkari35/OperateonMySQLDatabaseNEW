package chandan.sagar.operateonmysqldatabase;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    //step2*******************************
    private RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    ArrayList<HashMap<String, String>> arrayListNews;
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_SERVICE_NAME = "service_name";
    private static final String KEY_BOOKING_DATE = "booking_date";
    private static final String KEY_BNO = "bno";
    private static final String KEY_BOOKING_STATUS = "booking_status";
    private static final String BASE_URL = "https://shankarsweb35.000webhostapp.com/home_services/";
    /**
     * AsyncTask for updating a movie details
     */
    public String sname, sbno,sdate,sstatus;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new FetchMovieDetailsAsyncTask().execute();

    }
    /**
     * Fetches single movie details from the server
     */
    private class FetchMovieDetailsAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading Movie Details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            initComponents();
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_myreq.php", "GET",null);
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONObject movie;
                if (success == 1) {
                    arrayListNews = new ArrayList<>();
                    movie = jsonObject.getJSONObject(KEY_DATA);
                    sdate = movie.getString(KEY_BOOKING_DATE);
                     sname= movie.getString(KEY_SERVICE_NAME);
                    sbno = movie.getString(KEY_BNO);
                    sstatus = movie.getString(KEY_BOOKING_STATUS);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    //Populate the Edit Texts once the network activity is finished executing
                    // {"movie_name":"Action","genre":"Robot","year":2010,"rating":9}]}
                    populateDetatList();
                }
            });
        }


    }
    /**
     * Checks whether all files are filled. If so then calls UpdateMovieAsyncTask.
     * Otherwise displays Toast message informing one or more fields left empty
     */
    // this is new

    private void initComponents() {
        //Step1******************************************
        recyclerView = findViewById(R.id.recyclerView);
        //Step2******************************************
        mLayoutManager = new LinearLayoutManager(this);
        //Step3*************************************
        recyclerView.setLayoutManager(mLayoutManager);

    }
    private void populateDetatList() {

        if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {

            HashMap<String, String> map = new HashMap<>();
            map.put("movie_name", sname);
            map.put("genre",sdate);
            map.put("year",sbno);
            map.put("rating",sstatus);
            arrayListNews.add(map);
            mAdapter = new HomeListAdapter(MainActivity.this, arrayListNews);
            recyclerView.setAdapter(mAdapter);

        } else {
            Toast.makeText(MainActivity.this,
                    "Unable to connect to internet",
                    Toast.LENGTH_LONG).show();
        }

    }

}


