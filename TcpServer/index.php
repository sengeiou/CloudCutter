<?php

	require_once __DIR__ . '/vendor/autoload.php';
	use Workerman\Worker;
	use Workerman\Lib\Timer;
	
    $clients=[];
	$connlist=[];

	// #### create socket and listen 1234 port ####
	$tcp_worker = new Worker("tcp://0.0.0.0:6123");

	// 4 processes
	$tcp_worker->count = 4;
	
	$tcp_worker->onWorkerStart = function($worker){
		Timer::add(5, function()use($worker){
            Global $clients; 
		});
	};

	// Emitted when new connection come
	$tcp_worker->onConnect = function($connection)
	{
        echo "New Connection\n";
        
	};

	// Emitted when data received
	$tcp_worker->onMessage = function($connection, $data)
	{
        
	};

	// Emitted when new connection come
	$tcp_worker->onClose = function($connection)
	{
		echo "Connection closed\n";
	};

	Worker::runAll();
	



?>