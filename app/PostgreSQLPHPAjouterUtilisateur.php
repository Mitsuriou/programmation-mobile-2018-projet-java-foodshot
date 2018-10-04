<?php
/**
 * Created by PhpStorm.
 * User: Marc-Antoine
 * Date: 04/10/2018
 * Time: 17:34
 */

namespace ProjetMobile;


class PostgreSQLPHPAjouterUtilisateur
{
    private $pdo;

    /**
     * insert a new row into the stocks table
     * @param type $nom
     * @param type $prenom
     * @param type $pseudonyme
     * @param type $mdp_hash
     * @return the id of the inserted row
     */
    public function ajouterUtilisateur($nom, $prenom, $pseudonyme, $mdp_hash) {
        // prepare statement for insert
        $sql = 'INSERT INTO utilisateur(nom,prenom,pseudonyme,mdp_hash) VALUES(:nom,:prenom,:pseudonyme,:mdp_hash)';
        $stmt = $this->pdo->prepare($sql);

        // pass values to the statement
        $stmt->bindValue(':nom', $nom);
        $stmt->bindValue(':prenom', $prenom);
        $stmt->bindValue(':pseudonyme', $pseudonyme);
        $stmt->bindValue(':mdp_hash', $mdp_hash);

        // execute the insert statement
        $stmt->execute();

        // return generated id
        return $this->pdo->lastInsertId();
    }

    public function __construct($pdo) {
        $this->pdo = $pdo;
    }
}