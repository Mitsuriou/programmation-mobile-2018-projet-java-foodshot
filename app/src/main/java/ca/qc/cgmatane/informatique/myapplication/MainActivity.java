package ca.qc.cgmatane.informatique.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
// https://developer.android.com/guide/topics/ui/layout/recyclerview#java

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    // vars
    private ArrayList<Publication> listePublication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG,"onCreate: started");

        listePublication = new ArrayList<>();

        initImageBitmaps();

    }

    private void initImageBitmaps(){

        Log.d(TAG,"initImageBitmaps: prepareing bitmaps");



        listePublication.add(new Publication(1
                ,"https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg"
                ,"https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg"
                , "Canada"
                ,"@Jackiedu25"
                ,"1"));

        listePublication.add(new Publication(2
                ,"http://flags.fmcdn.net/data/flags/w580/mx.png"
                ,"https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg"
                , "Mexico"
                ,"@jazocoti"
                ,"0"));

        listePublication.add(new Publication(3
                ,"http://flags.fmcdn.net/data/flags/w580/fr.png"
                ,"https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg"
                , "France!"
                ,"@mitsurio"
                ,"1,5M"));

        listePublication.add(new Publication(4
                ,"http://flags.fmcdn.net/data/flags/w580/de.png"
                ,"https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg"
                , "Germany"
                ,"@tenam"
                ,"3.6k"));

        listePublication.add(new Publication(5
                ,"http://flags.fmcdn.net/data/flags/w580/au.png"
                ,"https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg"
                , "Australia"
                ,"@xXxDarckBibidu7"
                ,"3.6k"));

        listePublication.add(new Publication(6
                ,"http://flags.fmcdn.net/data/flags/w580/gb.png"
                ,"https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg"
                , "United Kingdom"
                ,"@DIEU"
                ,"3.6k"));

        listePublication.add(new Publication(7
                ,"http://flags.fmcdn.net/data/flags/w580/ar.png"
                ,"https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg"
                , "Argentina"
                ,"@Mafia_Officiel"
                ,"3.6k"));

        listePublication.add(new Publication(8
                ,"http://www.commongroundgroup.net/wp-content/uploads/2011/10/earth-from-space-western-400x400.jpg"
                ,"https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg"
                , "South Africa"
                ,"@Womi"
                ,"3.6k"));

        listePublication.add(new Publication(9
                ,"https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg"
                ,"https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg"
                , "Spain"
                ,"@yadu"
                ,"3.6k"));

        listePublication.add(new Publication(10,
                "http://flags.fmcdn.net/data/flags/w580/ar.png"
                ,"https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg"
                , "Russia"
                ,"@Rushbee"
                ,"3.6k"));

        listePublication.add(new Publication(11
                ,"http://flags.fmcdn.net/data/flags/w580/ar.png"
                ,"https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg"
                , "Croatia"
                ,"@Salteau!"
                ,"3.6k"));













        listePublication.add(new Publication(1,
                "https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg"
                ,"https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg"
                , "Canada"
                ,"@Jackiedu25"
                ,"1"));

        listePublication.add(new Publication(12
                ,"http://flags.fmcdn.net/data/flags/w580/mx.png"
                ,"https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg"
                , "Mexico"
                ,"@jazocoti"
                ,"0"));

        listePublication.add(new Publication(13
                ,"http://flags.fmcdn.net/data/flags/w580/fr.png"
                ,"https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg"
                , "France!"
                ,"@mitsurio"
                ,"1,5M"));

        listePublication.add(new Publication(14
                ,"http://flags.fmcdn.net/data/flags/w580/de.png"
                ,"https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg"
                , "Germany"
                ,"@tenam"
                ,"3.6k"));

        listePublication.add(new Publication(15
                ,"http://flags.fmcdn.net/data/flags/w580/au.png"
                ,"https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg"
                , "Australia"
                ,"@xXxDarckBibidu7"
                ,"3.6k"));

        listePublication.add(new Publication(16
                ,"http://flags.fmcdn.net/data/flags/w580/gb.png"
                ,"https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg"
                , "United Kingdom"
                ,"@DIEU"
                ,"3.6k"));

        listePublication.add(new Publication(17
                ,"http://flags.fmcdn.net/data/flags/w580/ar.png"
                ,"https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg"
                , "Argentina"
                ,"@Mafia_Officiel"
                ,"3.6k"));

        listePublication.add(new Publication(18
                ,"http://www.commongroundgroup.net/wp-content/uploads/2011/10/earth-from-space-western-400x400.jpg"
                ,"https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg"
                , "South Africa"
                ,"@Womi"
                ,"3.6k"));

        listePublication.add(new Publication(19
                ,"https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg"
                ,"https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg"
                , "Spain"
                ,"@yadu"
                ,"3.6k"));

        listePublication.add(new Publication(20
                ,"http://flags.fmcdn.net/data/flags/w580/ar.png"
                ,"https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg"
                , "Russia"
                ,"@Rushbee"
                ,"3.6k"));

        listePublication.add(new Publication(21
                ,"http://flags.fmcdn.net/data/flags/w580/ar.png"
                ,"https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg"
                , "Croatia"
                ,"@Salteau!"
                ,"3.6k"));


        initRecyclerView();

    }

    private void initRecyclerView(){
        Log.d(TAG,"initRecyclerView: init recyclerView");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,listePublication);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}