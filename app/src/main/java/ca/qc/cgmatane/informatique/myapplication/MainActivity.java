package ca.qc.cgmatane.informatique.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private int count = 21;
    private int page = 1;
    private RecevoirPublicationAPI recevoirPublicationAPI;
    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private int currentItems, totalItems, scrollOutItems;
    private boolean isScrolling = false;
    private RecyclerViewAdapter adapter;
    private ArrayList<Publication> listePublication;
    private int compteur = 1;
    private ProgressBar progressBar;
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new ThemeColors(this,getClass().getPackage().getName());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progress);
        listePublication = new ArrayList<>();

        initRecyclerView();
    }


    private void initRecyclerView() {

        recyclerView = findViewById(R.id.recycler_view);
        adapter = new RecyclerViewAdapter(this, listePublication);
        fetchData();
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrollOutItems = manager.findFirstVisibleItemPosition();

                if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
                    isScrolling = false;
                    fetchData();
                }
            }
        });
    }

    public void fetchData() {
        progressBar.setVisibility(View.VISIBLE);
        recevoirPublicationAPI = new RecevoirPublicationAPI(page);
        try {
            Log.d("ALED", "ALED");
            recevoirPublicationAPI.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        page ++;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("SIZEEEEE","" + recevoirPublicationAPI.getListePublication().size());
                for (int i = 0; i < recevoirPublicationAPI.getListePublication().size()-1; i++) {
                    adapter.ajouterPublication(recevoirPublicationAPI.getListePublication().get(i));
                    Log.d("CHANGED", "" + adapter.getListePublication().get(adapter.getItemCount() - 1).getURLimage());
                    Log.d("CHANGED", "" + adapter.getItemCount());
                    count++;
                    progressBar.setVisibility(View.GONE);
                    adapter.notifyItemInserted(adapter.getItemCount() + 1);
                }

            }
        }, 5000);

    }

}