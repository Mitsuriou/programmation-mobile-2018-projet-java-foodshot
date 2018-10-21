package ca.qc.cgmatane.informatique.foodshot.modele;

public class ModeleUtilisateurRecherche {

    private int idUtilisateur;
    private String nom;
    private String pseudonyme;

    public ModeleUtilisateurRecherche(int idUtilisateur, String nom, String pseudonyme) {
        this.idUtilisateur = idUtilisateur;
        this.nom = nom;
        this.pseudonyme = pseudonyme;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public String getNom() {
        return nom;
    }

    public String getPseudonyme() {
        return pseudonyme;
    }
}
