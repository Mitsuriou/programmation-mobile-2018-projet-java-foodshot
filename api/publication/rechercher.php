<?php
/**
 * Created by PhpStorm.
 * User: Marc-Antoine
 * Date: 13/10/2018
 * Time: 15:15
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
use ProjetMobileAPI\ReponseAPI;
use ProjetMobileAPI\Publication;

// récupération de la connexion à la base de données
$bdd = Connexion::get()->connect();

// création des objet requis
$publication = new Publication($bdd);
$reponseAPI = new ReponseAPI();

// récupération des coordonnées
$publication->latitude = (isset($_GET["latitude"])) ? $_GET["latitude"] : die();
$publication->longitude = (isset($_GET["longitude"])) ? $_GET["longitude"] : die();

// définition de l'id de l'utilisateur dont on doit vérifier les publications aimées
$id_utilisateur_j_aime = isset($_GET['id_utilisateur']) ? filter_var(
    $_GET['id_utilisateur'],
    FILTER_VALIDATE_INT,
    array(
        'options' => array(
            'min_range' => 1
        )
    )
) : die();

// recherche des publications
$stmt = $publication->rechercher($id_utilisateur_j_aime);

// récupération du contenu de la table
while ($enregistrement = $stmt->fetch(PDO::FETCH_ASSOC)) {

    // extraction de l'enregistrement
    extract($enregistrement);

    $item_publication['id_publication'] = $id_publication;
    $item_publication['titre'] = html_entity_decode($titre);
    $item_publication['description'] = html_entity_decode($description);
    $item_publication['url_image'] = $url_image;
    $item_publication['latitude'] = $latitude;
    $item_publication['longitude'] = $longitude;
    $item_publication['j_aime'] = $j_aime;
    $item_publication['nombre_mention_aime'] = $nombre_mention_aime;
    $item_publication['id_utilisateur'] = $id_utilisateur;
    $item_publication['pseudonyme_utilisateur'] = $pseudonyme_utilisateur;
    $item_publication['url_image_utilisateur'] = $url_image_utilisateur;
    $item_publication['creation'] = $creation;

    array_push($reponseAPI->tab_publication, $item_publication);
}

// Ajout d'un message si aucun enregistrement n'a été trouvé
if ($stmt->rowCount() == 0) {

    $item_message['code'] = 0;
    $item_message['type'] = "alerte";
    $item_message['message'] = "Aucune publication n'a été trouvée";

    array_push($reponseAPI->tab_message, $item_message);
}

$reponseAPI->ajouter_publication();
$reponseAPI->statut = true;

echo $reponseAPI->construire_reponse();