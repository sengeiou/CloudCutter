<?php
define(READSPEED, 0x10);
define(READBLADEPRESS, 0x11);
define(READGEARRATE, 0x12);
define(READMACHINEID, 0x13);
define(READAPINFO, 0x14);
define(READSTAINFO, 0x15);
define(READSPACING, 0x18);
define(READWIDTH, 0x19);

define(SETSPEED, 0x10);
define(SETBLADEPRESS, 0x11);
define(SETGEARRATE, 0x12);
define(SETMACHINEID, 0x13);
define(SETAPINFO, 0x14);
define(SETSTAINFO, 0x15);
define(RESET, 0x16);
define(TRYCUT, 0x17);
define(SETSPACING, 0x18);
define(SETWIDTH, 0x19);

define(READMACHINESTATUS, 0x20);
define(WRITEFILE, 0x30);

class Cutter
{
    public $socketclient = null;
    public $device_id = 0;
    public $machineid = "";
    public function __construct($machineid, $socketclient)
    {
        global $dbmgr;
        $this->machineid = $machineid;
        $this->socketclient = $socketclient;
        $result = $dbmgr->fetch_array($dbmgr->query("select id from tb_device where deviceno='$machineid' "));
        $this->device_id = $result["id"] + 0;
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
            error_log(date("[Y-m-d H:i:s]") . "[SENDFAST]" . ($str) . "\r\n", 3, "client-" . date("YmdH") . ".log");
            //echo $str;
            $this->socketclient->send($data);
        }else{
			error_log(date("[Y-m-d H:i:s]") . "[SENDQUEUE]" . ($str) . "\r\n", 3, "client-" . date("YmdH") . ".log");
            array_unshift($this->sendqueue,$data);
        }
        return $str;
    }

    public function queueSend(){
        if(count($this->sendqueue)>0){
            $k=array_pop($this->sendqueue);
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
                $STR=$this->setGear(intval($x,$y));
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
        $machinestatus = $data[7];
        $resultcode = $data[8];
        //5aa5aa0010040000000a00c80d0a

        $device_id = $this->device_id;
        $sql = "insert into tb_receivedata (`id`,`created_date`,`created_user`,`updated_date`,`updated_user`,
        `device_id`,`datastr`,`status`,`receivetime`,`type`,`comm`)
        select ifnull(max(id),0)+1,now(),-1,now(),-1,
        $device_id,'$str','$resultcode',now(),'$READ','$COMM' from tb_receivedata  ";
        $dbmgr->query($sql);

        $sql="update tb_device set lastupdatetime=now(),machinestatus='$machinestatus' 
        where id=$device_id ";
        $dbmgr->query($sql);


        if ($READ == 0xaa) {
            

            if ($COMM == READSPEED) {
                if ($resultcode == 0x00) {
                    $speed=Cutter::GetNumber2($data[9],$data[10]);
                    $sql="update tb_device set speed='$speed',lastupdatetime=now(),machinestatus='$machinestatus' 
                    where id=$device_id ";
                    $dbmgr->query($sql);
                }
            }elseif ($COMM == READBLADEPRESS) {
                if ($resultcode == 0x00) {
                    $pressure=Cutter::GetNumber2($data[9],$data[10]);
                    $sql="update tb_device set pressure='$pressure',lastupdatetime=now(),machinestatus='$machinestatus' 
                    where id=$device_id ";
                    $dbmgr->query($sql);
                }
            }elseif ($COMM == READGEARRATE) {
                if ($resultcode == 0x00) {
                    $x=Cutter::GetNumber2($data[9],$data[10]);
                    $y=Cutter::GetNumber2($data[11],$data[12]);
                    $sql="update tb_device set gear='$x,$y',lastupdatetime=now(),machinestatus='$machinestatus' 
                    where id=$device_id ";
                    $dbmgr->query($sql);
                }
            }elseif ($COMM == READSPACING) {
                if ($resultcode == 0x00) {
                    $spacing=$data[9];
                    $sql="update tb_device set spacing='$spacing',lastupdatetime=now(),machinestatus='$machinestatus' 
                    where id=$device_id ";
                    $dbmgr->query($sql);
                }
            }elseif ($COMM == READWIDTH) {
                if ($resultcode == 0x00) {
                    $width=Cutter::GetNumber2($data[9],$data[10]);
                    $sql="update tb_device set width='$width',lastupdatetime=now(),machinestatus='$machinestatus' 
                    where id=$device_id ";
                    $dbmgr->query($sql);
                }
            }elseif ($COMM == READMACHINESTATUS) {
                if ($resultcode == 0x00) {
                    $sql="update tb_device set lastupdatetime=now(),machinestatus='$machinestatus' 
                    where id=$device_id ";
                    $dbmgr->query($sql);
                }
            }
        }
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
        return $this->send($data);
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
        return $this->send($data);
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
        return $this->send($data);
    }
    public function setSpacing($val) {
        $data = [];
        $data[] = (0xbb);
        $data[] = (0x00);
        $data[] = (SETSPACING);
        $data[] = (0x01);
        $data[] = (0x00);
        $data[] = ($val);
        return $this->send($data);
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
        return $this->send($data);
    }

    public function tryCut() {
        $data = [];
        $data[] = (0xbb);
        $data[] = (0x00);
        $data[] = (TRYCUT);
        $data[] = (0x00);
        $data[] = (0x00);
        return $this->send($data);
    }


    public function reset($mode) {
        $data = [];
        $data[] = (0xbb);
        $data[] = (0x00);
        $data[] = (RESET);
        $data[] = (0x01);
        $data[] = (0x00);
        $data[] = ($mode);
        return $this->send($data);
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
            $str = substr($str, strlen($machineidstart), 32);
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
    public static function GetNumber2($d1, $d2) {
        return hexdec("0x" . dechex($d2) . dechex($d1));
    }
}
