package ca.qc.cgmatane.informatique.foodshot.modele;

public class ModeleNouvellePublciation {

    private String titre;
    private String description;
    private double latitude;
    private double longitude;
    private int id_utilisateur;

    public ModeleNouvellePublciation(String titre, String description, double latitude, double longitude, int id_utilisateur) {
        this.titre = titre;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.id_utilisateur = id_utilisateur;
    }

    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getId_utilisateur() {
        return id_utilisateur;
    }
}
