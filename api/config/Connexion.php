<?php
/**
 * Created by PhpStorm.
 * User: Marc-Antoine
 * Date: 02/10/2018
 * Time: 09:21
 * Class from: http://www.postgresqltutorial.com/postgresql-php/connect/
 */

namespace ProjetMobileAPI;

/**
 * Représente la Connexion
 */
class Connexion
{

    /**
     * Connexion
     * @var mixed
     */
    private static $conn;

    protected function __construct()
    {

    }

    /**
     * Retourne une instance de l'objet Connexion
     * @return mixed
     */
    public static function get()
    {
        if (null === static::$conn) {
            static::$conn = new static();
        }

        return static::$conn;
    }

    /**
     * Connexion à la base de données et retourne une instance de l'objet \PDO
     * @return \PDO
     * @throws \Exception
     */
    public function connect()
    {

        // Lecture des paramètres dans le fichier de configuration ini
        $params = parse_ini_file('identifiants_bdd.ini');
        if ($params === false) {
            throw new \Exception("Erreur lors de la lecture du fichier de configuration de la base de données");
        }
        // connexion à la base de données postgresql
        $conStr = sprintf("pgsql:host=%s;port=%d;dbname=%s;user=%s;password=%s",
            $params['host'],
            $params['port'],
            $params['database'],
            $params['user'],
            $params['password']);

        $pdo = new \PDO($conStr);
        $pdo->setAttribute(\PDO::ATTR_ERRMODE, \PDO::ERRMODE_EXCEPTION);

        return $pdo;
    }

    private function __clone()
    {

    }

    private function __wakeup()
    {

    }

}