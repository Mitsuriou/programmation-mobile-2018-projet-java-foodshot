<?php
/**
 * Created by PhpStorm.
 * User: Marc-Antoine
 * Date: 10/10/2018
 * Time: 18:27
 */

namespace ProjetMobileAPI;


class Publication
{
    // Connexion à la base de données et nom de la table
    private $connexion_bdd;
    private $nom_table = "publication";

    // propriétés de l'objet
    public $id_publication;
    public $titre;
    public $description;
    public $url_image;
    public $latitude;
    public $longitude;
    public $mention_j_aime;
    public $id_utilisateur;
    public $pseudonyme_utilisateur;
    public $url_image_utilisateur;
    public $creation;

    /**
     * Constructeur de Publication.
     * @param $bdd connexion à la base de données
     */
    public function __construct($bdd){
        $this->connexion_bdd = $bdd;
    }

    /**
     * lire les publications
     * @return mixed
     */
    function lire()
    {
        $requete = "SELECT
                p.id_publication, p.titre, p.description, p.url_image, p.latitude, p.longitude, p.id_utilisateur, p.creation
            FROM
                " . $this->nom_table . " p
            ORDER BY
                p.creation DESC";

        $stmt = $this->connexion_bdd->prepare($requete);

        $stmt->execute();

        return $stmt;
    }

    // read products with pagination
    public function lirePagination($numero_enregistrement_debut, $enregistrements_par_page){

        // select query
        $requete = "SELECT
                u.pseudonyme as pseudonyme_utilisateur, u.url_image as url_image_utilisateur,
                p.id_publication, p.titre, p.description, p.url_image, p.latitude, p.longitude, p.id_utilisateur, p.creation
            FROM
                " . $this->nom_table . " p
                LEFT JOIN
                    utilisateur u
                        ON p.id_utilisateur = u.id_utilisateur
            ORDER BY p.creation DESC
            LIMIT ?
            OFFSET ?";

        // prepare query statement
        $stmt = $this->connexion_bdd->prepare($requete);

        // bind variable values
        $stmt->bindParam(1, $enregistrements_par_page, \PDO::PARAM_INT);
        $stmt->bindParam(2, $numero_enregistrement_debut, \PDO::PARAM_INT);

        // execute query
        $stmt->execute();

        // return values from database
        return $stmt;
    }

    // used for paging products
    public function conter(){
        $requete = "SELECT COUNT(*) as enregistrements_totaux FROM " . $this->nom_table;

        $stmt = $this->connexion_bdd->prepare( $requete );
        $stmt->execute();
        $enregistrement = $stmt->fetch(\PDO::FETCH_ASSOC);

        return $enregistrement['enregistrements_totaux'];
    }
}