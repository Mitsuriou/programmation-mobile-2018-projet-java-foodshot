<?php
/**
 * Created by PhpStorm.
 * User: Marc-Antoine
 * Date: 11/10/2018
 * Time: 19:53
 */

//ini_set('display_errors', 1);
//error_reporting(E_ALL);

// url de la page d'accueil
$url_accueil="http://54.37.152.134/api/";

// page donnée dans les paramètres de l'URL, la page par défaut est la une
$page = isset($_GET['page']) ? $_GET['page'] : 1;

// définistion du nombre d'enregistrements par page
$enregistrements_par_page = 10;

// calcul pour la clause LIMIT de la requete
$from_record_num = ($enregistrements_par_page * $page) - $enregistrements_par_page;