<?php
/**
 * Created by PhpStorm.
 * User: Marc-Antoine
 * Date: 04/10/2018
 * Time: 21:54
 */

namespace ProjetMobileAPI;


class Utilisateur
{
    private $connexion_bdd;
    private $nom_table = "utilisateur";
    private $limite_taille_recherche = 10;

    // propriétés de l'objet
    public $id_utilisateur;
    public $nom;
    public $pseudonyme;
    public $url_image;
    public $mdp_hash;
    public $nombre_mention_aime;
    public $creation;

    /**
     * constructeur d'Utilisateur
     * @param $bdd connexion à la base de données
     */
    public function __construct($bdd)
    {
        $this->connexion_bdd = $bdd;
    }

    /**
     * lire les utilisateurs
     * @return mixed
     */
    function lire()
    {
        $requete = "SELECT
                u.id_utilisateur, u.nom, u.pseudonyme, u.url_image
            FROM
                " . $this->nom_table . " u
            ORDER BY
                u.creation DESC";

        $stmt = $this->connexion_bdd->prepare($requete);

        $stmt->execute();

        return $stmt;
    }

    /**
     * créer un utilisateur
     * @return bool indiquant l'état d'exécution de la requete
     */
    function creer()
    {
        // requete pour insérer un enregistrement
        $requete = "INSERT INTO
                " . $this->nom_table . "(nom, pseudonyme, url_image, mdp_hash)
            VALUES
                (:nom, :pseudonyme, :url_image, :mdp_hash)";

        // préparation de la requete
        $stmt = $this->connexion_bdd->prepare($requete);

        // sanitize
        $this->nom=htmlspecialchars(strip_tags($this->nom));
        $this->pseudonyme=htmlspecialchars(strip_tags($this->pseudonyme));
        $this->url_image=htmlspecialchars(strip_tags($this->url_image));
        $this->mdp_hash=htmlspecialchars(strip_tags($this->mdp_hash));

        // liaison des variables
        $stmt->bindParam(":nom", $this->nom);
        $stmt->bindParam(":pseudonyme", $this->pseudonyme);
        $stmt->bindParam(":url_image", $this->url_image);
        $stmt->bindParam(":mdp_hash", $this->mdp_hash);

        // exécution de la requete
        if($stmt->execute()){
            return true;
        }

        return false;
    }

    /**
     * lire les données d'un utilisateur
     * utilisé pour pré-remplir le formulaire de modification de l'utilisateur
     */
    function lireUn()
    {
        // requete pour lire un seul enregistrement
        $requete = "SELECT
                count(a.*) as nombre_mention_aime,
                u.nom, u.pseudonyme, u.url_image, u.creation
            FROM
                " . $this->nom_table . " u
                LEFT JOIN
                    publication p
                        ON u.id_utilisateur = p.id_utilisateur
                LEFT JOIN
                    aime a
                        ON p.id_publication = a.id_publication
            WHERE
                u.id_utilisateur = ?
            GROUP BY
                u.pseudonyme, u.nom, u.url_image, u.creation
            LIMIT
                1";

        // préparation de la requete
        $stmt = $this->connexion_bdd->prepare($requete);

        // liaison de l'id de l'utilisateur à modifier
        $stmt->bindParam(1, $this->id_utilisateur);

        // exécution de la requete
        $stmt->execute();

        // récupérer l'enregistrement renvoyé
        $enregistrement = $stmt->fetch(\PDO::FETCH_ASSOC);

        // définir les valeurs comme propriétés de l'objet
        $this->nom = $enregistrement['nom'];
        $this->pseudonyme = $enregistrement['pseudonyme'];
        $this->url_image = $enregistrement['url_image'];
        $this->nombre_mention_aime = $enregistrement['nombre_mention_aime'];
        $this->creation = $enregistrement['creation'];
    }

