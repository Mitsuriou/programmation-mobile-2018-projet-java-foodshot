<?php
/**
 * Created by PhpStorm.
 * User: Marc-Antoine
 * Date: 10/10/2018
 * Time: 13:49
 */

// headers requis
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

// include database and object file
require_once '../config/Connexion.php';
require_once '../objets/Utilisateur.php';

ini_set('display_errors', 'On');
error_reporting(E_ALL);

use ProjetMobileAPI\Connexion;
use ProjetMobileAPI\Utilisateur;

// get database connection
$bdd = Connexion::get()->connect();

// création de l'objet utilisateur
$utilisateur = new Utilisateur($bdd);

// get product id
$data = json_decode(file_get_contents("php://input"));

// set product id to be deleted
$utilisateur->id_utilisateur = $data->id_utilisateur;

// delete the product
if($utilisateur->supprimer()){
    echo '{';
    echo '"message": "L\'utilisateur a été supprimé."';
    echo '}';
}

// if unable to delete the product
else{
    echo '{';
    echo '"message": "Impossible de supprimer l\'utilisateur."';
    echo '}';
}
?>