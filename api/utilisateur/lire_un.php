<?php
/**
 * Created by PhpStorm.
 * User: Marc-Antoine
 * Date: 08/10/2018
 * Time: 22:52
 */

// headers requis
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Headers: access");
header("Access-Control-Allow-Methods: GET");
header("Access-Control-Allow-Credentials: true");
header('Content-Type: application/json; charset=UTF-8');

require_once '../config/Connexion.php';
require_once '../objets/Utilisateur.php';
require_once '../objets/ReponseAPI.php';

//ini_set('display_errors', 'On');
//error_reporting(E_ALL);

use ProjetMobileAPI\Connexion;
use ProjetMobileAPI\ReponseAPI;
use ProjetMobileAPI\Utilisateur;

// récupération de la connexion à la base de données
$bdd = Connexion::get()->connect();

// création des objet requis
$utilisateur = new Utilisateur($bdd);
$reponseAPI = new ReponseAPI();

// définition de l'id de l'utilisateur à récupérer
$utilisateur->id_utilisateur = isset($_GET['id_utilisateur']) ? $_GET['id_utilisateur'] : die();

// lecture des détails de l'utilisateur à récupérer
$utilisateur->lireUn();

$item_utilisateur['id_utilisateur'] = $utilisateur->id_utilisateur;
$item_utilisateur['nom'] = html_entity_decode($utilisateur->nom);
$item_utilisateur['pseudonyme'] = $utilisateur->pseudonyme;
$item_utilisateur['url_image'] = $utilisateur->url_image;
$item_utilisateur['nombre_mention_aime'] = $utilisateur->nombre_mention_aime;
$item_utilisateur['creation'] = $utilisateur->creation;

array_push($reponseAPI->tab_utilisateur, $item_utilisateur);

$reponseAPI->ajouter_utilisateur();
$reponseAPI->statut = true;

echo $reponseAPI->construire_reponse();