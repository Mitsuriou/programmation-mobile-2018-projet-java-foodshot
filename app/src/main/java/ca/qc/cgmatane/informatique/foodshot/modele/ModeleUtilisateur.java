package ca.qc.cgmatane.informatique.foodshot.modele;

public class ModeleUtilisateur {

    private int id_utilisateur;
    private String nom;
    private String pseudonyme;

    public ModeleUtilisateur(int id_utilisateur, String nom, String pseudonyme) {
        this.id_utilisateur = id_utilisateur;
        this.nom = nom;
        this.pseudonyme = pseudonyme;
    }

    public int getId_utilisateur() {
        return id_utilisateur;
    }

    public String getNom() {
        return nom;
    }

    public String getPseudonyme() {
        return pseudonyme;
    }
}
