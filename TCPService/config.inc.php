<?php
            

#[Root]
$CONFIG['Title']             = '远程云切膜';
$CONFIG['URL']="http://cmsdev.app-link.org/alucard263096/cloudcutter";
$CONFIG['lang']="zh-cn";//en-us
$CONFIG["SessionName"]="FooterCMS_alucard263096_cloudcutter";
$CONFIG["SupportMultiLanguage"]=false;

$CONFIG['solution_configuration']='debug';
$CONFIG['server']		= 'windows';   //windows or linux


$CONFIG['SERVERIP']='120.77.151.197';


#[Database]
$CONFIG['database']['provider']	= 'mysql';  //mssql,sqlsrv
$CONFIG['database']['host']		= '';  
$CONFIG['database']['database']	= '';  
$CONFIG['database']['user']		= '';  
$CONFIG['database']['psw']		= ''; 


#[File upload]
$CONFIG['fileupload']['upload_path']	= "upload";
$CONFIG['fileupload']['oss']	= true;//改为oss上传的话
if($CONFIG['fileupload']['oss']==true){
	$CONFIG['fileupload']['upload_path']="aliyun";//改为qiniu则用七牛的云
	$CONFIG['fileupload']['upload_path']	= "http://applinkupload.oss-cn-shenzhen.aliyuncs.com";
	$CONFIG['fileupload']['accesskeyid']    = "";
	$CONFIG['fileupload']['accesskeysecret']    = "";
	$CONFIG['fileupload']['endpoint']    = "oss-cn-shenzhen-internal.aliyuncs.com";
	$CONFIG['fileupload']['bucket']    = "applinkupload";
	$CONFIG['fileupload']['bucket_path']    = "";
	
	define("UPLOADPATH","http://applinkupload.oss-cn-shenzhen.aliyuncs.com/alucard263096/cloudcutter/");
	
define("FILEREMOTE", 'http://applinkupload.oss-cn-shenzhen.aliyuncs.com/alucard263096/cloudcutter/model/');
}
            ?>
