<?php

    require_once __DIR__ . '/vendor/autoload.php';
	include(__DIR__ ."/phpseclib/Crypt/DES.php");
    require_once __DIR__ . '/config.inc.php';
    require_once __DIR__ . '/mysql.cls.php';
    require_once __DIR__ . '/Cutter.php';
	use Workerman\Worker;
	use Workerman\Lib\Timer;
	
	//print_r($_SERVER);
	
	$cutterlist=[];
	$connectlist=[];

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
		Timer::add(30, function()use($worker){
            Global $cutterlist;
			foreach($cutterlist as $cutter){
                $cutter->syncStatus();
            }
		});
		Timer::add(10, function()use($worker){
            Global $cutterlist,$connectlist;
			
			foreach($connectlist as $conn){
				$b=false;
				foreach($cutterlist as $cutter){
					if($conn==$cutter->socketclient){
						$b=true;
						break;
					}
				}
				if($b==false){
					$getmachineid="5aa5aa00130000bd0d0a";
					//error_log(date("[Y-m-d H:i:s]")."[CONN]".($getmachineid) ."\r\n\r\n\r\n", 3,"conn-".date("YmdH").".log");
					$data=hex2bin($getmachineid);
					//echo $data;
					$conn->send($data);
				}
			}
			
			//echo date("Y-m-d H:i:s").count($cutterlist)."\r\n";
			foreach($cutterlist as $cutter){
				//echo date("Y-m-d H:i:s~").$cutter->machineid."\r\n";
                //有文件，但是已经发送超过10秒了，就删除了
				if(count($cutter->filewritedata)>0){
					//echo date("Y-m-d H:i:s~").$cutter->lastfiletime."-".time()."=".($cutter->lastfiletime-time())."\r\n";
				}
				if(count($cutter->filewritedata)>0){
					//echo date("Y-m-d H:i:s~").$cutter->lastfiletime."-".time()."=".(time()-$cutter->lastfiletime)."\r\n";
					//print_r($cutter->lastfiletime);
				}
                if(count($cutter->filewritedata)>0
                &&(time()-$cutter->lastfiletime>10)){
                    $cutter->filewritedata=[];
                }
            }
		});
	};

	// Emitted when new connection come
	$tcp_worker->onConnect = function($connection)
	{
		global $connectlist;
		$connectlist[$connection->id]=$connection;
		//echo date("Y-m-d H:i:s")."====new comming".$connection->id."\r\n";
		//print_r($connection);
		//$clients[$connection->id]=$connection;
		$getmachineid="5aa5aa00130000bd0d0a";
		//error_log(date("[Y-m-d H:i:s]")."[CONN]".($getmachineid) ."\r\n\r\n\r\n", 3,"conn-".date("YmdH").".log");
		$data=hex2bin($getmachineid);
		//echo $data;
		$connection->send($data);
		$connectlist[]=$connection;
	};

	// Emitted when data received
	$tcp_worker->onMessage = function($connection, $data)
	{
		Global $cutterlist,$connectlist;
        try{
            $app="APP";
            //error_log(date("[Y-m-d H:i:s]")."[RECEPURE]".($data) ."\r\n", 3,"buffer-".date("YmdH").".log");
            if(substr($data,0,strlen($app))==$app){
				//echo $data;
                $args=explode(",",$data);
                $MACHINEID=$args[1];
                $COMM=trim($args[1]);
                if($COMM=='LIST'){
					
                    $list=[];
                    foreach($cutterlist as  $cutter){
                        $name=$cutter->name;
                        $machineid=$cutter->machineid;
                        $device_id=$cutter->device_id;
                        $list[]=$machineid."|".$device_id."|".$name;
                    }
					//print_r($list);
					if(count($list)>0){
						$list=join(",",$list);
					}else{
						$list="NOMACHINECONNECT";
					}
					//echo $list;
					
                    $connection->send($list);
					//$connection->close();
                    return;
                }elseif($COMM=='LISTCONN'){
					
                    $list=[];
                    foreach($connectlist as  $conn){
                        $id=$conn->id;
                        $list[]=$id;
                    }
					//print_r($list);
					if(count($list)>0){
						$list=join(",",$list);
					}else{
						$list="NOCONNECT";
					}
					//echo $list;
					
                    $connection->send($list);
					$connection->close();
                    return;
                }else{
                    foreach($cutterlist as  $cutter){
                        if($cutter->machineid==$MACHINEID){
                            $str=$cutter->write($args);
                            $connection->send($str);
                            return;
                        }
                    }
					echo "nomachine?";
                    $connection->send("ERR|NOMACHINE");
					//$connection->close();
                    return;
                }
            }
            $data=bin2hex($data);
            //error_log(date("[Y-m-d H:i:s]")."[RECE]".($data) ."\r\n", 3,"buffer-".date("YmdH").".log");
            $machineid=Cutter::GetMachineID($data);
            if($machineid!=""){
                $cutter=new Cutter($machineid,$connection);
                $cutterlist[$machineid]=$cutter;
                //error_log(date("[Y-m-d H:i:s]")."[INFO] $machineid comming "."\r\n", 3,"buffer-".date("YmdH").".log");
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

            
            $connection->send("OK");
            return;
        }catch(Exception  $ex){
            $m=$e->getMessage();
            error_log(date("[Y-m-d H:i:s]")."[ERR] $m  "."\r\n", 3,"error-".date("YmdH").".log");
        }
	};

	// Emitted when new connection come
	$tcp_worker->onClose = function($connection)
	{
		Global $cutterlist,$connectlist;
		
		unset($connectlist[$connection->id]);
		foreach($cutterlist as $mid=> $cutter){
                if($cutter->socketclient==$connection){
					echo date("Y-m-d H:i:s")."====out ".$connection->id."\r\n";
                    unset($cutterlist[$mid]);
					return;
                }
            }
	};

	Worker::runAll();
	



?>