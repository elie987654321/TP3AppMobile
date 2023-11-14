<?php

$database = new PDO(
	"mysql:host=mysql-1534568d-elie987654321-5f3e.a.aivencloud.com:27690;dbname=defaultdb",
	"avnadmin",
	"AVNS__jOOMT5ehepiQKSKDOW"
);

$rawPostData = file_get_contents("php://input");
$data = json_decode($rawPostData);

$prenom = $data->prenom;
$nom = $data->nom;

echo $nom;
?>
