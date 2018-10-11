<?php
/**
 * Created by PhpStorm.
 * User: Marc-Antoine
 * Date: 10/10/2018
 * Time: 19:22
 */

// headers requis
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

require_once '../config/Connexion.php';
require_once '../objets/Publication.php';
require_once '../objets/ReponseAPI.php';

//ini_set('display_errors', 'On');
//error_reporting(E_ALL);

use ProjetMobileAPI\Connexion;
use ProjetMobileAPI\Publication;
use ProjetMobileAPI\ReponseAPI;

// récupération de la connexion à la base de données
$bdd = Connexion::get()->connect();

// création des objet requis
$publication = new Publication($bdd);
$reponseAPI = new ReponseAPI();

// recherche de publications
$stmt = $publication->lire();

// récupération du contenu de la table
while ($enregistrement = $stmt->fetch(PDO::FETCH_ASSOC)) {

    // extraction de l'enregistrement
    extract($enregistrement);

    $item_publication = array(
        "id_publication" => $enregistrement->id_publication,
        "titre" => html_entity_decode($enregistrement->titre),
        "description" => html_entity_decode($enregistrement->description),
        "url_image" => $enregistrement->url_image,
        "latitude" => $enregistrement->latitude,
        "longitude" => $enregistrement->longitude,
        //"nombre_mention_aime" => $enregistrement->nombre_mention_aime,
        "id_utilisateur" => $enregistrement->id_utilisateur,
        //"pseudonyme_utilisateur" => $enregistrement->pseudonyme_utilisateur,
        //"url_image_utilisateur" => $enregistrement->url_image_utilisateur,
        "creation" => $enregistrement->creation
    );

    array_push($reponseAPI->tab_publication, $item_publication);
}

// Ajout d'un message si aucun enregistrement n'a été trouvé
if ($stmt->rowCount() == 0) {
    $item_message = array(
        "code" => 0,
        "type" => "alerte",
        "message" => "Aucune publication n'a été trouvée"
    );

    array_push($reponseAPI->tab_message, $item_message);
}

$reponseAPI->statut = true;
$reponseAPI->ajouter_publication();

echo $reponseAPI->construire_reponse();