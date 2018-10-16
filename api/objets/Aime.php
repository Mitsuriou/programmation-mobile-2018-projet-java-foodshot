<?php
/**
 * Created by PhpStorm.
 * User: Marc-Antoine
 * Date: 16/10/2018
 * Time: 08:09
 */

namespace ProjetMobileAPI;


class Aime
{
    // Connexion à la base de données et nom de la table
    private $connexion_bdd;
    private $nom_table = "aime";

    // propriétés de l'objet
    public $id_utilisateur;
    public $id_publication;

    /**
     * Constructeur de Aime.
     * @param $bdd connexion à la base de données
     */
    public function __construct($bdd){
        $this->connexion_bdd = $bdd;
    }

    /**
     * créer une relation "aime"
     * @return bool indiquant l'état d'exécution de la requete
     */
    function creer()
    {
        // requete pour insérer un enregistrement
        $requete = "INSERT INTO
                " . $this->nom_table . "(id_utilisateur, id_publication)
            VALUES
                (:id_utilisateur, :id_publication)";

        // préparation de la requete
        $stmt = $this->connexion_bdd->prepare($requete);

        // sanitize
        $this->id_utilisateur=htmlspecialchars(strip_tags($this->id_utilisateur));
        $this->id_publication=htmlspecialchars(strip_tags($this->id_publication));

        // liaison des variables
        $stmt->bindParam(":id_utilisateur", $this->id_utilisateur);
        $stmt->bindParam(":id_publication", $this->id_publication);

        // exécution de la requete
        if($stmt->execute()){
            return true;
        }

        return false;
    }
}