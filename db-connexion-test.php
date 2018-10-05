<?php
/**
 * Created by PhpStorm.
 * User: Marc-Antoine
 * Date: 02/10/2018
 * Time: 09:27
 */

//ini_set('display_errors', 'On');
//error_reporting(E_ALL);

require_once 'api/config/Connexion.php';
use ProjetMobileAPI\Connexion;

try {
    Connexion::get()->connect();
    echo 'Une connection au serveur de base de données PostgreSQL a été établie avec succès.';
} catch (\PDOException $e) {
    echo $e->getMessage();
}