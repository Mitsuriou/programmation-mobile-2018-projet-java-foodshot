package ca.qc.cgmatane.informatique.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
// https://developer.android.com/guide/topics/ui/layout/recyclerview#java

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    // vars
    private ArrayList<String> lNames = new ArrayList<>();
    private ArrayList<String> lURL = new ArrayList<>();
    private ArrayList<String> lUserNames = new ArrayList<>();
    private ArrayList<String> lPP = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG,"onCreate: started");

        initImageBitmaps();

    }

    private void initImageBitmaps(){

        Log.d(TAG,"initImageBitmaps: prepareing bitmaps");

        lURL.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");
        lNames.add("Canadaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        lUserNames.add("@Jackiedu25");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");

        lURL.add("http://flags.fmcdn.net/data/flags/w580/mx.png");
        lNames.add("Mexico");
        lUserNames.add("@jazocoti");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");

        lURL.add("http://flags.fmcdn.net/data/flags/w580/fr.png");
        lNames.add("France");
        lUserNames.add("@mitsurio");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");

        lURL.add("http://flags.fmcdn.net/data/flags/w580/de.png");
        lNames.add("Germany");
        lUserNames.add("@tenam");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");

        lURL.add("http://flags.fmcdn.net/data/flags/w580/au.png");
        lNames.add("Australia");
        lUserNames.add("@xXxDarckBibidu75xXx");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");

        lURL.add("http://flags.fmcdn.net/data/flags/w580/gb.png");
        lNames.add("United Kingdom");
        lUserNames.add("@DIEU");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");

        lURL.add("http://flags.fmcdn.net/data/flags/w580/ar.png");
        lNames.add("Argentina");
        lUserNames.add("@Mafia_Officiel");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");






        lURL.add("http://flags.fmcdn.net/data/flags/w580/ar.png");
        lNames.add("South Africa");
        lUserNames.add("@Womi");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");

        lURL.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");
        lNames.add("Spain");
        lUserNames.add("@yadu");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");

        lURL.add("http://flags.fmcdn.net/data/flags/w580/ar.png");
        lNames.add("Russia");
        lUserNames.add("@Rushbee");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");

        lURL.add("http://flags.fmcdn.net/data/flags/w580/ar.png");
        lNames.add("Croatia");
        lUserNames.add("@Salteau!");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");







        lURL.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");
        lNames.add("Canadaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        lUserNames.add("@Jackiedu25");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");

        lURL.add("http://flags.fmcdn.net/data/flags/w580/mx.png");
        lNames.add("Mexico");
        lUserNames.add("@jazocoti");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");

        lURL.add("http://flags.fmcdn.net/data/flags/w580/fr.png");
        lNames.add("France");
        lUserNames.add("@mitsurio");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");

        lURL.add("http://flags.fmcdn.net/data/flags/w580/de.png");
        lNames.add("Germany");
        lUserNames.add("@tenam");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");

        lURL.add("http://flags.fmcdn.net/data/flags/w580/au.png");
        lNames.add("Australia");
        lUserNames.add("@xXxDarckBibidu75xXx");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");

        lURL.add("http://flags.fmcdn.net/data/flags/w580/gb.png");
        lNames.add("United Kingdom");
        lUserNames.add("@DIEU");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");

        lURL.add("http://flags.fmcdn.net/data/flags/w580/ar.png");
        lNames.add("Argentina");
        lUserNames.add("@Mafia_Officiel");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");





        lURL.add("http://flags.fmcdn.net/data/flags/w580/za.png");
        lNames.add("South Africa");
        lUserNames.add("@Womi");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");

        lURL.add("http://flags.fmcdn.net/data/flags/h80/es.png");
        lNames.add("Spain");
        lUserNames.add("@yadu");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");

        lURL.add("http://flags.fmcdn.net/data/flags/h80/ru.png");
        lNames.add("Russia");
        lUserNames.add("@Rushbee");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");

        lURL.add("http://flags.fmcdn.net/data/flags/h80/hr.png");
        lNames.add("Croatia");
        lUserNames.add("@Salteau!");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");







        initRecyclerView();

    }

    private void initRecyclerView(){
        Log.d(TAG,"initRecyclerView: init recyclerView");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,lURL, lNames,lUserNames,lPP);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}