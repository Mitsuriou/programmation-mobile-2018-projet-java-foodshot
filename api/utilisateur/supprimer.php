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

// récupération de l'id de l'utilisateur
$data = json_decode(file_get_contents("php://input"));

// définition de l'id de l'utilisateur à supprimer
$utilisateur->id_utilisateur = $data->id_utilisateur;

// suppression de l'utilisateur
if ($utilisateur->supprimer()) {

    echo json_encode(
        array(
            "statut" => true,
            "donnee" => [],
            "message" => [
                array(
                    "code" => 0,
                    "type" => "info",
                    "message" => "L'utilisateur a été supprimé avec succès"
                )
            ]
        )
    );
} else {

    // si l'utilisateur n'est pas supprimé, informe l'utilisateur
    echo json_encode(
        array(
            "statut" => false,
            "donnee" => [],
            "message" => [
                array(
                    "code" => 0,
                    "type" => "erreur",
                    "message" => "Impossible de supprimer l'utilisateur"
                )
            ]
        )
    );
}