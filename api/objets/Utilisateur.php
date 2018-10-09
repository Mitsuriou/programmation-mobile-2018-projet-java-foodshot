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
    public $prenom;
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
                u.id_utilisateur, u.nom, u.prenom, u.pseudonyme, u.mdp_hash, u.creation
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
        $query = "INSERT INTO
                " . $this->table_name . "
            SET
                nom=:nom, prenom=:prenom, pseudonyme=:pseudonyme, mdp_hash=:mdp_hash, creation=:creation";

        // prepare query
        $stmt = $this->conn->prepare($query);

        // sanitize
        $this->name=htmlspecialchars(strip_tags($this->nom));
        $this->price=htmlspecialchars(strip_tags($this->prenom));
        $this->description=htmlspecialchars(strip_tags($this->pseudonyme));
        $this->category_id=htmlspecialchars(strip_tags($this->mdp_hash));
        $this->created=htmlspecialchars(strip_tags($this->creation));

        // bind values
        $stmt->bindParam(":nom", $this->nom);
        $stmt->bindParam(":prenom", $this->prenom);
        $stmt->bindParam(":pseudonyme", $this->pseudonyme);
        $stmt->bindParam(":mdp_hash", $this->mdp_hash);
        $stmt->bindParam(":creation", $this->creation);

        // execute query
        if($stmt->execute()){
            return true;
        }

        return false;
    }
}