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
    private $conn;
    private $nom_table = "publication";

    // propriétés de l'objet
    public $id_publication;
    public $titre;
    public $description;
    public $url_image;
    public $id_utilisateur;
    public $pseudonyme_utilisateur;
    public $creation;

    /**
     * Constructeur de Publication.
     * @param $bdd connexion à la base de données
     */
    public function __construct($bdd){
        $this->conn = $bdd;
    }
}