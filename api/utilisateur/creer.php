<?php
/**
 * Created by PhpStorm.
 * User: Marc-Antoine
 * Date: 08/10/2018
 * Time: 22:15
 */

// headers requis
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

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

// récupération des données transmises en POST
$data = json_decode(file_get_contents("php://input"));

// définition des valeurs des propriétés de l'utilisateur
$utilisateur->nom = $data->nom;
$utilisateur->pseudonyme = $data->pseudonyme;
$utilisateur->mdp_hash = $data->mdp_hash;

// création de l'utilisateur
if($utilisateur->creer()){
    echo '{';
    echo '"message": "L\'utilisateur a été créé."';
    echo '}';
}

// si l'utilisateur n'est pas créé, informe l'utilisateur
else{
    echo '{';
    echo '"message": "Impossible de créer l\'utilisateur."';
    echo '}';
}
?>