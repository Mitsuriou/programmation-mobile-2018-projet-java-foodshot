<?php
/**
 * Created by PhpStorm.
 * User: Marc-Antoine
 * Date: 11/10/2018
 * Time: 20:54
 */

// headers requis
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

require_once '../config/Connexion.php';
require_once '../objets/Publication.php';
require_once '../objets/ReponseAPI.php';

ini_set('display_errors', 'On');
error_reporting(E_ALL);

use ProjetMobileAPI\Connexion;
use ProjetMobileAPI\Publication;
use ProjetMobileAPI\ReponseAPI;

// récupération de la connexion à la base de données
$bdd = Connexion::get()->connect();

// création des objet requis
$publication = new Publication($bdd);
$reponseAPI = new ReponseAPI();

// récupération des données transmises en POST
$data = json_decode(file_get_contents("php://input"));

// définition des valeurs des propriétés de la publication
$publication->titre = $data->titre;
$publication->description = $data->description;
$publication->latitude = $data->latitude;
$publication->longitude = $data->longitude;
$publication->id_utilisateur = $data->id_utilisateur;

$item_message = array();

// création de la publication
if ($publication->creer()) {
    $item_message = array(
        "code" => 0,
        "type" => "info",
        "message" => "La publication a été créée avec succès"
    );
} else {

    // si la publication n'est pas créée, informe l'utilisateur
    $item_message = array(
        "code" => 0,
        "type" => "erreur",
        "message" => "Impossible de créer la publication"
    );
}

array_push($reponseAPI->tab_message, $item_message);

//$reponseAPI->ajouter_publication();
$reponseAPI->statut = true;

echo $reponseAPI->construire_reponse();