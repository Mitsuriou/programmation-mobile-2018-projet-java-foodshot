<?php
/**
 * Created by PhpStorm.
 * User: Marc-Antoine
 * Date: 16/10/2018
 * Time: 08:09
 */

namespace ProjetMobileAPI;

require_once 'ReponseAPI.php';

class Aime
{
    // Connexion à la base de données et nom de la table
    private $connexion_bdd;
    private $nom_table = "aime";

    // propriétés de l'objet
    public $id_utilisateur;
    public $id_publication;

    public $reponseAPI;

    /**
     * Constructeur de Aime.
     * @param $bdd connexion à la base de données
     */
    public function __construct($bdd){
        $this->connexion_bdd = $bdd;
        $this->reponseAPI = new ReponseAPI();
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

        try {
            // exécution de la requete
            $stmt->execute();
        } catch (\PDOException $e) {

            //code d'erreur correspondant à une violation de contrainte de clé primaire
            $SQLSTATE = '23505';
            if ($e->getCode() === $SQLSTATE) {

                // si la mention j'aime existe déjà, informe l'utilisateur
                $item_message['code'] = 0;
                $item_message['type'] = "erreur";
                $item_message['message'] = "La mention j'aime existe déjà";

                array_push($this->reponseAPI->tab_message, $item_message);
            } else {
                throw $e;
            }

            return false;
        }

        return true;
    }
}