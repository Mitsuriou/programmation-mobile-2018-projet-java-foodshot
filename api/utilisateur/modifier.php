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

// include database and object files
require_once '../config/Connexion.php';
require_once '../objets/Utilisateur.php';

//ini_set('display_errors', 'On');
//error_reporting(E_ALL);

use ProjetMobileAPI\Connexion;
use ProjetMobileAPI\Utilisateur;

// get database connection
$bdd = Connexion::get()->connect();

// création de l'objet utilisateur
$utilisateur = new Utilisateur($bdd);

// get id of product to be edited
$data = json_decode(file_get_contents("php://input"));

// set ID property of product to be edited
$utilisateur->id_utilisateur = $data->id_utilisateur;

// set product property values
$utilisateur->nom = $data->nom;
$utilisateur->pseudonyme = $data->pseudonyme;
$utilisateur->mdp_hash = $data->mdp_hash;

// update the product
if($utilisateur->modifier()){
    echo '{';
    echo '"message": "L\'utilisateur a été modifié."';
    echo '}';
}

// if unable to update the product, tell the user
else{
    echo '{';
    echo '"message": "Impossible de modifier l\'utilisateur.."';
    echo '}';
}
?>