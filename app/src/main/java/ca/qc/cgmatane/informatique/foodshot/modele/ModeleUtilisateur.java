package ca.qc.cgmatane.informatique.foodshot.modele;

public class ModeleUtilisateur {

    private int id_utilisateur;
    private String nom;
    private String pseudonyme;
    private String urlImage;
    private int nbrMentionAime;
    private String creation;

    public ModeleUtilisateur(int id_utilisateur, String nom, String pseudonyme, String urlImage, int nbrMentionAime, String creation) {
        this.id_utilisateur = id_utilisateur;
        this.nom = nom;
        this.pseudonyme = pseudonyme;
        this.urlImage = urlImage;
        this.nbrMentionAime = nbrMentionAime;
        this.creation = creation;
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

    public String getUrlImage() {
        return urlImage;
    }

    public int getNbrMentionAime() {
        return nbrMentionAime;
    }

    public String getCreation() {
        return creation;
    }
}
