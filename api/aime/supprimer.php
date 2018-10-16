<?php
/**
 * Created by PhpStorm.
 * User: Marc-Antoine
 * Date: 16/10/2018
 * Time: 11:31
 */

// headers requis
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

require_once '../config/Connexion.php';
require_once '../objets/Aime.php';

//ini_set('display_errors', 'On');
//error_reporting(E_ALL);

use ProjetMobileAPI\Connexion;
use ProjetMobileAPI\Aime;

// récupération de la connexion à la base de données
$bdd = Connexion::get()->connect();

// création des objet requis
$aime = new Aime($bdd);

// récupération des données transmises
$donnees = json_decode(file_get_contents("php://input"));

// définition de l'id de l'utilisateur et de l'id de la publication de la mention j'aime à supprimer
$aime->id_utilisateur = $donnees->id_utilisateur;
$aime->id_publication = $donnees->id_publication;

// suppression de l'utilisateur
if ($aime->supprimer()) {

    $item_message['code'] = 0;
    $item_message['type'] = "info";
    $item_message['message'] = "La mention j'aime a été supprimée avec succès";

    $aime->reponseAPI->statut = true;
} else {

    // si l'utilisateur n'est pas supprimé, informe l'utilisateur
    $item_message['code'] = 0;
    $item_message['type'] = "erreur";
    $item_message['message'] = "Impossible de supprimer la mention j'aime";
}

array_push($aime->reponseAPI->tab_message, $item_message);

echo $aime->reponseAPI->construire_reponse();