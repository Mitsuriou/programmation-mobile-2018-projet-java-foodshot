package ca.qc.cgmatane.informatique.foodshot;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;
import java.util.ArrayList;

public class ActivitePrincipale extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int DEMANDE_PERMISSION_LOCALISATION = 1;
    public static boolean IS_CONNECTE = true;

    // List
    private List<String> lNames = new ArrayList<>();
    private List<String> lURL = new ArrayList<>();
    private List<String> lUserNames = new ArrayList<>();
    private List<String> lPP = new ArrayList<>();
    private String coeur;
    private List<String> nbCoeur = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_activite_principale);

        // TODO : Gérer si l'utilisateur est déjà connecté
        if (!IS_CONNECTE) {
            Intent intentionSeConnecter = new Intent(this, ActiviteConnexion.class);
            startActivity(intentionSeConnecter);
            finish();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.barre_outils);
        setSupportActionBar(toolbar);

        FloatingActionButton boutonCreerNouvellePublication = (FloatingActionButton) findViewById(R.id.bouton_creer_nouvelle_publication);
        boutonCreerNouvellePublication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentionNaviguerVersVueNouveauPoste = new Intent(getApplicationContext(), ActiviteNouvellePublication.class);
                startActivity(intentionNaviguerVersVueNouveauPoste);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initImageBitmaps();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activite_principale, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            Intent intentionChercherProfil =
                    new Intent(this, ActiviteRechercherProfil.class);
            startActivity(intentionChercherProfil);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.profil) {
            Intent intentionNaviguerVersMonProfil =
                    new Intent(this, ActiviteMonProfil.class);
            startActivity(intentionNaviguerVersMonProfil);
        } else if (id == R.id.aime) {
            Intent intentionNaviguerVersPublicationAimees =
                    new Intent(this, ActivitePublicationsAimees.class);
            startActivity(intentionNaviguerVersPublicationAimees);
        } else if (id == R.id.personnes_suivies) {
            Intent intentionNaviguerVersPersonnesSuivies =
                    new Intent(this, ActivitePersonnesSuivies.class);
            startActivity(intentionNaviguerVersPersonnesSuivies);
        } else if (id == R.id.autour_de_moi) {
            Intent intentionNaviguerVersAutourDeMoi = new Intent(this, ActiviteCarte.class);
            startActivity(intentionNaviguerVersAutourDeMoi);
        } else if (id == R.id.notifications) {
            //TODO
            Toast.makeText(this, "A venir", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.parametres) {
            //TODO
            Toast.makeText(this, "A venir", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.deconnexion) {
            //TODO
            Toast.makeText(this, "A venir", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initImageBitmaps(){
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
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getApplicationContext(),lURL, lNames,lUserNames,lPP,coeur,nbCoeur);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
