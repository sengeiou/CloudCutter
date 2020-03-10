<?php


class DbMysql
{
	/**
	* Sqlsrv connect identifier
	* @var resource
	*/
	public $conn;
	public $in_trans=0;

	/**
	* calculate executed sql statement num
	* @var int
	*/
	public $querynum = 0;
	private  $codeKey="unbreakable";

	private static $instance = null;
	
	
	
	function __destruct()
	{		
		//~ close the opened connection(no effect to sqlsrv_pconnect)
		
	}
	
	/**
	* connect to db, return connect identifier
	* @param string db host
	* @param string db user
	* @param string db password
	* @param string db name
	* @param bool is persistent connection: 1 - Yes, 0 - No
	* @return link_identifier
	*/
	public $dbhost;
	public $dbuser;
	public $dbpass;
	public $dbname;
	function __construct($dbhost, $dbuser, $dbpass, $dbname ="", $pconnect = 0) 
	{
		//echo $dbhost.' '.$dbuser.' '.$dbpass.' '.$dbname;exit;
		$this->dbhost=$dbhost;
		$this->dbuser=$dbuser;
		$this->dbpass=$dbpass;
		$this->dbname=$dbname;
	}


	/**
	* select database
	* @param string db name
	* @return boolean Success: true, Fail: false
	*/
	function select_db($dbname) 
	{
		if(empty($dbname)){
			return;
		}
		return @mysqli_select_db($dbname , $this->conn);
	}
	
	function begin_trans()
	{
		if($this->in_trans==0){
			if (mysqli_query($this->conn,"BEGIN") == false)
			{
			     echo "Could not begin transaction.\n";
			     die( print_r( sqlsrv_errors(), true ));
			}
		}
		$this->in_trans++;
		
		//echo "begin ".$this->in_trans."<br />";
	}
	function commit_trans()
	{
		$this->in_trans--;
		//echo "commint ".$this->in_trans."<br />";
		if($this->in_trans>0){
			return;
		}
		//echo "ready commit ".$this->in_trans."<br />";
		mysqli_query($this->conn,"COMMIT" );
		$this->in_trans=0;
	}
	function rollback_trans()
	{
		mysqli_query($this->conn,"ROOLBACK" );
		$this->in_trans=0;
	}

	/**
	* execute sql statement
	* @param string $sql: sql statement
	* @param string $type: default '', option: CACHE | UNBUFFERED
	* @param int $expires: Cache lifetime, second for unit
	* @param string $dbname: db name
	* @return resource
	*/
	function query($sql) 
	{
		
		if(!$this->conn = mysqli_connect($this->dbhost,$this->dbuser,$this->dbpass,$this->dbname))
		{
			$this->halt('service unavailable');
		}
		
		mysqli_set_charset($this->conn,"utf8");
		$this->conn;
		
		$query = mysqli_query($this->conn,$sql );
		if($query==null)
		{
			//error_log(date("[Y-m-d H:i:s]")."[SQL]".($sql) ." \r\n", 3,"sqlerror-".date("YmdH").".log");
			if($this->in_trans>0)
			{
				$this->rollback_trans();
			}
			$this->halt(mysqli_error($this->conn), $sql);
		}
		//error_log(date("[Y-m-d H:i:s]")."[SQL]".($sql) ." \r\n", 3,"sqldebug-".date("YmdH").".log");
		$this->querynum++;
		
		if($this->conn)	$this->close();
		
		return $query;
	}
	
	function getNewId($tablename){
		$sql="select ifnull(max(id),0)+1 id from ".$tablename;
		$query = $this->query($sql);
		$result = $this->fetch_array($query); 
		$id=$result["id"];
		return $id;
	}
	
