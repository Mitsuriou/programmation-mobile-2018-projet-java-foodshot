package ca.qc.cgmatane.informatique.foodshot.modele;

public class ModelePublication {

    private int id;
    private int idUtilisateur;
    private String URLImage;
    private String titre;
    private String URLProfil;
    private String descImage;
    private String nomUtilisateur;
    private boolean jAime;
    private int nbrLike;
    private double latitude;
    private double longitude;
    private String dateCreation;

    public ModelePublication(int id, String titre, String descImage, String URLImage, double latitude, double longitude, boolean jAime, int nbrLike, int idUtilisateur, String nomUtilisateur, String URLProfil, String dateCreation) {
        this.id = id;
        this.URLImage = URLImage;
        this.titre = titre;
        this.URLProfil = URLProfil;
        this.descImage = descImage;
        this.nomUtilisateur = nomUtilisateur;
        this.latitude = latitude;
        this.longitude = longitude;
        this.jAime = jAime;
        this.idUtilisateur = idUtilisateur;
        this.nbrLike = nbrLike;
        // TODO convertir la string en date
        //this.dateCreation = convertStringToTimestamp(dateCreation);
        this.dateCreation = dateCreation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getURLImage() {
        return URLImage;
    }

    public void setURLImage(String URLImage) {
        this.URLImage = URLImage;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getURLProfil() {
        return URLProfil;
    }

    public void setURLProfil(String URLProfil) {
        this.URLProfil = URLProfil;
    }

    public String getDescImage() {
        return descImage;
    }

    public void setDescImage(String descImage) {
        this.descImage = descImage;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public boolean isJaime() {
        return jAime;
    }

    public void setJaime(boolean j_aime) {
        this.jAime = j_aime;
    }

    public int getNbrLike() {
        return nbrLike;
    }

    public void setNbrLike(int nbrLike) {
        this.nbrLike = nbrLike;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    /*    public static Timestamp convertStringToTimestamp(String str_date) {
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
    }*/

}
