package ca.qc.cgmatane.informatique.myapplication;

import android.app.Activity;
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

    private int id_utilisateur;
    private int page = 1;
    private RecevoirPublicationAPI recevoirPublicationAPI;
    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private int currentItems, totalItems, scrollOutItems;
    private boolean isScrolling = false;
    private RecyclerViewAdapter adapter;
    private ArrayList<Publication> listePublication;
    private int dernierId = 1;
    private ProgressBar progressBar;
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new ThemeColors(this, getClass().getPackage().getName());
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

        if(page>1){
            recevoirPublicationAPI = new RecevoirPublicationAPI(id_utilisateur,page, dernierId);
        }
        else recevoirPublicationAPI = new RecevoirPublicationAPI(id_utilisateur,page);

        try {
            Log.d("ALED", "ALED");
            recevoirPublicationAPI.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        page++;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("SIZEEEEE", "" + recevoirPublicationAPI.getListePublication().size());
                for (int i = 0; i < recevoirPublicationAPI.getListePublication().size(); i++) {
                    Log.d("IDDDDDDDDDDDDD", "" + recevoirPublicationAPI.getListePublication().get(i).getId());
                    adapter.ajouterPublication(recevoirPublicationAPI.getListePublication().get(i));
                    Log.d("CHANGED", "" + adapter.getListePublication().get(adapter.getItemCount() - 1).getURLimage());
                    Log.d("CHANGED", "" + adapter.getItemCount());
                    dernierId = recevoirPublicationAPI.getListePublication().get(i).getId();
                    progressBar.setVisibility(View.GONE);
                    adapter.notifyItemInserted(adapter.getItemCount() + 1);
                }

            }
        }, 5000);

    }

}