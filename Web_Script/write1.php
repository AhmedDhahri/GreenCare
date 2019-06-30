<?php
date_default_timezone_set('Africa/Tunis');
$volume = $_GET["V"];
$dte = date("Y-m-d h:i:s a");

class info{
	public $V = 0.0;
	public $D = "";
}

$i = new info();
$i->V = $volume;
$i->D = $dte;

$f = fopen('store1.json','w');
fwrite($f,json_encode($i));
fclose($f);
?>