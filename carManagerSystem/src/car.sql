create table car_user(
	 id int not null auto_increment,    	
	 userName VARCHAR(30) unique,	
	 realName VARCHAR(30) not null,	
	 passWord VARCHAR(30) not null,		
	 phone VARCHAR(30) not null,	
	 type int  not null,	      
	 constraint car_user primary key (id)
);


create table park_info_set(
	 id int not null auto_increment,    		
	 parkName VARCHAR(30) not null,	
	 parkLocation VARCHAR(30) not null,		
	 openDate  VARCHAR(30) not null,      
	 closeDate  VARCHAR(30) not null, 
	 parkNumber int not null,
	 rentOrSale int not null,
	 feeScale  double not null,  
	 userInfo VARCHAR(500),
	 constraint park_info_set primary key (id)
);

insert into park_info_set (id,parkName,parkLocation,openDate,closeDate,parkNumber,rentOrSale,feeScale,userInfo) values(1,'bbs','2015-6-22 12:21:43','jahduad','jhfsjhfdkaijd'); 


create table car_park_info(
	 id int not null auto_increment,    		
	 userName VARCHAR(30) not null,
	 parkName VARCHAR(30) not null,
	 carNo VARCHAR(30) not null,		
	 inTime VARCHAR(30) not null,	
	 type int not null,
	 outTime varchar(30) not null,      
	 locationNo  VARCHAR(30),		
	 constraint car_park_info primary key (id)
);



create table charge_info(
	 id int not null auto_increment,    		
	 userName VARCHAR(30) not null,
	 parkName VARCHAR(30) not null,
	 carNo VARCHAR(30) not null,	
	 hour int not null,
	 type int not null,
	 locationNo int not null,
	 charge double not null,
	 createTime VARCHAR(30) not null,
	 isCharge int not null,
	 constraint charge_info primary key (id)
);




create table vip(
	 id int not null auto_increment,    		
	 userName VARCHAR(30) not null,
	 carNo VARCHAR(30) not null,	
	 type int not null,
	 endTime VARCHAR(30) not null,	
	 startTime VARCHAR(30) not null,	
	 createTime VARCHAR(30) not null,	
	 parkNo VARCHAR(30) not null,	
	 location VARCHAR(30) not null,	
	 constraint vip primary key (id)
);



create table reservation(
	 id int not null auto_increment,    		
	 userName VARCHAR(30) not null,
	 carNo VARCHAR(30) not null,	
	 parkName VARCHAR(30) not null,	
	 parkNo VARCHAR(30) not null,	
	 type VARCHAR(5) not null,	
	 constraint reservation primary key (id)
);

