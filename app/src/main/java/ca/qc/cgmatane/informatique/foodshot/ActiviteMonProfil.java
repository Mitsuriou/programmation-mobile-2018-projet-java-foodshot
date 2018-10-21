package ca.qc.cgmatane.informatique.foodshot;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import ca.qc.cgmatane.informatique.foodshot.constantes.Constantes;
import ca.qc.cgmatane.informatique.foodshot.modele.ModelePublication;
import ca.qc.cgmatane.informatique.foodshot.serveur.RecevoirPublicationAPI;

public class ActiviteMonProfil extends AppCompatActivity {

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
            setTheme(R.style.AppTheme);
        }
        else {
            setTheme(R.style.AppThemeNoir);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_activite_mon_profil);

        this.page = 1;
        this.dernierId = 1;
        this.isScrolling = false;

        this.preferencesPartageesGenerales = getSharedPreferences(Constantes.PREFERENCES_GENERALES, Context.MODE_PRIVATE);
        if (!this.preferencesPartageesGenerales.contains("id_utilisateur")) {
            Intent intentionSeConnecter = new Intent(this, ActiviteConnexion.class);
            startActivity(intentionSeConnecter);
            finish();
        }

        barreProgression = (ProgressBar) findViewById(R.id.progressProfil);
        listePublication = new ArrayList<>();

        initialiserRecyclerView();
    }

    private void initialiserRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_viewProfil);
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
            recevoirPublicationAPI = new RecevoirPublicationAPI(this.preferencesPartageesGenerales.getInt("id_utilisateur", -1), page, dernierId, true);
        } else {
            recevoirPublicationAPI = new RecevoirPublicationAPI(this.preferencesPartageesGenerales.getInt("id_utilisateur", -1), page, true);
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
}
