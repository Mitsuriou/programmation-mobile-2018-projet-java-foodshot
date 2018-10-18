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
    public $id_publication;
    public $titre;

    // propriétés de l'objet
    public $description;
    public $url_image;
    public $latitude;
    public $longitude;
    public $nombre_mention_aime;
    public $id_utilisateur;
    public $pseudonyme_utilisateur;
    public $url_image_utilisateur;
    public $creation;
    private $connexion_bdd;
    private $nom_table = "publication";

    /**
     * Constructeur de Publication.
     * @param $bdd connexion à la base de données
     */
    public function __construct($bdd)
    {
        $this->connexion_bdd = $bdd;
    }

    /**
     * lire les publications
     * @return mixed
     */
    function lire()
    {
        $requete = "SELECT
                (SELECT count(*) FROM aime WHERE aime.id_publication = p.id_publication) as nombre_mention_aime,
                u.pseudonyme as pseudonyme_utilisateur, u.url_image as url_image_utilisateur,
                p.id_publication, p.titre, p.description, p.url_image, p.latitude, p.longitude, p.id_utilisateur, p.creation
            FROM
                " . $this->nom_table . " p
                LEFT JOIN
                    utilisateur u
                        ON p.id_utilisateur = u.id_utilisateur
            ORDER BY
                p.creation DESC";

        $stmt = $this->connexion_bdd->prepare($requete);

        $stmt->execute();

        return $stmt;
    }

    /**
     * créer une publication
     * @return bool indiquant l'état d'exécution de la requete
     */
    function creer()
    {
        // requete pour insérer un enregistrement
        $requete = "INSERT INTO
                " . $this->nom_table . "(titre, description, latitude, longitude, id_utilisateur)
            VALUES
                (:titre, :description, :latitude, :longitude, :id_utilisateur)";

        // préparation de la requete
        $stmt = $this->connexion_bdd->prepare($requete);

        // sanitize
        $this->titre = htmlspecialchars(strip_tags($this->titre));
        $this->description = htmlspecialchars(strip_tags($this->description));
        $this->latitude = htmlspecialchars(strip_tags($this->latitude));
        $this->longitude = htmlspecialchars(strip_tags($this->longitude));
        $this->id_utilisateur = htmlspecialchars(strip_tags($this->id_utilisateur));

        // liaison des variables
        $stmt->bindParam(":titre", $this->titre);
        $stmt->bindParam(":description", $this->description);
        $stmt->bindParam(":latitude", $this->latitude);
        $stmt->bindParam(":longitude", $this->longitude);
        $stmt->bindParam(":id_utilisateur", $this->id_utilisateur);

        // exécution de la requete
        if ($stmt->execute()) {
            return true;
        }

        return false;
    }

    /**
     * lire les publications avec pagination
     * @param integer $numero_enregistrement_debut
     * @param integer $enregistrements_par_page
     * @param integer $id_utilisateur sur lequel on doit vérifer la présence d'un j'aime
     * @return mixed
     */
    public function lirePagination($numero_enregistrement_debut, $enregistrements_par_page, $id_utilisateur, $publication_perso)
    {
        $texte_publication_perso = ($publication_perso) ? " WHERE p.id_utilisateur = :id_utilisateur " : " ";
        // requete de selection
        $requete = "SELECT
            (SELECT count(*) FROM aime WHERE aime.id_publication = p.id_publication) as nombre_mention_aime,
            EXISTS(SELECT id_utilisateur, id_publication FROM aime WHERE aime.id_utilisateur = :id_utilisateur AND aime.id_publication = p.id_publication) as j_aime,
            u.pseudonyme as pseudonyme_utilisateur, u.url_image as url_image_utilisateur,
            p.id_publication, p.titre, p.description, p.url_image, p.latitude, p.longitude, p.id_utilisateur, p.creation
        FROM
            " . $this->nom_table . " p
            LEFT JOIN
                utilisateur u
                    ON p.id_utilisateur = u.id_utilisateur"
        . $texte_publication_perso
        . "ORDER BY p.creation DESC
        LIMIT :enregistrements_par_page
        OFFSET :numero_enregistrement_debut";

        // préparation de la requete
        $stmt = $this->connexion_bdd->prepare($requete);

        // sanitize
        $id_utilisateur = htmlspecialchars(strip_tags($id_utilisateur));

        // liaison des variables
        $stmt->bindParam(":id_utilisateur", $id_utilisateur, \PDO::PARAM_INT);
        $stmt->bindParam(":enregistrements_par_page", $enregistrements_par_page, \PDO::PARAM_INT);
        $stmt->bindParam(":numero_enregistrement_debut", $numero_enregistrement_debut, \PDO::PARAM_INT);

        // exécution de la requete
        $stmt->execute();

        return $stmt;
    }

    /**
     * rechercher des publications proches
     * @param $latitude double
     * @param $longitude double
     * @return mixed résultat de la requete à la base de données
     */
    function rechercher()
    {
        // requete pour selectionner les utilisateurs correspondants à la recherche
        $requete = "SELECT
                (SELECT count(*) FROM aime WHERE aime.id_publication = p.id_publication) as nombre_mention_aime,
                u.pseudonyme as pseudonyme_utilisateur, u.url_image as url_image_utilisateur,
                p.id_publication, p.titre, p.description, p.url_image, p.latitude, p.longitude, p.id_utilisateur, p.creation
            FROM
                " . $this->nom_table . " p
                LEFT JOIN
                    utilisateur u
                        ON p.id_utilisateur = u.id_utilisateur
            WHERE
                p.latitude BETWEEN :latitude_min AND :latitude_max AND
                p.longitude BETWEEN :longitude_min AND :longitude_max
            ORDER BY
                p.creation DESC
            LIMIT
                30";

        // préparation de la requete
        $stmt = $this->connexion_bdd->prepare($requete);

        // sanitize
        $this->latitude = htmlspecialchars(strip_tags($this->latitude));
        $this->longitude = htmlspecialchars(strip_tags($this->longitude));

        $latitude_min = $this->latitude - 0.1;
        $latitude_max = $this->latitude + 0.1;
        $longitude_min = $this->longitude - 0.2;
        $longitude_max = $this->longitude + 0.2;

        // liaison des variables
        $stmt->bindParam(":latitude_min", $latitude_min);
        $stmt->bindParam(":latitude_max", $latitude_max);
        $stmt->bindParam(":longitude_min", $longitude_min);
        $stmt->bindParam(":longitude_max", $longitude_max);

        // exécution de la requete
        $stmt->execute();

        return $stmt;
    }
}