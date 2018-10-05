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
}