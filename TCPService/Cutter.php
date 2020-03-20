<?php
define("READSPEED", 0x10);
define("READBLADEPRESS", 0x11);
define("READGEARRATE", 0x12);
define("READMACHINEID", 0x13);
define("READAPINFO", 0x14);
define("READSTAINFO", 0x15);
define("READSPACING", 0x18);
define("READWIDTH", 0x19);

define("SETSPEED", 0x10);
define("SETBLADEPRESS", 0x11);
define("SETGEARRATE", 0x12);
define("SETMACHINEID", 0x13);
define("SETAPINFO", 0x14);
define("SETSTAINFO", 0x15);
define("RESET", 0x16);
define("TRYCUT", 0x17);
define("SETSPACING", 0x18);
define("SETWIDTH", 0x19);

define("READMACHINESTATUS", 0x20);
define("VERSION", 0x21);
define("WRITEFILE", 0x30);
define("CHANGESERVER", 0x1a);



class Cutter
{
    public $socketclient = null;
    public $device_id = 0;
    public $machineid = "";
    public $name = "";
    public function __construct($machineid, $socketclient)
    {
        global $dbmgr;
        $this->machineid = $machineid;
		echo date("[Y-m-d H:i:s]")." $machineid is comming\r\n";
        $this->socketclient = $socketclient;
        $result = $dbmgr->fetch_array($dbmgr->query("select id,name from tb_device where deviceno='$machineid' "));
        $this->device_id = $result["id"] + 0;
        if($this->device_id==0){
            $sql = "insert into tb_device (`id`,`created_date`,`created_user`,`updated_date`,`updated_user`,
            `deviceno`,`name`,`status`)
            select ifnull(max(id),0)+1,now(),-1,now(),-1,
            '$machineid','unset','I' from tb_device  ";
            $dbmgr->query($sql);
        }
        $result = $dbmgr->fetch_array($dbmgr->query("select id,deviceno,name,ipset from tb_device where deviceno='$machineid' "));
        $this->device_id = $result["id"] + 0;
        $this->name = $result["name"];
        $this->changeserver($result["deviceno"],$result["ipset"]);
    }

    public $sendqueue=[];
    public function send($command,$sendfast=false)
    {
        $data = [];
        $data[] = (0x5A);
        $data[] = (0xA5);

        $d = 0x00;
        foreach ($command as $item) {
            $data[] = ($item);
            $d += $item;
        }
        $d = $d & 0xff;
        $data[] = ($d);
        $data[] = (0x0D);
        $data[] = (0x0A);
        $str = "";
        foreach ($data as $item) {
             $a = dechex($item);
            if (strlen($a) == 1) {
                $a = "0" . $a;
            }
            $str .= ($a);
        }
        $data = hex2bin($str);
        if($sendfast==true){
            //error_log(date("[Y-m-d H:i:s]") . "[SENDFAST]" . ($str) . "\r\n", 3, "client-" . date("YmdH") . ".log");
            //echo $str;
            $this->socketclient->send($data);
        }else{
			//error_log(date("[Y-m-d H:i:s]") . "[SENDQUEUE]" . ($str) . "\r\n", 3, "client-" . date("YmdH") . ".log");
            $this->sendqueue[]=$data;
        }
        return $str;
    }
    public function sendstr($command,$sendfast=false)
    {
        $data = [];
        $data[] = (0x5A);
        $data[] = (0xA5);

        $d = 0x00;
        foreach ($command as $item) {
            $data[] = ($item);
            $d += $item;
        }
        $d = $d & 0xff;
        $data[] = ($d);
        $data[] = (0x0D);
        $data[] = (0x0A);
        $str = "";
        foreach ($data as $item) {
             $a = dechex($item);
            if (strlen($a) == 1) {
                $a = "0" . $a;
            }
            $str .= ($a);
        }
        $data = hex2bin($str);
        echo $data;
        return $str;
    }
    public function sendforfile($command,$sendfast=false)
    {
        $data = [];
        $data[] = (0x5A);
        $data[] = (0xA5);

        foreach ($command as $item) {
            $data[] = ($item);
        }
        $str = "";
        foreach ($data as $item) {
             $a = dechex($item);
            if (strlen($a) == 1) {
                $a = "0" . $a;
            }
            $str .= ($a);
        }
        $data = hex2bin($str);
        error_log(date("[Y-m-d H:i:s]") . "[SENDFAST]" . ($str) . "\r\n", 3, "sendfile-" . date("YmdH") . ".log");
        //echo $str;
        $this->socketclient->send($data);
        return $str;
    }

    public function queueSend(){
        if(count($this->sendqueue)>0){
            $k=$this->sendqueue[0];
			$this->sendqueue=array_slice($this->sendqueue,1);
            $this->socketclient->send($k);
        }
    }

    public function syncStatus()
    {
        $this->getSpeed();
        $this->getPressure();
        $this->getGear();
        $this->getSpacing();
        $this->getWidth();
        $this->getMachineStatus();
        $this->getVersion();
    }

    public function getSpeed()
    {
        $data = [];
        $data[] = (0xaa);
        $data[] = (0x00);
        $data[] = (READSPEED);
        $data[] = (0x00);
        $data[] = (0x00);
        $this->send($data);
    }
    public function getPressure()
    {
        $data = [];
        $data[] = (0xaa);
        $data[] = (0x00);
        $data[] = (READBLADEPRESS);
        $data[] = (0x00);
        $data[] = (0x00);
        $this->send($data);
    }
    public function getGear()
    {
        $data = [];
        $data[] = (0xaa);
        $data[] = (0x00);
        $data[] = (READGEARRATE);
        $data[] = (0x00);
        $data[] = (0x00);
        $this->send($data);
    }
    public function getSpacing()
    {
        $data = [];
        $data[] = (0xaa);
        $data[] = (0x00);
        $data[] = (READSPACING);
        $data[] = (0x00);
        $data[] = (0x00);
        $this->send($data);
    }
    public function getWidth()
    {
        $data = [];
        $data[] = (0xaa);
        $data[] = (0x00);
        $data[] = (READWIDTH);
        $data[] = (0x00);
        $data[] = (0x00);
        $this->send($data);
    }
    public function getMachineStatus()
    {
        $data = [];
        $data[] = (0xaa);
        $data[] = (0x00);
        $data[] = (READMACHINESTATUS);
        $data[] = (0x00);
        $data[] = (0x00);
        $this->send($data);
    }
    public function getVersion()
    {
        $data = [];
        $data[] = (0xaa);
        $data[] = (0x00);
        $data[] = (VERSION);
        $data[] = (0x00);
        $data[] = (0x00);
        $this->send($data);
    }

    public function write($args){
        
        $COMM=trim($args[2]);
        $STR="";
        switch($COMM){
            case "SPEED":
                $speed=trim($args[3]);
                $STR=$this->setSpeed(intval($speed));
                break;
            case "PRESSURE":
                $val=trim($args[3]);
                $STR=$this->setPressure(intval($val));
                break;
            case "GEAR":
                $x=trim($args[3]);
                $y=trim($args[4]);
                $STR=$this->setGear(intval($x),intval($y));
                break;
            case "SPACING":
                $val=trim($args[3]);
                $STR=$this->setSpacing(intval($val));
                break;
            case "WIDTH":
                $width=trim($args[3]);
                $STR=$this->setWidth(intval($width));
                break;
            case "TRYCUT":
                $STR=$this->tryCut();
                break;
            case "RESET":
                $mode=trim($args[3]);
                $STR=$this->reset(intval($mode));
                break;
            case "WRITE":

                //if(count($this->filewritedata)>0){
                //    return "ERR|".$COMM."|INWRITING";
                //}
                $fileid=trim($args[3]);
				$this->filewritedata=[];
                $STR=$this->writefile(intval($fileid));
                break;
            case "SYNCSTATUS":
                $this->getMachineStatus();
                $STR="SYNC";
                break;
            case "SYNC":
                $this->syncStatus();
                $STR="SYNC";
                break;
			default: return "ERR|NOCOMM";
        }

        return "OK|".$COMM."|".$STR;
    }

    public function read($str)
    {
        global $dbmgr;

        //$str="5aa5aa00131200000032ffde054e5737313466084300000000d50d0a";
        $data = Cutter::GetData($str);
        $READ = $data[2];
        $COMM = $data[4];
		$machinestatus=$data[7];
        $machinestatus_decode = Cutter::decodeStatusCode($machinestatus);
		$xianwei=$machinestatus_decode[5];
		$usestatus=$machinestatus_decode[6].$machinestatus_decode[7];
        $resultcode = $data[8];
        //5aa5aa0010040000000a00c80d0a

        $device_id = $this->device_id;
        $sql = "insert into tb_receivedata (`id`,`created_date`,`created_user`,`updated_date`,`updated_user`,
        `device_id`,`datastr`,`status`,`receivetime`,`type`,`comm`)
        select ifnull(max(id),0)+1,now(),-1,now(),-1,
        $device_id,'$str','$resultcode',now(),'$READ','$COMM' from tb_receivedata  ";
        $dbmgr->query($sql);

        $sql="update tb_device set lastupdatetime=now(),machinestatus='$usestatus'
        where id=$device_id ";
        $dbmgr->query($sql);
		


        if ($READ == 0xaa) {
            

            if ($COMM == READSPEED) {
                if ($resultcode == 0x00) {
                    $speed=Cutter::GetNumber2($data[9],$data[10]);
                    $sql="update tb_device set speed='$speed',lastupdatetime=now() 
                    where id=$device_id ";
                    $dbmgr->query($sql);
                }
            }elseif ($COMM == READBLADEPRESS) {
                if ($resultcode == 0x00) {
                    $pressure=Cutter::GetNumber2($data[9],$data[10]);
                    $sql="update tb_device set pressure='$pressure',lastupdatetime=now()
                    where id=$device_id ";
                    $dbmgr->query($sql);
                }
            }elseif ($COMM == READGEARRATE) {
                if ($resultcode == 0x00) {
                    $x=Cutter::GetNumber2($data[9],$data[10]);
                    $y=Cutter::GetNumber2($data[11],$data[12]);
                    $sql="update tb_device set gear='$x,$y',lastupdatetime=now()
                    where id=$device_id ";
                    $dbmgr->query($sql);
                }
            }elseif ($COMM == READSPACING) {
                if ($resultcode == 0x00) {
					//print_r($data);
                    $spacing=$data[9];
                    $sql="update tb_device set spacing='$spacing',lastupdatetime=now() 
                    where id=$device_id ";
                    $dbmgr->query($sql);
                }
            }elseif ($COMM == READWIDTH) {
                if ($resultcode == 0x00) {
                    $width=Cutter::GetNumber2($data[9],$data[10]);
                    $sql="update tb_device set width='$width',lastupdatetime=now() 
                    where id=$device_id ";
                    $dbmgr->query($sql);
                }
            }elseif ($COMM == READMACHINESTATUS) {
                if ($resultcode == 0x00) {
                    //$sql="update tb_device set lastupdatetime=now() ,machinestatus='$usestatus'
                    //where id=$device_id ";
                    //$dbmgr->query($sql);
                }
            }elseif ($COMM == VERSION) {
                if ($resultcode == 0x00) {
                    $version=Cutter::GetString(array_slice($data,9,11));
					
					//print_r(array_slice($data,9,11));
					//echo $version;
                    $sql="update tb_device set version='$version',lastupdatetime=now() 
                    where id=$device_id ";
                    $dbmgr->query($sql);
                }
            }
        }if ($READ == 0xbb) {
            

            if ($COMM == SETSPEED) {
                $this->getSpeed();
            }elseif ($COMM == SETBLADEPRESS) {
                $this->getPressure();
            }elseif ($COMM == SETGEARRATE) {
                $this->getGear();
            }elseif ($COMM == SETSPACING) {
                $this->getSpacing();
            }elseif ($COMM == SETWIDTH) {
                $this->getWidth();
            }elseif ($COMM == CHANGESERVER) {
                echo $str;
            }
        }
        if ($READ == 0xCC) {
            if ($COMM == WRITEFILE) {
                if(count($this->filewritedata)>0){
                    $arr=$this->filewritedata[0];
                    $this->filewritedata=array_slice($this->filewritedata,1);
                    $this->sendforfile($arr);
                }
            }
        }
    }

    public $filewritedata=[];
    public $lastfiletime=0;

    public function writefile($fileid){

        Global $dbmgr;
        $fileid=$fileid+0;
        $file=$dbmgr->fetch_array($dbmgr->query("select * from tb_model where id=$fileid "));
		//print_r($file);
        $filename=$file["file"];
		if($filename==""){
			return "ERR|NOFILE";
		}
        $filepath=FILEREMOTE.$filename;
        $filecontent=file_get_contents($filepath);

        $filecontent=trim($filecontent) ;
        $filecontentlengthbyte = Cutter::ConvertNumber(strlen($filecontent), 8);
        $filenamebyte = Cutter::ConvertString("test", 16);

        $data = [];
        $data[] = (0xCC);
        $data[] = (0x01);
        $data[] = (WRITEFILE);
        $data[] = (0x14);
        $data[] = (0x00);
        $data = array_merge($data,$filecontentlengthbyte);
        $data = array_merge($data,$filenamebyte);

        $filecontentbyte = Cutter::ConvertString($filecontent, strlen($filecontent));

        $bigdata=[];
        //$bigdata[]=$data;

        $ci=0x02;
        while(count($filecontentbyte)>0){
            if (count($filecontentbyte) <= 1024) {
                $ci = 0x00;
            }
            $filechunlk =array_slice($filecontentbyte,0, 1024);
            $filecontentbyte = array_slice($filecontentbyte,1024);
            $filechunlkbyt = Cutter::ConvertNumber(count($filechunlk), 4);

            $filedata = [];
            $filedata[]=(0xcc);
            $filedata[]=$ci;
            $filedata[]=(WRITEFILE);
            $filedata[]=$filechunlkbyt[0];
            $filedata[]=$filechunlkbyt[1];
            $filedata=array_merge($filedata,$filechunlk);
            $bigdata[]=$filedata;
            $ci++;
            if($ci==0x00){
                break;
            }
        }

        $this->sendqueue=[];

        $this->filewritedata=$bigdata;
        $this->lastfiletime=time();

        return $this->send($data,true);

    }


    public function setSpeed($speed) {
        $speedbyt = Cutter::ConvertNumber($speed, 4);
        $data = [];
        $data[] = (0xbb);
        $data[] = (0x00);
        $data[] = (SETSPEED);
        $data[] = (0x02);
        $data[] = (0x00);
        $data[] = ($speedbyt[0]);
        $data[] = ($speedbyt[1]);
        $ret= $this->send($data,true);
        //$this->getSpeed();
        return $ret;
    }


    public function setPressure($val) {
        $by = Cutter::ConvertNumber($val, 4);
        $data = [];
        $data[] = (0xbb);
        $data[] = (0x00);
        $data[] = (SETBLADEPRESS);
        $data[] = (0x02);
        $data[] = (0x00);
        $data[] = ($by[0]);
        $data[] = ($by[1]);
        $ret= $this->send($data,true);
        //$this->getPressure();
        return $ret;
    }

    public function setGear($x,$y) {
        $bx = Cutter::ConvertNumber($x, 4);
        $by = Cutter::ConvertNumber($y, 4);
        $data = [];
        $data[] = (0xbb);
        $data[] = (0x00);
        $data[] = (SETGEARRATE);
        $data[] = (0x04);
        $data[] = (0x00);
        $data[] = ($bx[0]);
        $data[] = ($bx[1]);
        $data[] = ($by[0]);
        $data[] = ($by[1]);
        $ret= $this->send($data);
        //$this->getGear();

        return $ret;
    }
    public function setSpacing($val) {
        $data = [];
        $data[] = (0xbb);
        $data[] = (0x00);
        $data[] = (SETSPACING);
        $data[] = (0x01);
        $data[] = (0x00);
        $data[] = ($val);
        $ret= $this->send($data);
        //$this->getSpacing();
        return $ret;
    }
    public function setWidth($val) {
        $by = Cutter::ConvertNumber($val, 4);
        $data = [];
        $data[] = (0xbb);
        $data[] = (0x00);
        $data[] = (SETWIDTH);
        $data[] = (0x02);
        $data[] = (0x00);
        $data[] = ($by[0]);
        $data[] = ($by[1]);
        $ret= $this->send($data);
        //$this->getWidth();

        return $ret;
    }

    public function tryCut() {
        $data = [];
        $data[] = (0xbb);
        $data[] = (0x00);
        $data[] = (TRYCUT);
        $data[] = (0x00);
        $data[] = (0x00);
        return $this->send($data,true);
    }


    public function reset($mode) {
        $data = [];
        $data[] = (0xbb);
        $data[] = (0x00);
        $data[] = (RESET);
        $data[] = (0x01);
        $data[] = (0x00);
        $data[] = ($mode);
        $ret= $this->send($data);

        return $ret;
    }

    public function changeserver($machineid,$ipset){
        Global $CONFIG,$dbmgr;
        $device_id=$this->device_id;
        $CONFServerIP=$CONFIG['SERVERIP'];
        if($CONFServerIP!=""){
            if($ipset!=""){
                if($ipset!=$CONFServerIP){
                    $str= "Make $machineid change Server:".$CONFServerIP;
                    error_log(date("[Y-m-d H:i:s]") . "[SENDFAST]" . ($str) . "\r\n", 3, "serverchange-" . date("YmdH") . ".log");
                    $data = [];
                    $data[] = (0xbb);
                    $data[] = (0x00);
                    $data[] = (CHANGESERVER);
                    $data[] = (0x08);
                    $data[] = (0x00);
                    $ipsetarr=explode(".",$ipset);
                    $data[] = $ipsetarr[0];
                    $data[] = $ipsetarr[1];
                    $data[] = $ipsetarr[2];
                    $data[] = $ipsetarr[3];
                    $b = Cutter::ConvertNumber(6123, 8);
                    $data[] = ($b[0]);
                    $data[] = ($b[1]);
                    $data[] = ($b[2]);
                    $data[] = ($b[3]);
                    $this->send($data);
                    //error_log(date("[Y-m-d H:i:s]") . "[SENDFAST]" . ($ret) . "\r\n", 3, "serverchange-" . date("YmdH") . ".log");
                    return;
                }
            }
        }
        $sql="update tb_device set curip='$CONFServerIP' where id=$device_id ";
        $dbmgr->query($sql);
    }

    public static function GetData($a)
    {
        $ret = [];
        for ($i = 0; $i < strlen($a); $i = $i + 2) {
            $ret[] = hexdec("0x" . $a[$i] . $a[$i + 1]);
        }
        return $ret;
    }

    public static function GetMachineID($str)
    {
        //$str="5aa5aa00131200000032ffde054e5737313466084300000000d50d0a";
        $machineidstart = "5aa5aa001312000000";
        if (substr($str, 0, strlen($machineidstart)) == $machineidstart) {
            $str =  strtoupper(substr($str, strlen($machineidstart), 24));
            return $str;
        }
        return "";
    }

    public static function ConvertNumber($num, $len)
    {
        $num = intval($num);
        //alert(intnum);
        $a = dechex($num);
        //alert(a);
        while (strlen($a) < $len) {
            $a = "0" . $a;
        }
        //alert(a);
        $ret = [];
        for ($i = 0; $i < $len; $i = $i + 2) {
            $ret[] = hexdec("0x" . $a[$i] . $a[$i + 1]);
        }
        $ret = array_reverse($ret);
        //alert(JSON.stringify(ret));
        return $ret;
    }
    public static function GetString($data)
    {
        // alert(JSON.stringify(data));
        $ret = "";
        foreach ($data as $d) {
            if ($d != 0x00) {
                $ret .= chr($d);
            }
        }
        return $ret;
    }


    public static function ConvertString($wifiname, $num) {
        $ret = [];
        for ($i = 0; $i < $num; $i++) {
            if (strlen($wifiname) > $i) {
                $ret[]=ord($wifiname[$i]);
            } else {
                $ret[]=(0);
            }
        }
        return $ret;
    }
    public static function GetNumber2($d1, $d2) {
        $d2=dechex($d2);
        if(strlen($d2)=="1"){
            $d2="0".$d2;
        }
        $d1=dechex($d1);
        if(strlen($d1)=="1"){
            $d1="0".$d1;
        }
        return hexdec("0x" . ($d2) . ($d1));
    }
	public static function decodeStatusCode($hexString){
	 $binString = base_convert($hexString,16,2);
	 $var=sprintf("%08d", $binString);
	 return $var;
	}
}