    /**
     * modifier un utilisateur
     * @return bool indiquant l'état d'exécution de la requete
     */
    function modifier()
    {
        // requete de modification
        $requete = "UPDATE
                " . $this->nom_table . "
            SET
                nom = :nom,
                mdp_hash = :mdp_hash
            WHERE
                id_utilisateur = :id_utilisateur";

        // préparation de la requete
        $stmt = $this->connexion_bdd->prepare($requete);

        // sanitize
        $this->id_utilisateur=htmlspecialchars(strip_tags($this->id_utilisateur));
        $this->nom=htmlspecialchars(strip_tags($this->nom));
        $this->mdp_hash=htmlspecialchars(strip_tags($this->mdp_hash));

        // liaison des variables
        $stmt->bindParam(':nom', $this->nom);
        $stmt->bindParam(':mdp_hash', $this->mdp_hash);
        $stmt->bindParam(':id_utilisateur', $this->id_utilisateur);

        // exécution de la requete
        if($stmt->execute()){
            return true;
        }

        return false;
    }

    /**
     * supprimer un utilisateur
     * @return bool indiquant l'état d'exécution de la requete
     */
    function supprimer()
    {
        // requete de suppression
        $query = "DELETE FROM " . $this->nom_table . " WHERE id_utilisateur = ?";

        // préparation de la requete
        $stmt = $this->connexion_bdd->prepare($query);

        // sanitize
        $this->id_utilisateur=htmlspecialchars(strip_tags($this->id_utilisateur));

        // liaison de l'id de l'utilisateur à supprimer
        $stmt->bindParam(1, $this->id_utilisateur);

        // exécution de la requete
        if($stmt->execute()){
            return true;
        }

        return false;
    }

    /**
     * rechercher un utilisateur
     * @param $mot_clef string à rechercher
     * @return mixed résultat de la requete à la base de données
     */
    function rechercher($mot_clef)
    {
        // requete pour selectionner les utilisateurs correspondants à la recherche
        $requete = "SELECT
                u.id_utilisateur, u.nom, u.pseudonyme
            FROM
                " . $this->nom_table . " u
            WHERE
                u.pseudonyme LIKE ?
            ORDER BY
                u.pseudonyme DESC
            LIMIT
                " . $this->limite_taille_recherche;

        // préparation de la requete
        $stmt = $this->connexion_bdd->prepare($requete);

        // sanitize
        $mot_clef=htmlspecialchars(strip_tags($mot_clef));
        $mot_clef = "%{$mot_clef}%";

        // liaison du mot-clef de l'utilisateur à rechercher
        $stmt->bindParam(1, $mot_clef);

        // exécution de la requete
        $stmt->execute();

        return $stmt;
    }

    /**
     * lire les données d'un utilisateur
     * utilisé pour pré-remplir le formulaire de modification de l'utilisateur
     */
    function authentifier()
    {
        // requete pour chercher l'utilisateur
        $requete = "SELECT
                count(a.*) as nombre_mention_aime,
                u.id_utilisateur, u.nom, u.url_image, u.mdp_hash, u.creation
            FROM
                " . $this->nom_table . " u
                LEFT JOIN
                    publication p
                        ON u.id_utilisateur = p.id_utilisateur
                LEFT JOIN
                    aime a
                        ON p.id_publication = a.id_publication
            WHERE
                u.pseudonyme = ?
            GROUP BY
                u.id_utilisateur, u.nom, u.url_image, u.mdp_hash, u.creation
            LIMIT
                1";

        // préparation de la requete
        $stmt = $this->connexion_bdd->prepare($requete);

        // sanitize
        $this->id_utilisateur=htmlspecialchars(strip_tags($this->id_utilisateur));

        // liaison du pseudonyme et du mot de passe de l'utilisateur à authentifier
        $stmt->bindParam(1, $this->pseudonyme);

        // exécution de la requete
        $stmt->execute();

        return $stmt;
    }
}