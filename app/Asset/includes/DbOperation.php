<?php

class DbOperation
{
    private $con;

    function __construct()
    {
        require_once dirname(__FILE__) . '/DbConnect.php';
        $db = new DbConnect();
        $this->con = $db->connect();
    }

    public function getUser($login){
        $stmt = $this->con->prepare("SELECT U_Id, UserLogin, UserPassword, User_type, Imie, Nazwisko, Data_Utworzenia, Data_Urodzenia, Wiek  FROM users WHERE UserLogin= '$login' ");
        $stmt->execute();
        $stmt->bind_result($U_Id, $UserLogin, $UserPassword, $User_type, $Imie, $Nazwisko, $Data_Utworzenia, $Data_Urodzenia, $Wiek);
        $stmt->fetch();
        $temp = array();
        $temp['U_Id'] = $U_Id;
        $temp['UserLogin'] = $UserLogin;
        $temp['UserPassword'] = $UserPassword;
        $temp['User_Type'] = $User_type;
        $temp['Imie'] = $Imie;
        $temp['Nazwisko'] = $Nazwisko;
        $temp['Data_Utworzenia'] = $Data_Utworzenia;
        $temp['Data_Urodzenia'] = $Data_Urodzenia;
        $temp['Wiek'] = $Wiek;
        if(is_null($UserLogin)){
            return "Error2";
        }
        return $temp;

    }

    public function getStockNames(){
        $stmt = $this->con->prepare("SELECT Name FROM inwentaryzacje");
        $stmt->execute();
        $stmt->bind_result($name);
        $names = array();
        while($stmt->fetch()){
            array_push($names, $name);
        }
        return $names;

    }
}