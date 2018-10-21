package ca.qc.cgmatane.informatique.foodshot;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ca.qc.cgmatane.informatique.foodshot.modele.ModelePublication;
import ca.qc.cgmatane.informatique.foodshot.serveur.AjoutAimeAPI;
import ca.qc.cgmatane.informatique.foodshot.serveur.SupprimerAimeAPI;

public class LongClicListener extends GestureDetector.SimpleOnGestureListener {

    private Context contexte;
    private int idUtilisateur;
    private List<ModelePublication> listePublications;
    private int position;

    public LongClicListener(Context contexte, int idUtilisateur, List<ModelePublication> listePublications, int position) {
        this.contexte = contexte;
        this.idUtilisateur = idUtilisateur;
        this.listePublications = new ArrayList<>();
        this.listePublications.addAll(listePublications);
        this.position = position;
    }

    private void actionAime() {
        if (this.listePublications.get(this.position).isJaime()){
            SupprimerAimeAPI supprimerAimeAPI = new SupprimerAimeAPI(this.idUtilisateur, this.listePublications.get(this.position).getId());
            try {
                supprimerAimeAPI.execute().get();
                this.listePublications.get(this.position).setJaime(false);
                Toast.makeText(contexte, "Vous n'aimez plus la publication.", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            AjoutAimeAPI ajoutAimeAPI = new AjoutAimeAPI(this.idUtilisateur, this.listePublications.get(this.position).getId());
            try {
                ajoutAimeAPI.execute().get();
                this.listePublications.get(this.position).setJaime(true);
                Toast.makeText(contexte, "Vous aimez la publication.", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onLongPress(MotionEvent e) {
        this.actionAime();
    }

}