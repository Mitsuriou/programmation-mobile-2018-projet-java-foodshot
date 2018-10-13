package ca.qc.cgmatane.informatique.foodshot;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
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
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ca.qc.cgmatane.informatique.foodshot.constantes.Constantes;
import ca.qc.cgmatane.informatique.foodshot.modele.ModelePublication;
import ca.qc.cgmatane.informatique.foodshot.serveur.LireUtilisateurCourantAPI;
import ca.qc.cgmatane.informatique.foodshot.serveur.RecevoirPublicationAPI;

public class ActivitePrincipale extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // RecyclerView
    private int count = 21;
    private int page = 1;
    private RecevoirPublicationAPI recevoirPublicationAPI;
    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private int currentItems, totalItems, scrollOutItems;
    private boolean isScrolling = false;
    private RecyclerViewAdapter adapter;
    private ArrayList<ModelePublication> listePublication;
    private int compteur = 1;
    private ProgressBar progressBar;

    private SharedPreferences preferencesPartagees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_activite_principale);

        preferencesPartagees = getSharedPreferences(Constantes.MES_PREFERENCES, Context.MODE_PRIVATE);
        if (!preferencesPartagees.contains("id_utilisateur")) {
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

        this.mettreAJourHeaderDuDrawer();

        progressBar = (ProgressBar) findViewById(R.id.progress);
        listePublication = new ArrayList<>();

        initRecyclerView();
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
            // TODO
            Toast.makeText(this, "A venir", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.parametres) {
            Intent intentionNaviguerVersParametres = new Intent(this, ActiviteParametres.class);
            startActivity(intentionNaviguerVersParametres);
        } else if (id == R.id.deconnexion) {
            this.deconnexion();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
            recevoirPublicationAPI.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        page ++;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < recevoirPublicationAPI.getListePublication().size()-1; i++) {
                    adapter.ajouterPublication(recevoirPublicationAPI.getListePublication().get(i));
                    count++;
                    progressBar.setVisibility(View.GONE);
                    adapter.notifyItemInserted(adapter.getItemCount() + 1);
                }

            }
        }, 5000);
    }

    private void deconnexion() {
        SharedPreferences preferencesPartagees = getSharedPreferences(Constantes.MES_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editeur = preferencesPartagees.edit();
        editeur.clear();
        editeur.apply();
        editeur.commit();
        Toast.makeText(this, "Déconnexion réussie.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, ActiviteConnexion.class));
    }

    private void mettreProfilAJour() {
        SharedPreferences preferencesPartagees = getSharedPreferences(Constantes.MES_PREFERENCES, Context.MODE_PRIVATE);
        LireUtilisateurCourantAPI lireUtilisateurCourantAPI = new LireUtilisateurCourantAPI(preferencesPartagees.getInt("id_utilisateur", -1));
        try {
            lireUtilisateurCourantAPI.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SharedPreferences.Editor editeur = preferencesPartagees.edit();

        editeur.putString("nom", lireUtilisateurCourantAPI.getNomUtilisateurCourant());
        editeur.putString("url_image", lireUtilisateurCourantAPI.getUrlImageUtilisateurCourant());
        editeur.putInt("nombre_mention_aime", lireUtilisateurCourantAPI.getNbrMentionAimeUtilisateurCourant());
        editeur.apply();
        editeur.commit();
    }

    private void mettreAJourHeaderDuDrawer() {
        this.mettreProfilAJour();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Ajout des informations de l'utilisateur dans le header du drawer
        View vueDrawer = navigationView.getHeaderView(0);
        TextView nomDrawer = (TextView) vueDrawer.findViewById(R.id.drawer_header_nom);
        nomDrawer.setText(preferencesPartagees.getString("nom", "Nom"));

        TextView pseudonymeDrawer = (TextView) vueDrawer.findViewById(R.id.drawer_header_pseudonyme);
        pseudonymeDrawer.setText("@");
        pseudonymeDrawer.append(preferencesPartagees.getString("pseudonyme", "Pseudonyme"));

        TextView nombreMentionAime = (TextView) vueDrawer.findViewById(R.id.drawer_header_nombres_mentions_jaime);
        nombreMentionAime.setText(String.valueOf(preferencesPartagees.getInt("nombre_mention_aime", 999999)));
        nombreMentionAime.append(" Coeurs");
    }

}
