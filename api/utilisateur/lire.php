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

// include database and object files
include_once '../config/Connexion.php';
include_once '../objets/Utilisateur.php';

use ProjetMobileAPI\Connexion;
use ProjetMobileAPI\Utilisateur;

// instantiate database and product object
$db = Connexion::get()->connect();

// initialize object
$utilisateur = new Utilisateur($db);

// query products
$stmt = $utilisateur->lire();
$num = $stmt->rowCount();

// check if more than 0 record found
if($num>0){

    // products array
    $tab_utilisateurs=array();
    $tab_utilisateurs["enregistrements"]=array();

    // retrieve our table contents
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
        // extract row
        // this will make $row['name'] to
        // just $name only
        extract($row);

        $item_utilisateur=array(
            "id_utilisateur" => $id_utilisateur,
            "nom" => html_entity_decode($nom),
            "prenom" => html_entity_decode($prenom),
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