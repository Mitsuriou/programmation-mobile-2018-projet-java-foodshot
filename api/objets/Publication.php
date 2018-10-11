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
}