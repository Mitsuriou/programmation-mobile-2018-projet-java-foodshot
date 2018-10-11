package ca.qc.cgmatane.informatique.myapplication;

public class Publication {

    private int id;
    private String URLimage;
    private String URLprofil;
    private String descImage;
    private String username;
    private String nbLike;

    public Publication(int id, String URLimage, String URLprofil, String descImage, String username, String nbLike) {
        this.id = id;
        this.URLimage = URLimage;
        this.URLprofil = URLprofil;
        this.descImage = descImage;
        this.username = username;
        this.nbLike = nbLike;
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

    public String getNbLike() {
        return nbLike;
    }

}
