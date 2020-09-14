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
        $stmt -> execute();
        $stmt -> bind_result($U_Id, $UserLogin, $UserPassword, $User_type, $Imie, $Nazwisko, $Data_Utworzenia, $Data_Urodzenia, $Wiek);
        $stmt -> fetch();
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
        $stmt = $this->con->prepare("SELECT Stock_ID, User, Stock_Name, Location_ID, Creation_Date, Date_Load_Inv, Is_Ended, Note FROM stocktaking WHERE Is_Ended = '0' ");
        $stmt -> execute();
        $stmt -> bind_result($Stock_ID, $User, $Stock_Name, $Location_ID, $Creation_Date, $Date_Load_Inv, $Is_Ended, $Note);
        $names = array();
        while($stmt->fetch()){
            $temp = array();
            $temp['Stock_ID'] = $Stock_ID;
            $temp['User'] = $User;
            $temp['Stock_Name'] = $Stock_Name;
            $temp['Location_ID'] = $Location_ID;
            $temp['Creation_Date'] = $Creation_Date;
            $temp['Date_Load_Inv'] = $Date_Load_Inv;
            $temp['Is_Ended'] = $Is_Ended;
            $temp['Note'] = $Note;

            array_push($names, $temp);
        }
        return $names;

    }

    public function getItems($Stock_ID){
        $stmt = $this->con->prepare("SELECT * FROM stocktaking_items WHERE Stock_ID = '$Stock_ID' AND IsChecked = '0' ");
        $stmt -> execute();
        $stmt -> bind_result($Row_ID, $Item_ID, $Item_Name, $Item_Image, $Qr_Code_Item,
            $Room_ID, $Room_Name, $Sys_Amount, $Real_Amount, $IsChecked, $Note, $Stock_ID);
        $items = array();
        while($stmt -> fetch()){
            $temp = array();
            $temp['Row_ID'] = $Row_ID;
            $temp['$Item_ID'] = $Item_ID;
            $temp['Item_Name'] = $Item_Name;
            $temp['Item_Image'] = base64_encode($Item_Image);
            $temp['Qr_Code_Item'] = base64_encode($Qr_Code_Item);
            $temp['Room_ID'] = $Room_ID;
            $temp['Room_Name'] = $Room_Name;
            $temp['Sys_Amount'] = $Sys_Amount;
            $temp['Real_Amount'] = $Real_Amount;
            $temp['IsChecked'] = $IsChecked;
            $temp['Note'] = $Note;
            $temp['Stock_ID'] = $Stock_ID;

            array_push($items, $temp);
        }

        return $items;
    }

    public function getRoomItems($Stock_ID, $Room_ID){
        $stmt = $this->con->prepare("SELECT * FROM stocktaking_items WHERE Stock_ID = '$Stock_ID' AND Room_ID = '$Room_ID' AND IsChecked = 0 ");
        $stmt -> execute();
        $stmt -> bind_result($Row_ID, $Item_ID, $Item_Name, $Item_Image, $Qr_Code_Item,
            $Room_ID, $Room_Name, $Sys_Amount, $Real_Amount, $IsChecked, $Note, $Stock_ID);
        $items = array();
        while($stmt -> fetch()){
            $temp = array();
            $temp['Row_ID'] = $Row_ID;
            $temp['$Item_ID'] = $Item_ID;
            $temp['Item_Name'] = $Item_Name;
            $temp['Item_Image'] = base64_encode($Item_Image);
            $temp['Qr_Code_Item'] = base64_encode($Qr_Code_Item);
            $temp['Room_ID'] = $Room_ID;
            $temp['Room_Name'] = $Room_Name;
            $temp['Sys_Amount'] = $Sys_Amount;
            $temp['Real_Amount'] = $Real_Amount;
            $temp['IsChecked'] = $IsChecked;
            $temp['Note'] = $Note;
            $temp['Stock_ID'] = $Stock_ID;

            array_push($items, $temp);
        }

        return $items;
    }

    public function getLocations(){
        $stmt = $this->con->prepare("SELECT * FROM locations");
        $stmt -> execute();
        $stmt -> bind_result($Location_ID, $Location_Name, $Street_Addres, $Postal_Code, $City, $Location_Image, $QrCode);
        $locations = array();
        while($stmt -> fetch()){
            $temp = array();
            $temp['Location_ID'] = $Location_ID;
            $temp['Location_Name'] = $Location_Name;
            $temp['Street_Addres'] = $Street_Addres;
            $temp['Postal_Code'] = $Postal_Code;
            $temp['City'] = $City;
            $temp['Location_Image'] = base64_encode($Location_Image);
            $temp['QrCode'] = base64_encode($QrCode);

            array_push($locations, $temp);
        }

        return $locations;
    }

    public function getRooms($Location_ID){
        $stmt = $this->con->prepare("SELECT * FROM room WHERE Location_ID = '$Location_ID'");
        $stmt -> execute();
        $stmt -> bind_result($Room_ID, $Room_Name, $QrCode, $Location_ID, $Room_Image);
        $rooms = array();
        while($stmt -> fetch()){
            $temp = array();
            $temp['Room_ID'] = $Room_ID;
            $temp['Room_Name'] = $Room_Name;
            $temp['QrCode'] = base64_encode($QrCode);
            $temp['Location_ID'] = $Location_ID;
            $temp['Room_Image'] = base64_encode($Room_Image);

            array_push($rooms, $temp);
        }

        return $rooms;
    }
	
	public function updateItem($Row_ID, $Real_Amount, $IsChecked, $Note){
		$stmt = $this->con->prepare("UPDATE stocktaking_items SET Real_Amount = ?, IsChecked = ?, Note = ? WHERE Row_ID = ?");
		$stmt->bind_param("iisi", $Real_Amount, $IsChecked, $Note, $Row_ID);
		$stmt -> execute();
		
		return "Done";
	}
	
	public function updateStock($Stock_ID, $User, $Is_Ended){
		$stmt = $this->con->prepare("UPDATE stocktaking SET User = ?, Is_Ended = ? WHERE Stock_ID = ?");
		$stmt->bind_param("sii", $User, $Is_Ended, $Stock_ID);
		$stmt -> execute();
		
		return "Done";
	}
}