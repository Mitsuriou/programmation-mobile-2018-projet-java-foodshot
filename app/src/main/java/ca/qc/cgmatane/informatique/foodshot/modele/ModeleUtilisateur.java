package ca.qc.cgmatane.informatique.foodshot.modele;

public class ModeleUtilisateur {

    private int idUtilisateur;
    private String nom;
    private String pseudonyme;
    private String urlImage;
    private int nbrMentionAime;
    private String creation;

    public ModeleUtilisateur(int idUtilisateur, String nom, String pseudonyme, String urlImage, int nbrMentionAime, String creation) {
        this.idUtilisateur = idUtilisateur;
        this.nom = nom;
        this.pseudonyme = pseudonyme;
        this.urlImage = urlImage;
        this.nbrMentionAime = nbrMentionAime;
        // TODO : changer la string en date
        this.creation = creation;
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
