<?php
/**
 * Created by PhpStorm.
 * User: Marc-Antoine
 * Date: 04/10/2018
 * Time: 18:01
 */

//ini_set('display_errors', 'On');
//error_reporting(E_ALL);

require_once 'api/config/Connexion.php';
require_once 'app/PostgreSQLPHPAjouterUtilisateur.php';

use ProjetMobileAPI\Connexion;
use ProjetMobile\PostgreSQLPHPAjouterUtilisateur;

try {
    // connexion à la base de données PostgreSQL
    $pdo = Connexion::get()->connect();
    //
    $insertDemo = new PostgreSQLPHPAjouterUtilisateur($pdo);

    // insérer un utilisateur dans la table utilisateur
    $id = $insertDemo->ajouterUtilisateur('Nom', 'Prenom', 'Pseudonyme', 'MDP');
    echo 'L\'utilisateur a été ajouté avec l\'id ' . $id . '<br>';
} catch (\PDOException $e) {
    echo $e->getMessage();
}