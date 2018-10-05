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
     * Ajoute un nouvel enregistrement dans la table utilisateur
     * @param string $nom
     * @param string $prenom
     * @param string $pseudonyme
     * @param string $mdp_hash
     * @return integer l'id de l'enregistrement inséré
     */
    public function ajouterUtilisateur($nom, $prenom, $pseudonyme, $mdp_hash) {
        // prepare statement pour l'insertion
        $sql = 'INSERT INTO utilisateur(nom,prenom,pseudonyme,mdp_hash) VALUES(:nom,:prenom,:pseudonyme,:mdp_hash)';
        $stmt = $this->pdo->prepare($sql);

        // passer les valeurs au statement
        $stmt->bindValue(':nom', $nom);
        $stmt->bindValue(':prenom', $prenom);
        $stmt->bindValue(':pseudonyme', $pseudonyme);
        $stmt->bindValue(':mdp_hash', $mdp_hash);

        // exécuter le statement d'insertion
        $stmt->execute();

        // retourne l'identifiant généré
        return $this->pdo->lastInsertId();
    }

    /**
     * Constructeur de PostgreSQLPHPAjouterUtilisateur.
     * @param $pdo
     */
    public function __construct($pdo) {
        $this->pdo = $pdo;
    }
}