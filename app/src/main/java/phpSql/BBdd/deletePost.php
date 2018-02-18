<meta charset="utf-8">
<?php

//--Incluimos el archivo en usuarioClass.php
require_once("bbdd.php");

$arrMensaje = array();

if(isset($_POST["id"])){
    $id = $_POST["id"];


    $query = "DELETE FROM `Jugadores` WHERE ID =".$id;
    

    $result = $conn->query ( $query );
    if (isset ( $result ) && $result) {

    }
    else {

	$arrMensaje["estado"] = "error";
	$arrMensaje["mensaje"] = "SE HA PRODUCIDO UN ERROR AL ACCEDER A LA BASE DE DATOS";
	$arrMensaje["error"] = $conn->error;
	$arrMensaje["query"] = $query;

}
$conn->close ();
}
