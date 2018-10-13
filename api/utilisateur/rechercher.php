<?php
/**
 * Created by PhpStorm.
 * User: Marc-Antoine
 * Date: 10/10/2018
 * Time: 14:09
 */

// headers requis
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

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

// récupération du mot-clef
$mot_clef = (isset($_GET["pseudonyme"]) and $_GET['pseudonyme'] != "") ? $_GET["pseudonyme"] : die();

// recherche des utilisateurs
$stmt = $utilisateur->rechercher($mot_clef);

// récupération du contenu de la table
while ($enregistrement = $stmt->fetch(PDO::FETCH_ASSOC)) {

    // extraction de l'enregistrement
    extract($enregistrement);

    $item_utilisateur['id_utilisateur'] = $id_utilisateur;
    $item_utilisateur['nom'] = html_entity_decode($nom);
    $item_utilisateur['pseudonyme'] = $pseudonyme;

    array_push($reponseAPI->tab_utilisateur, $item_utilisateur);
}

// Ajout d'un message si aucun enregistrement n'a été trouvé
if ($stmt->rowCount() == 0) {

    $item_message['code'] = 0;
    $item_message['type'] = "alerte";
    $item_message['message'] = "Aucun utilisateur n'a été trouvé";

    array_push($reponseAPI->tab_message, $item_message);
}

$reponseAPI->ajouter_utilisateur();
$reponseAPI->statut = true;

echo $reponseAPI->construire_reponse();