<?php

$database = new PDO(
	"mysql:host=mysql-1534568d-elie987654321-5f3e.a.aivencloud.com:27690;dbname=defaultdb",
	"avnadmin",
	"AVNS__jOOMT5ehepiQKSKDOW"
);



$data = $database->query("SELECT * FROM user")->fetchAll();


echo header('Content-Type: application/json; charset=utf-8');
echo json_encode($data, JSON_FORCE_OBJECT);
?>