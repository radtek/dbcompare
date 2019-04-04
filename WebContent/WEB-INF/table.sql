CREATE TABLE MY_FILE_HISTORY (
	ID NUMBER(10) NOT NULL ,
	FILE_TYPE VARCHAR2(10) ,
	REAL_NAME VARCHAR2(100) ,
	SERVICE_NAME VARCHAR2(100)  ,
	SERVICE_URL VARCHAR2(500)  ,
	MD5 VARCHAR2(32) ,
	CREATE_TIME TIMESTAMP(6)   ,
	UPDATE_TIME TIMESTAMP(6)   ,
	STATUS NUMBER(4) ,
	PHYSICS_FLAG NUMBER(10)  ,
	CHECK_ON NUMBER(10)  ,
	APPEND_BASIC NUMBER(10)  
);


COMMENT ON TABLE  MY_FILE_HISTORY IS '脚本上传记录表';
COMMENT ON COLUMN  MY_FILE_HISTORY.ID IS 'ID.';
COMMENT ON COLUMN  MY_FILE_HISTORY.FILE_TYPE IS '类型ddl dml';
COMMENT ON COLUMN  MY_FILE_HISTORY.REAL_NAME IS '真实名字';
COMMENT ON COLUMN  MY_FILE_HISTORY.SERVICE_NAME IS '服务器存储名字';
COMMENT ON COLUMN  MY_FILE_HISTORY.SERVICE_URL IS '路径';
COMMENT ON COLUMN  MY_FILE_HISTORY.MD5 IS 'MD5';
COMMENT ON COLUMN  MY_FILE_HISTORY.CREATE_TIME IS '上传日期';
COMMENT ON COLUMN  MY_FILE_HISTORY.UPDATE_TIME IS '更新时间.';
COMMENT ON COLUMN  MY_FILE_HISTORY.STATUS IS '状态';
COMMENT ON COLUMN  MY_FILE_HISTORY.PHYSICS_FLAG IS '物理标识.1:正常;2:删除';
COMMENT ON COLUMN  MY_FILE_HISTORY.CHECK_ON IS '是否入库检查:0-检查，1-不检查';
COMMENT ON COLUMN  MY_FILE_HISTORY.APPEND_BASIC IS '是否追加到基础库:0-追加，1-不追加';

-- Create/Recreate primary, unique and foreign key constraints 
alter table MY_FILE_HISTORY add constraint MY_FILE_HISTORY_PK primary key (ID) using index;

--Create sequence
create sequence SEQ_MY_FILE_HISTORY
minvalue 1
maxvalue 999999999999999
start with 1
increment by 1
cache 5
cycle;

-- Create table 下载记录
create table MY_DOWN
(
   ID NUMBER(10) NOT NULL,
   SECHMA  VARCHAR2(20) ,
   WAY  VARCHAR2(20) ,
   RANGE  VARCHAR2(255) ,
   CONTENT  VARCHAR2(4000) ,
   URL  VARCHAR2(100) ,
   IP  VARCHAR2(100) ,
   DOWN_TYPE NUMBER(10) ,
   PHYSICS_FLAG NUMBER(10) ,
   UPDATE_TIME TIMESTAMP(6),
   CREATE_TIME TIMESTAMP(6),
   REMARK  VARCHAR2(100) ,
   REAL_RANGE  VARCHAR2(100) 
);

-- Add comments to the table 
comment on table MY_DOWN is '下载记录.';

-- Add comments to the columns 
comment on column MY_DOWN.ID is '记录编号.';
comment on column MY_DOWN.SECHMA is '表空间';
comment on column MY_DOWN.WAY is '下载方式：压缩下载,跨时间段下载';
comment on column MY_DOWN.RANGE is '下载方式区间';
comment on column MY_DOWN.CONTENT is '下载方式内容';
comment on column MY_DOWN.DOWN_TYPE is '类型：测试0，线上1';
comment on column MY_DOWN.URL is '下载路径';
 comment on column MY_DOWN.IP is 'IP';
comment on column MY_DOWN.UPDATE_TIME is '更新时间.';
comment on column MY_DOWN.CREATE_TIME is '创建时间.';
comment on column MY_DOWN.PHYSICS_FLAG is '物理标识.1:正常;2:删除';
comment on column MY_DOWN.REMARK is '备注';
comment on column MY_DOWN.REAL_RANGE is '实际区间';

-- Create/Recreate primary, unique and foreign key constraints 
alter table MY_DOWN add constraint MY_DOWN_PK primary key (ID) using index;

