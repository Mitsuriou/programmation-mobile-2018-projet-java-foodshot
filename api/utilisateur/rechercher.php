<?php
/**
 * Created by PhpStorm.
 * User: Marc-Antoine
 * Date: 10/10/2018
 * Time: 14:09
 */

// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

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

// get keywords
$mot_clef=isset($_GET["s"]) ? $_GET["s"] : "";

// query products
$stmt = $utilisateur->recherche($mot_clef);
$num = $stmt->rowCount();

// check if more than 0 record found
if($num>0){

    // products array
    $tab_utilisateurs=array();
    $tab_utilisateurs["enregistrements"]=array();

    // retrieve our table contents
    // fetch() is faster than fetchAll()
    // http://stackoverflow.com/questions/2770630/pdofetchall-vs-pdofetch-in-a-loop
    while ($enregistrement = $stmt->fetch(PDO::FETCH_ASSOC)){
        // extract row
        // this will make $row['name'] to
        // just $name only
        extract($enregistrement);

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
        array("message" => "Aucun utilisateur trouvé.")
    );
}
?>