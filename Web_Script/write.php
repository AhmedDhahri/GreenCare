<?php
date_default_timezone_set('Africa/Tunis');
$ntemp = $_GET["temp"];
$nhum = $_GET["hum"];
$nsoilm= $_GET["soilm"];
$ndte = date("Y-m-d- -h-i-s-a");

class info{
	public $temp = array(0);
	public $hum = array(0);
	public $soilm = array(0);
	public $dte = array();
}

$i = new info;

if(file_exists("store.json")){
	
	$string = file_get_contents("store.json");
	$i = json_decode($string, true);
	
	
	
	$atemp = $i['temp'];
	$ahum = $i['hum'];
	$asoilm = $i['soilm'];
	$adte = $i['dte'];	
	
	
	$atemp[] = $ntemp;
	$ahum[] = $nhum;
	$asoilm[] = $nsoilm;
	$adte[] = $ndte;
	
	
	$atemp = array_slice($atemp,-100,100);
	$ahum = array_slice($ahum,-100,100);
	$asoilm = array_slice($asoilm,-100,100);
	$adte = array_slice($adte,-100,100);
	
	print_r($atemp);
	print_r($ahum);
	print_r($asoilm);
	print_r($adte);
	
	echo("exists\n");
	
}else{
	$atemp = array($ntemp);
	$ahum = array($nhum);
	$asoilm = array($nsoilm);
	$adte = array($ndte);
}
$i = new info();
$i->temp = $atemp;
$i->hum = $ahum;
$i->soilm = $asoilm;
$i->dte = $adte;

$f = fopen('store.json','w');
fwrite($f,json_encode($i));
fclose($f);


?>