--Create sequence
create sequence SEQ_MY_DOWN
minvalue 1
maxvalue 999999999999999
start with 1
increment by 1
cache 5
cycle;

-- Create table 标准表维护
create table MY_BASIC_TABLE
(
   ID NUMBER(10) NOT NULL,
  	FILE_TYPE VARCHAR2(10) ,
	REAL_NAME VARCHAR2(100) ,
   SERVICE_URL  VARCHAR2(500) ,
   IP  VARCHAR2(100) ,
   ORDERS NUMBER(10) ,
   PHYSICS_FLAG NUMBER(10) ,
   UPDATE_TIME TIMESTAMP(6),
   CREATE_TIME TIMESTAMP(6) 
);

-- Add comments to the table 
comment on table MY_BASIC_TABLE is '标准表维护';

-- Add comments to the columns 
comment on column MY_BASIC_TABLE.ID is '记录编号.';
COMMENT ON COLUMN  MY_BASIC_TABLE.FILE_TYPE IS '类型ddl dml';
COMMENT ON COLUMN  MY_BASIC_TABLE.REAL_NAME IS '真实名字';
COMMENT ON COLUMN  MY_BASIC_TABLE.SERVICE_URL IS '路径';
comment on column MY_BASIC_TABLE.IP is 'IP';
comment on column MY_BASIC_TABLE.ORDERS is '排序';

comment on column MY_BASIC_TABLE.UPDATE_TIME is '更新时间.';
comment on column MY_BASIC_TABLE.CREATE_TIME is '创建时间.';
comment on column MY_BASIC_TABLE.PHYSICS_FLAG is '物理标识.1:正常;2:删除';


-- Create/Recreate primary, unique and foreign key constraints 
alter table MY_BASIC_TABLE add constraint MY_BASIC_TABLE_PK primary key (ID) using index;

--Create sequence
create sequence SEQ_MY_BASIC_TABLE
minvalue 1
maxvalue 999999999999999
start with 1
increment by 1
cache 5
cycle;


-- Create table 银行表
create table MY_BASIC_BANK
(
   ID NUMBER(10) NOT NULL,
   	URL VARCHAR2(100) ,
   	USER_NAME VARCHAR2(100) ,
   	REAL_NAME VARCHAR2(100) ,
   	PWD VARCHAR2(100) ,
   	SECHMA VARCHAR2(100) ,
   	BANK_TYPE VARCHAR2(2) ,
   	MOLD NUMBER(2) , 
   	WEB_STIE VARCHAR2(50) ,
   	CHECK_TIME VARCHAR2(20) ,
   	
   	CHECK_RESULT NUMBER(2) ,
   	CHECK_CONTENT VARCHAR2(4000) ,
   	PHYSICS_FLAG NUMBER(10) ,
   UPDATE_TIME TIMESTAMP(6),
   CREATE_TIME TIMESTAMP(6),
    CHECK_IS NUMBER(2),
    MERGE VARCHAR2(200) ,
    CLEAING_PROCESS VARCHAR2(200) ,
    PLAN_NAME VARCHAR2(200) ,
    FLS1 VARCHAR2(200) ,
    FLS2 VARCHAR2(200) ,
    FLS3 VARCHAR2(4000), 
    
    acceptorg VARCHAR2(64),
    platform VARCHAR2(64),
    jar VARCHAR2(64),
    md5 VARCHAR2(64),
    fileType VARCHAR2(64),
    filecount VARCHAR2(64),
    nullfile VARCHAR2(64),
    
    plan_pame_override VARCHAR2(64),
    cleaing_process_override VARCHAR2(64),
    ftp VARCHAR2(64),
    notify VARCHAR2(64),
     autoclean VARCHAR2(64),
    service_port VARCHAR2(64),
    etraparam VARCHAR2(1000),
    
    isfreez VARCHAR2(64),
    issubdiy VARCHAR2(64),
    isbroken VARCHAR2(1000),
    
     webok VARCHAR2(64),
     needback VARCHAR2(64),
     needfile VARCHAR2(64),
      needcode VARCHAR2(64),
      
      issend NUMBER(2),
      sendcount NUMBER(2),
      TEST_SECHMA VARCHAR2(100)  
    
);

-- Add comments to the table 
comment on table MY_BASIC_BANK is '银行表';

-- Add comments to the columns 
comment on column MY_BASIC_BANK.ID is '记录编号.';

