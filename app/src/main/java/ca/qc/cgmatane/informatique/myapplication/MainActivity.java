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
// https://developer.android.com/guide/topics/ui/layout/recyclerview#java

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    // vars
    private ArrayList<String> lNames = new ArrayList<>();
    private ArrayList<String> lURL = new ArrayList<>();
    private ArrayList<String> lUserNames = new ArrayList<>();
    private ArrayList<String> lPP = new ArrayList<>();
    private String coeur;
    private ArrayList<String> nbCoeur = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG,"onCreate: started");

        initImageBitmaps();

    }

    private void initImageBitmaps(){

        Log.d(TAG,"initImageBitmaps: prepareing bitmaps");

        coeur="/res/mipmap/ic_launcher/ic_launcher.png";

        lURL.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");
        lNames.add("Canada");
        lUserNames.add("@Jackiedu25");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");
        nbCoeur.add("1");

        lURL.add("http://flags.fmcdn.net/data/flags/w580/mx.png");
        lNames.add("Mexico");
        lUserNames.add("@jazocoti");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");
        nbCoeur.add("0");

        lURL.add("http://flags.fmcdn.net/data/flags/w580/fr.png");
        lNames.add("France!");
        lUserNames.add("@mitsurio");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");
        nbCoeur.add("1,5M");

        lURL.add("http://flags.fmcdn.net/data/flags/w580/de.png");
        lNames.add("Germany");
        lUserNames.add("@tenam");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");
        nbCoeur.add("3.6k");

        lURL.add("http://flags.fmcdn.net/data/flags/w580/au.png");
        lNames.add("Australia");
        lUserNames.add("@xXxDarckBibidu7");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");
        nbCoeur.add("3.6k");

        lURL.add("http://flags.fmcdn.net/data/flags/w580/gb.png");
        lNames.add("United Kingdom");
        lUserNames.add("@DIEU");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");
        nbCoeur.add("3.6k");

        lURL.add("http://flags.fmcdn.net/data/flags/w580/ar.png");
        lNames.add("Argentina");
        lUserNames.add("@Mafia_Officiel");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");
        nbCoeur.add("3.6k");






        lURL.add("http://www.commongroundgroup.net/wp-content/uploads/2011/10/earth-from-space-western-400x400.jpg");
        lNames.add("South Africa");
        lUserNames.add("@Womi");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");
        nbCoeur.add("3.6k");

        lURL.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");
        lNames.add("Spain");
        lUserNames.add("@yadu");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");
        nbCoeur.add("3.6k");

        lURL.add("http://flags.fmcdn.net/data/flags/w580/ar.png");
        lNames.add("Russia");
        lUserNames.add("@Rushbee");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");
        nbCoeur.add("3.6k");

        lURL.add("http://flags.fmcdn.net/data/flags/w580/ar.png");
        lNames.add("Croatia");
        lUserNames.add("@Salteau!");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");
        nbCoeur.add("3.6k");







        lURL.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");
        lNames.add("Canada !");
        lUserNames.add("@Jackiedu25");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");
        nbCoeur.add("1");

        lURL.add("http://flags.fmcdn.net/data/flags/w580/mx.png");
        lNames.add("Mexico");
        lUserNames.add("@jazocoti");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");
        nbCoeur.add("0");

        lURL.add("http://flags.fmcdn.net/data/flags/w580/fr.png");
        lNames.add("France!");
        lUserNames.add("@mitsurio");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");
        nbCoeur.add("11,5M");

        lURL.add("http://flags.fmcdn.net/data/flags/w580/de.png");
        lNames.add("Germany");
        lUserNames.add("@tenam");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");
        nbCoeur.add("3.6k");

        lURL.add("http://flags.fmcdn.net/data/flags/w580/au.png");
        lNames.add("Australia");
        lUserNames.add("@xXxDarckBibidu7");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");
        nbCoeur.add("3.6k");

        lURL.add("http://flags.fmcdn.net/data/flags/w580/gb.png");
        lNames.add("United Kingdom");
        lUserNames.add("@DIEU");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");
        nbCoeur.add("3.6k");

        lURL.add("http://flags.fmcdn.net/data/flags/w580/ar.png");
        lNames.add("Argentina");
        lUserNames.add("@Mafia_Officiel");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");
        nbCoeur.add("3.6k");






        lURL.add("http://www.commongroundgroup.net/wp-content/uploads/2011/10/earth-from-space-western-400x400.jpg");
        lNames.add("South Africa");
        lUserNames.add("@Womi");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");
        nbCoeur.add("3.6k");

        lURL.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");
        lNames.add("Spain");
        lUserNames.add("@yadu");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");
        nbCoeur.add("3.6k");

        lURL.add("http://flags.fmcdn.net/data/flags/w580/ar.png");
        lNames.add("Russia");
        lUserNames.add("@Rushbee");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");
        nbCoeur.add("3.6k");

        lURL.add("http://flags.fmcdn.net/data/flags/w580/ar.png");
        lNames.add("Croatia");
        lUserNames.add("@Salteau!");
        lPP.add("https://cdn.mos.cms.futurecdn.net/FUE7XiFApEqWZQ85wYcAfM.jpg");
        nbCoeur.add("3.6k");







        initRecyclerView();

    }

    private void initRecyclerView(){
        Log.d(TAG,"initRecyclerView: init recyclerView");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,lURL, lNames,lUserNames,lPP,coeur,nbCoeur);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}