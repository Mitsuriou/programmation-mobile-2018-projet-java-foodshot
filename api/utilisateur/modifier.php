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

// récupération de l'id de l'utilisateur à modifier
$data = json_decode(file_get_contents("php://input"));

// définition de l'id de l'utilisateur à éditer
$utilisateur->id_utilisateur = $data->id_utilisateur;

// définition des valeurs des propriétés de l'utilisateur
$utilisateur->nom = $data->nom;
$utilisateur->mdp_hash = $data->mdp_hash;

// modification de l'utilisateur
if($utilisateur->modifier()){
    echo json_encode(
        array(
            "statut" => true,
            "donnee" => [
                "utilisateur" => array(
                    "id_utilisateur" =>  $utilisateur->id_utilisateur,
                    "nom" => html_entity_decode($utilisateur->nom)
                )
            ],
            "message" => [
                array(
                    "code" => 0,
                    "type" => "info",
                    "message" => "L'utilisateur a été modifié avec succès"
                )
            ]
        )
    );
}

// si l'utilisateur n'est pas modifié, informe l'utilisateur
else{
    echo json_encode(
        array(
            "statut" => false,
            "donnee" => [
                "utilisateur" => array(
                    "id_utilisateur" =>  $data->id_utilisateur,
                    "nom" => $data->nom
                )
            ],
            "message" => [
                array(
                    "code" => 0,
                    "type" => "erreur",
                    "message" => "Impossible de modifier l'utilisateur"
                )
            ]
        )
    );
}