comment on column MY_BASIC_BANK.URL is '链接地址';
comment on column MY_BASIC_BANK.USER_NAME is '用户名';
comment on column MY_BASIC_BANK.REAL_NAME is '实列名';
comment on column MY_BASIC_BANK.PWD is '密码';
comment on column MY_BASIC_BANK.SECHMA is '用户空间';
comment on column MY_BASIC_BANK.BANK_TYPE is '银行类型 A B C级别';
comment on column MY_BASIC_BANK.MOLD is '类型 0测试库 1私有云专属库 2私有云网络库';
comment on column MY_BASIC_BANK.WEB_STIE is '网址';
comment on column MY_BASIC_BANK.CHECK_TIME is '检测时间';
comment on column MY_BASIC_BANK.CHECK_RESULT is '检测结果 0-为比较 1-比较无差异 2-比较有差异';
comment on column MY_BASIC_BANK.CHECK_CONTENT is '检测内容';

comment on column MY_BASIC_BANK.UPDATE_TIME is '更新时间.';
comment on column MY_BASIC_BANK.CREATE_TIME is '创建时间.';
comment on column MY_BASIC_BANK.PHYSICS_FLAG is '物理标识.1:正常;2:删除';
comment on column MY_BASIC_BANK.CHECK_IS is '是否检测0:检测;1:不检测';
comment on column MY_BASIC_BANK.MERGE is '合并方案';

comment on column MY_BASIC_BANK.CLEAING_PROCESS is '清分类';

comment on column MY_BASIC_BANK.PLAN_NAME is '清分日结方案';
comment on column MY_BASIC_BANK.FLS1 is 'FLS1';
comment on column MY_BASIC_BANK.FLS2 is 'FLS2';
comment on column MY_BASIC_BANK.FLS3 is 'FLS3';

comment on column MY_BASIC_BANK.acceptorg is '受理机构号';
comment on column MY_BASIC_BANK.platform is '标识符';
comment on column MY_BASIC_BANK.jar is 'jar包';
comment on column MY_BASIC_BANK.md5 is 'md5';
comment on column MY_BASIC_BANK.fileType is '清分文件类型';

comment on column MY_BASIC_BANK.filecount is '清分文件个数';
comment on column MY_BASIC_BANK.nullfile is '空文件';

comment on column MY_BASIC_BANK.plan_pame_override is '日结是否重写';

comment on column MY_BASIC_BANK.cleaing_process_override is '清分文件是否定制';
comment on column MY_BASIC_BANK.ftp is 'ftp类型';
comment on column MY_BASIC_BANK.notify is '通知类型';
comment on column MY_BASIC_BANK.autoclean is '是否自动清分';
comment on column MY_BASIC_BANK.service_port is '后台service端口';
comment on column MY_BASIC_BANK.etraparam is '清分文件涉及额外参数';

comment on column MY_BASIC_BANK.isfreez is '是否冻结';

 comment on column MY_BASIC_BANK.issubdiy is '是否补贴';
comment on column MY_BASIC_BANK.isbroken is '威富通服务费是否分离';
 
comment on column MY_BASIC_BANK.webok is '网站访问是否正常';
comment on column MY_BASIC_BANK.needback is '是否回盘';
comment on column MY_BASIC_BANK.needfile is '回盘文件名';
comment on column MY_BASIC_BANK.needcode is '回盘成功码';

comment on column MY_BASIC_BANK.issend is '是否发送邮件';
comment on column MY_BASIC_BANK.sendcount is '邮件发送次数';
comment on column MY_BASIC_BANK.TEST_SECHMA is '测试用户空间';
--alter table MY_BASIC_BANK add (acceptorg VARCHAR2(64)) ;
-- Create/Recreate primary, unique and foreign key constraints 
alter table MY_BASIC_BANK add constraint MY_BASIC_BANK_PK primary key (ID) using index;

--Create sequence
create sequence SEQ_MY_BASIC_BANK
minvalue 1
maxvalue 999999999999999
start with 1
increment by 1
cache 5
cycle;

-- Create table 我的参数
create table MY_PARAMETER
( 	PARAMETER_NAME VARCHAR2(100) ,
   	PARAMETER_VALUE VARCHAR2(100) ,
   	REMARK VARCHAR2(100) ,
   UPDATE_TIME TIMESTAMP(6),
   CREATE_TIME TIMESTAMP(6)
    
);
comment on column MY_PARAMETER.PARAMETER_VALUE is '参数值';
comment on column MY_PARAMETER.PARAMETER_NAME is '参数key';
comment on column MY_PARAMETER.PARAMETER_NAME is '备注';
comment on column MY_PARAMETER.UPDATE_TIME is '更新时间.';
comment on column MY_PARAMETER.CREATE_TIME is '创建时间.';

