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

        // query to insert record
        $requete = "INSERT INTO
                " . $this->nom_table . "(nom, pseudonyme, mdp_hash, creation)
            VALUES
                (:nom, :pseudonyme, :mdp_hash, :creation)";

        // prepare query
        $stmt = $this->conn->prepare($requete);

        // sanitize
        $this->nom=htmlspecialchars(strip_tags($this->nom));
        $this->pseudonyme=htmlspecialchars(strip_tags($this->pseudonyme));
        $this->mdp_hash=htmlspecialchars(strip_tags($this->mdp_hash));
        $this->creation=htmlspecialchars(strip_tags($this->creation));

        // bind values
        $stmt->bindParam(":nom", $this->nom);
        $stmt->bindParam(":pseudonyme", $this->pseudonyme);
        $stmt->bindParam(":mdp_hash", $this->mdp_hash);
        $stmt->bindParam(":creation", $this->creation);

        // execute query
        if($stmt->execute()){
            return true;
        }

        return false;
    }

    // used when filling up the update product form
    function lireUn(){

        // query to read single record
        $requete = "SELECT
                u.id_utilisateur, u.nom, u.pseudonyme, u.mdp_hash, u.creation
            FROM
                " . $this->nom_table . " u
            WHERE
                u.id_utilisateur = ?
            LIMIT
                1";

        // prepare query statement
        $stmt = $this->conn->prepare($requete);

        // bind id of product to be updated
        $stmt->bindParam(1, $this->id_utilisateur);

        // execute query
        $stmt->execute();

        // get retrieved row
        $row = $stmt->fetch(\PDO::FETCH_ASSOC);

        // set values to object properties
        $this->nom = $row['nom'];
        $this->pseudonyme = $row['pseudonyme'];
    }
}