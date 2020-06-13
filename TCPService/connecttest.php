<?php

    require_once __DIR__ . '/vendor/autoload.php';
	use Workerman\Worker;
	use Workerman\Lib\Timer;
	
	//print_r($_SERVER);


	define('HEARTBEAT_TIME', 55);
	
	$cutterlist=[];
	$connectlist=[];

	// #### create socket and listen 1234 port ####
	$tcp_worker = new Worker("tcp://0.0.0.0:6124");

	// 4 processes
	$tcp_worker->count = 4;
	
	$tcp_worker->onWorkerStart = function($worker){
		
		Timer::add(10, function()use($worker){
			
			foreach($worker->connections as $conn) {
				$getmachineid="5aa5aa00130000bd0d0a";
				//error_log(date("[Y-m-d H:i:s]")."[CONN]".($getmachineid) ."\r\n\r\n\r\n", 3,"conn-".date("YmdH").".log");
				$data=hex2bin($getmachineid);
				//echo $data;
				$conn->send($data);
			}
			
		});
	};

	// Emitted when new connection come
	$tcp_worker->onConnect = function($connection)
	{
		//echo date("Y-m-d H:i:s")."====new comming".$connection->id."\r\n";
		//print_r($connection);
		//$clients[$connection->id]=$connection;
		$getmachineid="5aa5aa00130000bd0d0a";
		error_log(date("[Y-m-d H:i:s]")."[CONN]".("aa") ."\r\n\r\n\r\n", 3,"log-".date("YmdH").".log");
		$data=hex2bin($getmachineid);
		//echo $data;
		$connection->send($data);
		$connectlist[]=$connection;
	};

	// Emitted when data received
	$tcp_worker->onMessage = function($connection, $data)
	{
        
	};

	// Emitted when new connection come
	$tcp_worker->onClose = function($connection)
	{
		error_log(date("[Y-m-d H:i:s]")."[DISCONN]".("kk") ."\r\n\r\n\r\n", 3,"log-".date("YmdH").".log");
	};

	Worker::runAll();
	



?>