	function checkHave($tablename,$where){
		$sql="select 1 checkid from ".$tablename." where ".$where;
		$query = $this->query($sql);
		$result = $this->fetch_array($query); 
		$id=$result["checkid"];
		return $id=="1";
	}
	function getDate(){
		return " now() ";
	}
	/**
	* execute sql statement, only get one record
	* @param string $sql: sql statement
	* @param string $type: default '', option: CACHE | UNBUFFERED
	* @param int $expires: Cache lifetime, second for unit
	* @param string $dbname: db name
	* @return array, if no record in $query, return an empty array
	*/
	function get_one($sql)
	{
		$query = $this->query($sql);
		$row = $this->fetch_array($query);
		$this->free_result($query);
		//return $row ;
		return $row===false ? array() : $row ;
	}
	
	/**
	* get one row data as associate array from resultset.
	* @param resource ResultSet
	* @param string define return array type, option value: MYSQL_ASSOC, MYSQL_BOTH, MYSQL_NUM
	* @return array
	*/
	function fetch_array($query) 
	{
		$ret= mysqli_fetch_array($query,MYSQLI_ASSOC);
		return $ret;
	}
	
	/**
	* get all rows data as associate array from resultset.
	* @param resource: ResultSet
	* @param string: define return array type,option value: MYSQL_ASSOC, MYSQL_BOTH, MYSQL_NUM
	* @return array: contain all rows data in $query; if no record in $query, return an empty array
	*/
	function fetch_array_all($query) 
	{
		while($row=mysqli_fetch_array($query,MYSQLI_ASSOC))
			$rows[] = $row;
		//return $rows;
		return !is_array($rows)? array() : $rows ;
	}

	function fetch_one_column( $query, $colname )
	{
		$retarr = $this->fetch_array_all( $query );
		foreach ( $retarr as &$v ){
			$v = $v[$colname];
		}
		return 	$retarr;
	}
	
	/**
	* save or update db record
	* @param string $sql_check: check if exist sql statement
	* @param string $sql_update: update sql statement
	* @param string $sql_insert: insert sql statement
	* @return effect row num
	*/
	function save_or_update($sql_check, $sql_update, $sql_insert) 
	{
		$this->query($sql_check);
		if ( $this->affected_rows() > 0 ) {	// exist corresponding record
			$this->query($sql_update);	
		} else {	// no exist
			$this->query($sql_insert);
		}
		
		return $this->affected_rows();
	}
	
	/**
	* get the last effect row num
	* @return int
	*/
	function affected_rows() 
	{
		return mysqli_affected_rows();
	}

	/**
	* get record count of resultset
	* @return int
	*/
	function num_rows($query) 
	{
		return mysqli_num_rows($query);
	}

	/**
	* get field count of resultset
	* @return int
	*/
	function num_fields($query) 
	{
		return mysqli_num_fields($query);
	}

    /**
     * get result set 
     * @return array
     */
	function result($query, $row) 
	{
		return @mysqli_result($query, $row);
	}

	
	/**
	 * free result set
	 * @return bool whether free result success 
	 */
	function free_result($query) 
	{
		return mysqli_free_result($query);
	}


    /**
     * get one row from resultset
	 * @return array
	 */
	function fetch_row($query) 
	{
		return mysqli_fetch_row($query);
	}


	/**
	 * close db connection
	 * @return bool whether close connection successful
	 */
	function close() 
	{
		return mysqli_close($this->conn);
	}

	/**
	 * Character encoding conversion
	 * @return character
	 */
//	function setCharsetEncoding($data){;
//		$data = ($data,'iso8859-1','utf-8');
//		return $data;
//	}
	
    /**
	* display error message and exit
	* @param string $message
	* @param string $sql, sql statememt
	*/
	function halt($message = '', $sql = '')
	{
		//echo $message;
		//throw new Exception($message);
		//exit();
		error_log(date("[Y-m-d H:i:s]")."[SQL]".($message) ."\r\n $sql \r\n", 3,"sqlerror-".date("YmdH").".log");
	}

}

$dbmgr = new DbMysql($CONFIG['database']['host'], $CONFIG['database']['user'], $CONFIG['database']['psw'], $CONFIG['database']['database']);

?>