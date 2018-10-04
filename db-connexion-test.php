<?php
/**
 * Created by PhpStorm.
 * User: Marc-Antoine
 * Date: 02/10/2018
 * Time: 09:27
 */

//ini_set('display_errors', 'On');
//error_reporting(E_ALL);

require_once 'app/Connection.php';
use ProjetMobile\Connection as Connection;

try {
    Connection::get()->connect();
    echo 'Une connection au serveur de base de données PostgreSQL a été établie avec succès.';
} catch (\PDOException $e) {
    echo $e->getMessage();
}