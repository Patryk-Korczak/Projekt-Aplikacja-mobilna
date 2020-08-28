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

    public function getItems(){
        $stmt = $this->con->prepare("SELECT Item_ID, Item_Name, Invoice_Number, Location_ID, Location_Name,
            Group_ID, NetValue, GrossValue, Room_ID, Room_Name, Qr_Code FROM items");
        $stmt->execute();
        $stmt -> bind_result($Item_ID, $Item_Name, $Invoice_Number, $Location_ID, $Location_Name,
            $Group_ID, $NetValue, $GrossValue, $Room_ID, $Room_Name, $Qr_Code);
        $items = array();
        while($stmt -> fetch()){
            $temp = array();
            $temp['Item_ID'] = $Item_ID;
            $temp['Item_Name'] = $Item_Name;
            $temp['Invoice_Number'] = $Invoice_Number;
            $temp['Location_ID'] = $Location_ID;
            $temp['Location_Name'] = $Location_Name;
            $temp['Group_ID'] = $Group_ID;
            $temp['NetValue'] = $NetValue;
            $temp['GrossValue'] = $GrossValue;
            $temp['Room_ID'] = $Room_ID;
            $temp['Room_Name'] = $Room_Name;
            //$temp['Item_Image'] = $Item_Image;
            $temp['Qr_Code'] = $Qr_Code;

            array_push($items, $temp);
        }

        return $items;
    }
}