ALTER TABLE MY_PARAMETER ADD CONSTRAINT PARAMETER_NAME_uk UNIQUE (PARAMETER_NAME) using index;
 --初始化数据
insert into MY_PARAMETER(PARAMETER_NAME,PARAMETER_VALUE,REMARK,UPDATE_TIME,CREATE_TIME) values('is_compare','false','是否数据库比较中',sysdate,sysdate);
insert into MY_PARAMETER(PARAMETER_NAME,PARAMETER_VALUE,REMARK,UPDATE_TIME,CREATE_TIME) values('last_unified_upgrade_time',to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),'最后一次统一升级时间',sysdate,sysdate);
insert into MY_PARAMETER(PARAMETER_NAME,PARAMETER_VALUE,REMARK,UPDATE_TIME,CREATE_TIME) values('last_upload_time',to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),'最后一次上传脚本更新时间',sysdate,sysdate);
insert into MY_PARAMETER(PARAMETER_NAME,PARAMETER_VALUE,REMARK,UPDATE_TIME,CREATE_TIME) values('ignore_table_reg','^(\\d).*|.*[0-9]$','忽视该表信息,比如去掉数字开头的:^(\\d).*|.*[0-9]$',sysdate,sysdate);
insert into MY_PARAMETER(PARAMETER_NAME,PARAMETER_VALUE,REMARK,UPDATE_TIME,CREATE_TIME) values('need_table_reg','','保留表信息,默认不检测,比如只保留cle模块:^cle.{1,}$',sysdate,sysdate);


CREATE TABLE MY_BANK_CONF (
	ID NUMBER(10) NOT NULL ,
	bank_id NUMBER(10) ,
	bank_name VARCHAR2(200) ,
	stype NUMBER(10) ,
	remark VARCHAR2(500)  ,
	excel_json clob ,

	content VARCHAR2(4000),
	
	excel_path VARCHAR2(500)  ,
	conf_path VARCHAR2(500) ,
	
	fld_n1 NUMBER(10)  ,
	fld_s1 VARCHAR2(500) ,
	
	PHYSICS_FLAG NUMBER(10)  ,
	CREATE_TIME TIMESTAMP(6)   ,
	UPDATE_TIME TIMESTAMP(6)   
);


COMMENT ON TABLE  MY_BANK_CONF IS '银行配置模板文件';
COMMENT ON COLUMN  MY_BANK_CONF.ID IS 'ID.';
COMMENT ON COLUMN  MY_BANK_CONF.bank_id IS 'MY_BASIC_BANK主键';
COMMENT ON COLUMN  MY_BANK_CONF.bank_name IS '银行名称';
COMMENT ON COLUMN  MY_BANK_CONF.stype IS '配置类型 0-测试 1-正式';
COMMENT ON COLUMN  MY_BANK_CONF.remark IS '备注';
COMMENT ON COLUMN  MY_BANK_CONF.excel_json IS '3.5平台公有云平台 支付平台  Spay终端 私有云平台 excel平台解析后内容';

COMMENT ON COLUMN  MY_BANK_CONF.content IS '内容';

COMMENT ON COLUMN  MY_BANK_CONF.excel_path IS 'Excel 上传路径';
COMMENT ON COLUMN  MY_BANK_CONF.conf_path IS '配置文件路径 Zip压缩';


COMMENT ON COLUMN  MY_BANK_CONF.fld_n1 IS '备份字段';
COMMENT ON COLUMN  MY_BANK_CONF.fld_s1 IS '备份字段';

COMMENT ON COLUMN  MY_BANK_CONF.PHYSICS_FLAG IS '物理标识.1:正常;2:删除';
COMMENT ON COLUMN  MY_BANK_CONF.CREATE_TIME IS '上传日期';
COMMENT ON COLUMN  MY_BANK_CONF.UPDATE_TIME IS '更新时间.';
-- Create/Recreate primary, unique and foreign key constraints 
alter table MY_BANK_CONF add constraint MY_BANK_CONF_PK primary key (ID) using index;

--Create sequence
create sequence SEQ_MY_BANK_CONF
minvalue 1
maxvalue 999999999999999
start with 1
increment by 1
cache 5
cycle;
