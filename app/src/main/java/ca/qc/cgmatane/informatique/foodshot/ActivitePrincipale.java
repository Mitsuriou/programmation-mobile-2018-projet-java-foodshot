package ca.qc.cgmatane.informatique.foodshot;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

public class ActivitePrincipale extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int DEMANDE_PERMISSION_LOCALISATION = 1;
    public static boolean IS_CONNECTE = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vue_activite_principale);
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

        // TODO : Gérer si l'utilisateur est déjà connecté
        if (!IS_CONNECTE) {
            Intent intentionSeConnecter = new Intent(this, ActiviteConnexion.class);
            startActivity(intentionSeConnecter);
            finish();
        }
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
}
