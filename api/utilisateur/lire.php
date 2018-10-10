<?php
/**
 * Created by PhpStorm.
 * User: Marc-Antoine
 * Date: 04/10/2018
 * Time: 22:10
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

// recherche des utilisateurs
$stmt = $utilisateur->lire();
$num = $stmt->rowCount();

// vérification de la présence d'au moins un enregistrement
if($num>0){

    // tableau d'utilisateurs
    $tab_utilisateurs=array();
    $tab_utilisateurs["enregistrements"]=array();

    // récupération du contenu de la table
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
        // extraction de l'enregistrement
        extract($row);

        $item_utilisateur=array(
            "id_utilisateur" => $id_utilisateur,
            "nom" => html_entity_decode($nom),
            "pseudonyme" => $pseudonyme
        );

        array_push($tab_utilisateurs["enregistrements"], $item_utilisateur);
    }

    echo json_encode($tab_utilisateurs);
}

else{
    echo json_encode(
        array("message" => "Aucun utilisateur n'a été trouvé.")
    );
}
?>