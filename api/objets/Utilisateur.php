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
    // Connexion à la base de données et nom de la table
    private $conn;
    private $nom_table = "utilisateur";

    // propriétés de l'objet
    public $id_utilisateur;
    public $nom;
    public $pseudonyme;
    public $mdp_hash;
    public $creation;

    /**
     * Constructeur d'Utilisateur.
     * @param $bdd connexion à la base de données
     */
    public function __construct($bdd){
        $this->conn = $bdd;
    }

    /**
     * Lire les utilisateurs
     * @return mixed
     */
    function lire(){
        $requete = "SELECT
                u.id_utilisateur, u.nom, u.pseudonyme, u.mdp_hash, u.creation
            FROM
                " . $this->nom_table . " u
            ORDER BY
                u.creation DESC";

        $stmt = $this->conn->prepare($requete);

        $stmt->execute();

        return $stmt;
    }

    // créer un utilisateur
    function creer(){

        // requete pour insérer un enregistrement
        $requete = "INSERT INTO
                " . $this->nom_table . "(nom, pseudonyme, mdp_hash)
            VALUES
                (:nom, :pseudonyme, :mdp_hash)";

        // préparation du statement de la requete
        $stmt = $this->conn->prepare($requete);

        // sanitize
        $this->nom=htmlspecialchars(strip_tags($this->nom));
        $this->pseudonyme=htmlspecialchars(strip_tags($this->pseudonyme));
        $this->mdp_hash=htmlspecialchars(strip_tags($this->mdp_hash));

        // liaison des variables
        $stmt->bindParam(":nom", $this->nom);
        $stmt->bindParam(":pseudonyme", $this->pseudonyme);
        $stmt->bindParam(":mdp_hash", $this->mdp_hash);

        // exécution de la requete
        if($stmt->execute()){
            return true;
        }

        return false;
    }

    // used when filling up the update product form
    function lireUn(){

        // requete pour lire un seul enregistrement
        $requete = "SELECT
                u.id_utilisateur, u.nom, u.pseudonyme, u.mdp_hash, u.creation
            FROM
                " . $this->nom_table . " u
            WHERE
                u.id_utilisateur = ?
            LIMIT
                1";

        // préparation du statement de la requete
        $stmt = $this->conn->prepare($requete);

        // liaison de l'id de l'utilisateur à modifier
        $stmt->bindParam(1, $this->id_utilisateur);

        // exécution de la requete
        $stmt->execute();

        // récupérer l'enregistrement renvoyé
        $enregistrement = $stmt->fetch(\PDO::FETCH_ASSOC);

        // définir les valeurs comme propriétés de l'objet
        $this->nom = $enregistrement['nom'];
        $this->pseudonyme = $enregistrement['pseudonyme'];
        $this->mdp_hash = $enregistrement['mdp_hash'];
        $this->creation = $enregistrement['creation'];
    }

    // modification de l'utilisateur
    function modifier(){

        // requete de modification
        $requete = "UPDATE
                " . $this->nom_table . "
            SET
                nom = :nom,
                pseudonyme = :pseudonyme,
                mdp_hash = :mdp_hash
            WHERE
                id_utilisateur = :id_utilisateur";

        // préparation du statement de la requete
        $stmt = $this->conn->prepare($requete);

        // sanitize
        $this->nom=htmlspecialchars(strip_tags($this->nom));
        $this->pseudonyme=htmlspecialchars(strip_tags($this->pseudonyme));
        $this->mdp_hash=htmlspecialchars(strip_tags($this->mdp_hash));

        // liaison des variables
        $stmt->bindParam(':nom', $this->nom);
        $stmt->bindParam(':pseudonyme', $this->pseudonyme);
        $stmt->bindParam(':mdp_hash', $this->mdp_hash);
        $stmt->bindParam(':id_utilisateur', $this->id_utilisateur);

        // exécution de la requete
        if($stmt->execute()){
            return true;
        }

        return false;
    }
}