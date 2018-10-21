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

    private int page;
    private RecevoirPublicationAPI recevoirPublicationAPI;
    private LinearLayoutManager manager;
    private int objetCourant;
    private int nbrTotalObjets;
    private int objetsHorsEcran;
    private boolean isScrolling;
    private RecyclerViewAdapter adapteur;
    private ArrayList<ModelePublication> listePublication;
    private int dernierId;
    private ProgressBar barreProgression;

    private SharedPreferences preferencesPartageesGenerales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSharedPreferences(Constantes.PREFERENCES_THEME_COULEUR, Context.MODE_PRIVATE).getInt("theme", 1) == 1) {
            setTheme(R.style.AppThemeNoActionBar);
        } else {
            setTheme(R.style.AppThemeNoirNoActionBar);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_activite_principale);

        this.page = 1;
        this.dernierId = 1;
        this.isScrolling = false;

        this.preferencesPartageesGenerales = getSharedPreferences(Constantes.PREFERENCES_GENERALES, Context.MODE_PRIVATE);
        if (!this.preferencesPartageesGenerales.contains("id_utilisateur")) {
            Intent intentionSeConnecter = new Intent(this, ActiviteConnexion.class);
            startActivity(intentionSeConnecter);
            finish();
        }

        Toolbar barreOutils = (Toolbar) findViewById(R.id.barre_outils);
        setSupportActionBar(barreOutils);

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
                this, drawer, barreOutils, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        this.mettreAJourHeaderDuDrawer();

        this.barreProgression = (ProgressBar) findViewById(R.id.progressActivitePrincipale);
        this.listePublication = new ArrayList<>();

        this.initialiserRecyclerView();
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
            // TODO : page de notifications
            Toast.makeText(this, "A venir", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.theme_noir) {
            SharedPreferences preferencesPartageesThemeCouleur =
                    getSharedPreferences(Constantes.PREFERENCES_THEME_COULEUR, Context.MODE_PRIVATE);

            SharedPreferences.Editor editeur =
                    getSharedPreferences(Constantes.PREFERENCES_THEME_COULEUR, Context.MODE_PRIVATE).edit();

            if (preferencesPartageesThemeCouleur.getInt("theme", 1) == 1) {
                editeur.putInt("theme", 2);
                editeur.apply();
                this.recreate();
            } else {
                editeur.putInt("theme", 1);
                editeur.apply();
                this.recreate();
            }
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

    private void initialiserRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        adapteur = new RecyclerViewAdapter(this.preferencesPartageesGenerales.getInt("id_utilisateur", -1), this, listePublication);
        recupererDonnees();
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapteur);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int nouvelEtat) {
                super.onScrollStateChanged(recyclerView, nouvelEtat);
                if (nouvelEtat == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                objetCourant = manager.getChildCount();
                nbrTotalObjets = manager.getItemCount();
                objetsHorsEcran = manager.findFirstVisibleItemPosition();

                if (isScrolling && (objetCourant + objetsHorsEcran == nbrTotalObjets)) {
                    isScrolling = false;
                    recupererDonnees();
                }
            }
        });
    }

    public void recupererDonnees() {
        barreProgression.setVisibility(View.VISIBLE);
        this.preferencesPartageesGenerales =
                getSharedPreferences(Constantes.PREFERENCES_GENERALES, Context.MODE_PRIVATE);

        if (page > 1) {
            recevoirPublicationAPI = new RecevoirPublicationAPI(this.preferencesPartageesGenerales.getInt("id_utilisateur", -1), page, dernierId);
        } else {
            recevoirPublicationAPI = new RecevoirPublicationAPI(this.preferencesPartageesGenerales.getInt("id_utilisateur", -1), page);
        }

        try {
            recevoirPublicationAPI.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (recevoirPublicationAPI.getProgress()) {
            page++;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < recevoirPublicationAPI.getListePublication().size(); i++) {
                        adapteur.ajouterPublication(recevoirPublicationAPI.getListePublication().get(i));
                        dernierId = recevoirPublicationAPI.getListePublication().get(i).getId();
                        barreProgression.setVisibility(View.GONE);
                        adapteur.notifyItemInserted(adapteur.getItemCount() + 1);
                    }

                }
            }, 5000);
        }
    }

    private void deconnexion() {
        this.preferencesPartageesGenerales = getSharedPreferences(Constantes.PREFERENCES_GENERALES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editeur = this.preferencesPartageesGenerales.edit();
        editeur.clear();
        editeur.apply();
        editeur.commit();
        Toast.makeText(this, "Déconnexion réussie.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, ActiviteConnexion.class));
    }

    private void mettreProfilAJour() {
        this.preferencesPartageesGenerales = getSharedPreferences(Constantes.PREFERENCES_GENERALES, Context.MODE_PRIVATE);
        LireUtilisateurCourantAPI lireUtilisateurCourantAPI = new LireUtilisateurCourantAPI(this.preferencesPartageesGenerales.getInt("id_utilisateur", -1));
        try {
            lireUtilisateurCourantAPI.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SharedPreferences.Editor editeur = this.preferencesPartageesGenerales.edit();

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
        nomDrawer.setText(this.preferencesPartageesGenerales.getString("nom", "Nom"));

        TextView pseudonymeDrawer = (TextView) vueDrawer.findViewById(R.id.drawer_header_pseudonyme);
        pseudonymeDrawer.setText("@");
        pseudonymeDrawer.append(this.preferencesPartageesGenerales.getString("pseudonyme", "Pseudonyme"));

        TextView nombreMentionAime = (TextView) vueDrawer.findViewById(R.id.drawer_header_nombres_mentions_jaime);
        nombreMentionAime.setText(String.valueOf(this.preferencesPartageesGenerales.getInt("nombre_mention_aime", 999999)));
        nombreMentionAime.append(" Coeurs");
    }

}
