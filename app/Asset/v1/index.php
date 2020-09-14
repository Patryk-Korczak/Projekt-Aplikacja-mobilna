<?php

//adding dboperation file
require_once '../includes/DbOperation.php';

//response array
$response = array();

//if a get parameter named op is set we will consider it as an api call
if(isset($_GET['op'])){

    //switching the get op value
    switch($_GET['op']){

        //if it is getUser that means we are fetching the records
        case 'getUser':
            if(isset($_GET['login'])) {
                $db = new DbOperation();
                $response = $db->getUser($_GET['login']);
            }
            break;

        case 'getStockNames':
            $db = new DbOperation();
            $response = $db->getStockNames();
            break;

        case 'getItems':
            if(isset($_GET['Stock_ID'])) {
                $db = new DbOperation();
                $response = $db->getItems($_GET['Stock_ID']);
            }
            break;

        case 'getRoomItems':
            if(isset($_GET['Stock_ID']) & isset($_GET['Room_ID'])) {
                $db = new DbOperation();
                $response = $db->getRoomItems($_GET['Stock_ID'], $_GET['Room_ID']);
            }
            break;

        case 'getLocations':
            $db = new DbOperation();
            $response = $db->getLocations();
            break;

        case 'getRooms':
            if(isset($_GET['Location_ID'])) {
                $db = new DbOperation();
                $response = $db->getRooms($_GET['Location_ID']);
            }
            break;

		case 'updateItem':
			if(isset($_GET['Row_ID']) & isset($_GET['Real_Amount']) & isset($_GET['IsChecked']) & isset($_GET['Note'])) {
				$db = new DbOperation();
				$db->updateItem($_GET['Row_ID'], $_GET['Real_Amount'], $_GET['IsChecked'], $_GET['Note']);
				$response = 'Done';
			}
	    break;
		
		case 'updateStock':
			if(isset($_GET['Stock_ID']) & isset($_GET['User']) &isset($_GET['Is_Ended']))	{
				$db = new DbOperation();
				$db->updateStock($_GET['Stock_ID'], $_GET['User'], $_GET['Is_Ended']);
				$response = 'Done';
			}
		break;
			
        default:
            $response['error'] = true;
            $response['message'] = 'No operation to perform';

    }

}else{
    $response['error'] = false;
    $response['message'] = 'Invalid Request';
}

//displaying the data in json
echo json_encode($response);