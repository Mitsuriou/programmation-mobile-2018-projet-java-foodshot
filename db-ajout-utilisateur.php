<?php
/**
 * Created by PhpStorm.
 * User: Marc-Antoine
 * Date: 04/10/2018
 * Time: 18:01
 */

ini_set('display_errors', 'On');
error_reporting(E_ALL);

require_once 'app/Connection.php';
require_once 'app/PostgreSQLPHPAjouterUtilisateur.php';

use ProjetMobile\Connection as Connection;
use ProjetMobile\PostgreSQLPHPAjouterUtilisateur as PostgreSQLPHPAjouterUtilisateur;

try {
    // connect to the PostgreSQL database
    $pdo = Connection::get()->connect();
    //
    $insertDemo = new PostgreSQLPHPAjouterUtilisateur($pdo);

    // insert a stock into the stocks table
    $id = $insertDemo->ajouterUtilisateur('Nom', 'Prenom', 'Pseudonyme', 'MDP');
    echo 'L\'utilisateur a été ajouté avec l\'id ' . $id . '<br>';
} catch (\PDOException $e) {
    echo $e->getMessage();
}