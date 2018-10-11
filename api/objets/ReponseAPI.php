<?php
/**
 * Created by PhpStorm.
 * User: Marc-Antoine
 * Date: 11/10/2018
 * Time: 15:47
 */

namespace ProjetMobileAPI;


class ReponseAPI
{
    // boolean représentant la réussite ou l'échec de l'opération
    public $statut = false;

    // tableau de donnees
    public $tab_donnee = array();

    // tableau de messages
    public $tab_message = array();

    // tableau d'utilisateurs
    public $tab_utilisateur = array();

    // tableau de publications
    public $tab_publication = array();

    // tableau de la reponse
    private $tab_reponse = array();

    /**
     * Constructeur de ReponseAPI.
     */
    public function __construct()
    {

    }

    function ajouter_utilisateur()
    {
        $this->tab_donnee["utilisateur"] = $this->tab_utilisateur;
    }

    function ajouter_publication()
    {
        $this->tab_donnee["publication"] = $this->tab_publication;
    }

    function construire_reponse()
    {
        $this->tab_reponse["statut"] = $this->statut;
        if (sizeof($this->tab_donnee) > 0) $this->tab_reponse["donnee"] = $this->tab_donnee;
        else $this->tab_reponse["donnee"] = new \stdClass();
        $this->tab_reponse["message"] = $this->tab_message;

        return json_encode($this->tab_reponse);
    }
}