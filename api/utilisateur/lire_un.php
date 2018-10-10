<?php
/**
 * Created by PhpStorm.
 * User: Marc-Antoine
 * Date: 08/10/2018
 * Time: 22:52
 */

// headers requis
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Headers: access");
header("Access-Control-Allow-Methods: GET");
header("Access-Control-Allow-Credentials: true");
header('Content-Type: application/json; charset=UTF-8');

require_once '../config/Connexion.php';
require_once '../objets/Utilisateur.php';

//ini_set('display_errors', 'On');
//error_reporting(E_ALL);

use ProjetMobileAPI\Connexion;
use ProjetMobileAPI\Utilisateur;

// récupération de la connexion à la base de données
$bdd = Connexion::get()->connect();

// création de l'objet utilisateur
$utilisateur = new Utilisateur($bdd);

// définition de l'id de l'utilisateur à récupérer
$utilisateur->id_utilisateur = isset($_GET['id_utilisateur']) ? $_GET['id_utilisateur'] : die();

// lecture des détails de l'utilisateur à récupérer
$utilisateur->lireUn();

// création d'un tableau
$tab_utilisateur = array(
    "id_utilisateur" =>  $utilisateur->id_utilisateur,
    "nom" => html_entity_decode($utilisateur->nom),
    "pseudonyme" => $utilisateur->pseudonyme,
    "mdp_hash" => $utilisateur->mdp_hash,
    "creation" => $utilisateur->creation
);

// conversion au format json
echo json_encode($tab_utilisateur);
?>