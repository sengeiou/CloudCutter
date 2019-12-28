<?php

    require_once __DIR__ . '/vendor/autoload.php';
    require_once __DIR__ . '/config.inc.php';
    require_once __DIR__ . '/mysql.cls.php';
    require_once __DIR__ . '/Cutter.php';
	use Workerman\Worker;
	use Workerman\Lib\Timer;
	
	$cutterlist=[];

	// #### create socket and listen 1234 port ####
	$tcp_worker = new Worker("tcp://0.0.0.0:6123");

	// 4 processes
	$tcp_worker->count = 4;
	
	$tcp_worker->onWorkerStart = function($worker){
		Timer::add(1, function()use($worker){
            Global $cutterlist;
			foreach($cutterlist as $cutter){
                $cutter->queueSend();
            }
		});
		Timer::add(60, function()use($worker){
            Global $cutterlist;
			foreach($cutterlist as $cutter){
                $cutter->syncStatus();
            }
		});
	};

	// Emitted when new connection come
	$tcp_worker->onConnect = function($connection)
	{
		//$clients[$connection->id]=$connection;
		$getmachineid="5aa5aa00130000bd0d0a";
		error_log(date("[Y-m-d H:i:s]")."[SEND]".($getmachineid) ."\r\n\r\n\r\n", 3,"buffer-".date("YmdH").".log");
		$data=hex2bin($getmachineid);
		//echo $data;
		$connection->send($data);
	};

	// Emitted when data received
	$tcp_worker->onMessage = function($connection, $data)
	{
		Global $cutterlist;
        
        $app="APP";
        error_log(date("[Y-m-d H:i:s]")."[RECEPURE]".($data) ."\r\n", 3,"buffer-".date("YmdH").".log");
		if(substr($data,0,strlen($app))==$app){
            $args=explode(",",$data);
            $MACHINEID=$args[1];
            foreach($cutterlist as $mid=> $cutter){
                if($mid==$MACHINEID){
                    $cutter->write($args);
                }
            }
			return;
        }
        $data=bin2hex($data);
        error_log(date("[Y-m-d H:i:s]")."[RECE]".($data) ."\r\n", 3,"buffer-".date("YmdH").".log");
        $machineid=Cutter::GetMachineID($data);
        if($machineid!=""){
            $cutter=new Cutter($machineid,$connection);
            $cutterlist[$machineid]=$cutter;
            error_log(date("[Y-m-d H:i:s]")."[INFO] $machineid comming "."\r\n", 3,"buffer-".date("YmdH").".log");
            $cutter->syncStatus();
            //print_r($cutterlist);
        }
        $atstart="5aa5";
        $atend="0d0a";
        if(substr($data,0,strlen($atstart))==$atstart&&substr($data,-4)==$atend){
            foreach($cutterlist as $mid=> $cutter){
                if($cutter->socketclient==$connection){
                    $cutter->read($data);
                }
            }
        }

        

	};

	// Emitted when new connection come
	$tcp_worker->onClose = function($connection)
	{
		echo "Connection closed\n";
	};

	Worker::runAll();
	



?>