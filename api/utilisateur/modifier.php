<?php
/**
 * Created by PhpStorm.
 * User: Marc-Antoine
 * Date: 10/10/2018
 * Time: 12:46
 */

// headers requis
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

require_once '../config/Connexion.php';
require_once '../objets/Utilisateur.php';
require_once '../objets/ReponseAPI.php';

//ini_set('display_errors', 'On');
//error_reporting(E_ALL);

use ProjetMobileAPI\Connexion;
use ProjetMobileAPI\ReponseAPI;
use ProjetMobileAPI\Utilisateur;

// récupération de la connexion à la base de données
$bdd = Connexion::get()->connect();

// création des objet requis
$utilisateur = new Utilisateur($bdd);
$reponseAPI = new ReponseAPI();

// récupération des données de l'utilisateur à modifier
$data = json_decode(file_get_contents("php://input"));

// définition de l'id de l'utilisateur à éditer
$utilisateur->id_utilisateur = $data->id_utilisateur;

// définition des valeurs des propriétés de l'utilisateur
$utilisateur->nom = $data->nom;
$utilisateur->mdp_hash = $data->mdp_hash;

// modification de l'utilisateur
if ($utilisateur->modifier()) {

    $item_message['code'] = 0;
    $item_message['type'] = "info";
    $item_message['message'] = "L'utilisateur a été modifié avec succès";

    $reponseAPI->statut = true;
} else {

    // si l'utilisateur n'est pas modifié, informe l'utilisateur
    $item_message['code'] = 0;
    $item_message['type'] = "erreur";
    $item_message['message'] = "Impossible de modifier l'utilisateur";
}

array_push($reponseAPI->tab_message, $item_message);

echo $reponseAPI->construire_reponse();