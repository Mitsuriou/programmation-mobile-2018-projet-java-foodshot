package ca.qc.cgmatane.informatique.foodshot.modele;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ModelePublication {

    private int id;
    private int id_utilisateur;
    private String URLimage;
    private String titre;
    private String URLprofil;
    private String descImage;
    private String username;
    private boolean j_aime;
    private int  nbLike;
    private double latitude;
    private double longitude;
    private Timestamp dateCreation;

    public ModelePublication(int id, String titre, String descImage, String URLimage, double latitude, double longitude, boolean j_aime, int nbLike, int id_utilisateur, String username, String URLprofil, String dateCreation) {
        this.id = id;
        this.URLimage = URLimage;
        this.titre = titre;
        this.URLprofil = URLprofil;
        this.descImage = descImage;
        this.username = username;
        this.latitude = latitude;
        this.longitude = longitude;
        this.j_aime = j_aime;
        this.id_utilisateur = id_utilisateur;
        this.nbLike = nbLike;
        this.dateCreation = convertStringToTimestamp(dateCreation);
    }

    public int getId() {
        return this.id;
    }

    public String getURLimage() {
        return URLimage;
    }

    public String getURLprofil() {
        return URLprofil;
    }

    public String getDescImage() {
        return descImage;
    }

    public String getUsername() {
        return username;
    }

    public int getNbLike() {
        return nbLike;
    }

    public boolean getJ_aime(){
        return this.j_aime;
    }
    
    public static Timestamp convertStringToTimestamp(String str_date) {
        try {
            DateFormat formatter;
            formatter = new SimpleDateFormat("dd/MM/yyyy");
            // you can change format of date
            Date date = formatter.parse(str_date);
            java.sql.Timestamp timeStampDate = new Timestamp(date.getTime());

            return timeStampDate;
        } catch (ParseException e) {
            System.out.println("Exception :" + e);
            return null;
        }
    }

}
