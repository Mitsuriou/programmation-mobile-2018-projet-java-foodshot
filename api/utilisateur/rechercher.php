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

//ini_set('display_errors', 'On');
//error_reporting(E_ALL);

use ProjetMobileAPI\Connexion;
use ProjetMobileAPI\Utilisateur;

// récupération de la connexion à la base de données
$bdd = Connexion::get()->connect();

// création de l'objet utilisateur
$utilisateur = new Utilisateur($bdd);

// récupération du mot-clef
$mot_clef=isset($_GET["pseudonyme"]) ? $_GET["pseudonyme"] : "";

// recherche des utilisateurs
$stmt = $utilisateur->rechercher($mot_clef);
$num = $stmt->rowCount();

// vérification de la présence d'au moins un enregistrement
if($num>0) {

    // tableau d'utilisateurs
    $tab_utilisateurs=array();

    // récupération du contenu de la table
    while ($enregistrement = $stmt->fetch(PDO::FETCH_ASSOC)){
        // extraction de l'enregistrement
        extract($enregistrement);

        $item_utilisateur=array(
            "id_utilisateur" => $id_utilisateur,
            "nom" => html_entity_decode($nom),
            "pseudonyme" => $pseudonyme
        );

        array_push($tab_utilisateurs, $item_utilisateur);
    }

    echo json_encode(
        array(
            "statut" => true,
            "donnee" => [
                "utilisateur" => $tab_utilisateurs
            ],
            "message" => []
        )
    );
} else {

    echo json_encode(
        array(
            "statut" => true,
            "donnee" => [],
            "message" => [
                "alerte" => [
                    array(
                    "code" => 0,
                    "message" => "Aucun utilisateur n'a été trouvé."
                    )
                ]
            ]
        )
    );
}