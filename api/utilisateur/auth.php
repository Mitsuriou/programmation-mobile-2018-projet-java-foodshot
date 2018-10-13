<?php
/**
 * Created by PhpStorm.
 * User: Marc-Antoine
 * Date: 12/10/2018
 * Time: 16:12
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

// récupération des données de l'utilisateur à authentifier
$data = json_decode(file_get_contents("php://input"));

// définition du pseudonyme de l'utilisateur à authentifier
$utilisateur->pseudonyme = $data->pseudonyme;

// recherche de l'utilisateur
$stmt = $utilisateur->authentifier();

// Ajout d'un message si aucun enregistrement n'a été trouvé
if ($stmt->rowCount() == 0) {

    $item_message['code'] = 0;
    $item_message['type'] = "alerte";
    $item_message['message'] = "Aucun compte n'existe avec ce pseudonyme";

    array_push($reponseAPI->tab_message, $item_message);
} else {

    // récupérer l'enregistrement renvoyé
    $enregistrement = $stmt->fetch(PDO::FETCH_ASSOC);

    // extraction de l'enregistrement
    extract($enregistrement);

    if ($mdp_hash != $data->mdp_hash) {

        $item_message['code'] = 0;
        $item_message['type'] = "alerte";
        $item_message['message'] = "Le mot de passe est incorrect";

        array_push($reponseAPI->tab_message, $item_message);
    } else {

        $item_utilisateur['id_utilisateur'] = $id_utilisateur;
        $item_utilisateur['nom'] = html_entity_decode($nom);
        $item_utilisateur['pseudonyme'] = $utilisateur->pseudonyme;
        $item_utilisateur['url_image'] = $url_image;
        $item_utilisateur['creation'] = $creation;

        array_push($reponseAPI->tab_utilisateur, $item_utilisateur);

        $item_message['code'] = 0;
        $item_message['type'] = "info";
        $item_message['message'] = "Connexion réussie";

        array_push($reponseAPI->tab_message, $item_message);
    }
}

$reponseAPI->ajouter_utilisateur();
$reponseAPI->statut = true;

echo $reponseAPI->construire_reponse();