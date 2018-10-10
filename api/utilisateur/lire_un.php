<?php
/**
 * Created by PhpStorm.
 * User: Marc-Antoine
 * Date: 08/10/2018
 * Time: 22:52
 */

header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Headers: access");
header("Access-Control-Allow-Methods: GET");
header("Access-Control-Allow-Credentials: true");
header('Content-Type: application/json; charset=UTF-8');

// include database and object files
include_once '../config/Connexion.php';
include_once '../objets/Utilisateur.php';

use ProjetMobileAPI\Connexion;
use ProjetMobileAPI\Utilisateur;

// get database connection
$bdd = Connexion::get()->connect();

// prepare product object
$utilisateur = new Utilisateur($bdd);

// set ID property of product to be edited
$utilisateur->id_utilisateur = isset($_GET['id_utilisateur']) ? $_GET['id_utilisateur'] : die();

// read the details of product to be edited
$utilisateur->lireUn();

// create array
$tab_utilisateur = array(
    "id_utilisateur" =>  $utilisateur->id_utilisateur,
    "nom" => $utilisateur->nom,
    "pseudonyme" => $utilisateur->pseudonyme
);

// make it json format
echo json_encode($tab_utilisateur);
?>