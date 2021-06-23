SET GLOBAL validate_password.policy=low;
SET GLOBAL validate_password.length=4;
SET GLOBAL validate_password.mixed_case_count=0;
SET GLOBAL validate_password.number_count=0;
SET GLOBAL validate_password.special_char_count=0;
create database interline_EstimateSystem default character set utf8;
create user 'hrhr6'@'%' identified by "hrhr";
GRANT ALL privileges ON interline_EstimateSystem.* TO 'hrhr6'@'%';
SET GLOBAL log_bin_trust_function_creators = 1;


use interline_EstimateSystem;
SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables  erm2 */

DROP TABLE IF EXISTS accountInform;
DROP TABLE IF EXISTS billSheet1Items;
DROP TABLE IF EXISTS billSheet1;
DROP TABLE IF EXISTS estimateSheet1Items;
DROP TABLE IF EXISTS estimateSheet1;
DROP TABLE IF EXISTS userInform;
DROP TABLE IF EXISTS auth;
DROP TABLE IF EXISTS companyInform;
DROP TABLE IF EXISTS department;
DROP TABLE IF EXISTS estimateMaster;
DROP TABLE IF EXISTS documentMaster;
DROP TABLE IF EXISTS documentState;
DROP TABLE IF EXISTS documentType;
DROP TABLE IF EXISTS fileNames;
DROP TABLE IF EXISTS masterSeq;
DROP TABLE IF EXISTS position;
DROP TABLE IF EXISTS workflow;
DROP TABLE IF EXISTS workflowInform;
DROP TABLE IF EXISTS systemType;




/* Create Tables */

-- 口座情報
CREATE TABLE accountInform
(
	accountInformNum int NOT NULL AUTO_INCREMENT COMMENT '口座情報の固有番号',
	-- このタイトルを見て請求書で口座情報を選択するようになる。
	accountInformName varchar(40) NOT NULL COMMENT '口座情報のタイトル : このタイトルを見て請求書で口座情報を選択するようになる。',
	bankName varchar(30) COMMENT '銀行名',
	branchName varchar(20) COMMENT '支店名',
	accountName varchar(30) COMMENT '口座名',
	hurigana varchar(30) COMMENT '口座名のフリカナ',
	accountNumber varchar(20) COMMENT '口座番号',
	depositeClassification varchar(10) COMMENT '預金区分',
	-- このデータが挿入された日時。又はこのデータが有効になった日時。
	insertDate datetime DEFAULT CURRENT_TIMESTAMP COMMENT '格納日時 : このデータが挿入された日時。又はこのデータが有効になった日時。',
	-- 更新日時
	updateDate datetime COMMENT 'updateDate : 更新日時',
	-- データの更新者のuserNum
	updater int COMMENT '更新者 : データの更新者のuserNum',
	PRIMARY KEY (accountInformNum)
) COMMENT = '口座情報';


-- 権限定義
CREATE TABLE auth
(
	-- u(ユーザ)、a(管理者)、sa(システム管理者)
	auth varchar(3) NOT NULL COMMENT '権限 : u(ユーザ)、a(管理者)、sa(システム管理者)',
	explanation varchar(80) COMMENT '権限説明',
	PRIMARY KEY (auth)
) COMMENT = '権限定義';


-- 請求書１
CREATE TABLE billSheet1
(
	documentNum varchar(20) NOT NULL COMMENT 'documentNum',
	-- ユーザ情報の固有ナンバー
	userNum int NOT NULL COMMENT '作成者 : ユーザ情報の固有ナンバー',
	userName varchar(30) COMMENT '作成者名前',
	userDepartment varchar(10) COMMENT '依頼部署',
	userPosition varchar(10) COMMENT '作成者役職',
	billDate varchar(20) COMMENT '作成日表示用',
	-- これがOZRが参照するデータテーブル名になる。
	-- 例）estimateSheet1 , BillSheet1
	documentTypeName varchar(20) DEFAULT 'billSheet1' NOT NULL COMMENT '文書種類の名前 : これがOZRが参照するデータテーブル名になる。
例）estimateSheet1 , BillSheet1',
	supplier varchar(40) COMMENT '供給者',
	address varchar(120) COMMENT '供給者住所',
	post varchar(10) COMMENT '供給者郵便番号',
	phoneNumber varchar(20) COMMENT '供給者電話番号',
	representative varchar(30) COMMENT '代表者',
	stamp varchar(25) COMMENT '印鑑ファイル名',
	stampFileName varchar(25) COMMENT '印鑑ファイル名',
	-- logoイメージのファイル名
	logoFileName varchar(25) COMMENT 'logoFileName : logoイメージのファイル名',
	receiver varchar(40) COMMENT '顧客名',
	documentName varchar(40) COMMENT '件名',
	payCondition varchar(40) COMMENT 'お支払い条件',
	deadline varchar(20) COMMENT '支払期限',
	bankName varchar(30) COMMENT '銀行名',
	branchName varchar(20) COMMENT '支店名',
	accountName varchar(30) COMMENT '口座名',
	hurigana varchar(60) COMMENT '口座名フリカナ',
	accountNumber varchar(20) COMMENT '口座番号',
	depositeClassification varchar(10) COMMENT '預金区分',
	note varchar(400) COMMENT '備考',
	sum bigint COMMENT '総計',
	tax bigint COMMENT '税金',
	sumWithTax bigint COMMENT '総計税金込み',
	sumWithTax2 bigint COMMENT '総計税金込み２',
	comment varchar(300) COMMENT 'コメント',
	-- このデータが挿入された日時。又はこのデータが有効になった日時。
	insertDate datetime DEFAULT CURRENT_TIMESTAMP COMMENT '格納日時 : このデータが挿入された日時。又はこのデータが有効になった日時。',
	-- 更新日時
	updateDate datetime COMMENT 'updateDate : 更新日時',
	-- データの更新者のuserNum
	updater int COMMENT '更新者 : データの更新者のuserNum',
	PRIMARY KEY (documentNum)
) COMMENT = '請求書１';


-- 請求書１のアイテム
CREATE TABLE billSheet1Items
(
	rowNum int COMMENT '行数',
	item varchar(2) COMMENT '項目',
	itemName varchar(60) COMMENT '項目名',
	amount int COMMENT '数量',
	unit varchar(5) COMMENT '単位',
	unitPrice int COMMENT '単価',
	price bigint COMMENT '値段',
	documentNum varchar(20) NOT NULL COMMENT 'documentNum'
) COMMENT = '請求書１のアイテム';


-- 会社情報
CREATE TABLE companyInform
(
	-- このデータの主キー
	companyInformNum int NOT NULL AUTO_INCREMENT COMMENT '会社情報固有番号 : このデータの主キー',
	-- 会社情報の名前。会社情報選択の時この名前を見て選択するようになる。
	companyInformName varchar(40) NOT NULL COMMENT '会社情報のタイトル : 会社情報の名前。会社情報選択の時この名前を見て選択するようになる。',
	-- 会社名
	companyName varchar(40) COMMENT '会社名 : 会社名',
	representative varchar(30) COMMENT '代表者',
	phoneNumber varchar(15) COMMENT '電話番号',
	address varchar(120) COMMENT '住所',
	post varchar(10) COMMENT '郵便番号',
	email varchar(30) COMMENT 'email',
	-- このデータが挿入された日時。又はこのデータが有効になった日時。
	insertDate datetime DEFAULT CURRENT_TIMESTAMP COMMENT '格納日時 : このデータが挿入された日時。又はこのデータが有効になった日時。',
	-- 更新日時
	updateDate datetime COMMENT 'updateDate : 更新日時',
	-- データの更新者のuserNum
	updater int COMMENT '更新者 : データの更新者のuserNum',
	PRIMARY KEY (companyInformNum)
) COMMENT = '会社情報';


-- 部署定義
CREATE TABLE department
(
	departmentNum int NOT NULL AUTO_INCREMENT COMMENT '部署の固有番号',
	department varchar(20) NOT NULL COMMENT '部署名',
	PRIMARY KEY (departmentNum)
) COMMENT = '部署定義';


-- 新規テーブル
CREATE TABLE documentMaster
(
	documentNum varchar(20) NOT NULL COMMENT 'documentNum',
	systemNum int NOT NULL COMMENT 'systemNum',
	-- これがOZRが参照するデータテーブル名になる。
	-- 例）estimateSheet1 , BillSheet1
	documentTypeName varchar(20) NOT NULL COMMENT '文書種類の名前 : これがOZRが参照するデータテーブル名になる。
例）estimateSheet1 , BillSheet1',
	-- これがOZRが参照するデータテーブル名になる。
	-- 例）estimateSheet1 , BillSheet1
	nextDocumentTypeName varchar(20) COMMENT '文書種類の名前 : これがOZRが参照するデータテーブル名になる。
例）estimateSheet1 , BillSheet1',
	userNum int NOT NULL COMMENT '作成者',
	userName varchar(30) COMMENT '作成者名前',
	userDepartment varchar(10) COMMENT '依頼部署',
	userPosition varchar(10) COMMENT '作成者役職',
	workflowNum int COMMENT 'WF情報の固有番号',
	-- wri(作成中),req(依頼済),app(承認済)
	state varchar(3) NOT NULL COMMENT '状態コード : wri(作成中),req(依頼済),app(承認済)',
	-- このデータが挿入された日時。又はこのデータが有効になった日時。
	insertDate datetime COMMENT '格納日時 : このデータが挿入された日時。又はこのデータが有効になった日時。',
	-- 更新日時
	updateDate datetime COMMENT 'updateDate : 更新日時',
	-- データの更新者のuserNum
	updater int COMMENT '更新者 : データの更新者のuserNum',
	PRIMARY KEY (documentNum)
) COMMENT = '新規テーブル';


-- 文書状態定義
CREATE TABLE documentState
(
	-- wri(作成中),req(依頼済),app(承認済)
	state varchar(3) NOT NULL COMMENT '状態コード : wri(作成中),req(依頼済),app(承認済)',
	-- 作成中,　依頼済,　承認済
	name varchar(10) NOT NULL COMMENT '状態名 : 作成中,　依頼済,　承認済',
	PRIMARY KEY (state),
	UNIQUE (state)
) COMMENT = '文書状態定義';


-- 文書の種類
CREATE TABLE documentType
(
	-- これがOZRが参照するデータテーブル名になる。
	-- 例）estimateSheet1 , BillSheet1
	documentTypeName varchar(20) NOT NULL COMMENT '文書種類の名前 : これがOZRが参照するデータテーブル名になる。
例）estimateSheet1 , BillSheet1',
	-- 例）製品用見積書の書式
	explanation varchar(40) COMMENT '文書種類の説明 : 例）製品用見積書の書式',
	systemNum int NOT NULL COMMENT 'システムの固有番号',
	PRIMARY KEY (documentTypeName)
) COMMENT = '文書の種類';


-- 新規テーブル
CREATE TABLE estimateMaster
(
	documentNum varchar(20) NOT NULL COMMENT 'documentNum',
	receiver varchar(40) COMMENT '顧客名',
	documentName varchar(40) COMMENT '件名',
	-- 関われている見積書のdocumentNum
	estimateNum varchar(20) COMMENT 'estimateNum : 関われている見積書のdocumentNum',
	PRIMARY KEY (documentNum),
    UNIQUE (estimateNum)
) COMMENT = '新規テーブル';


-- 見積書１
CREATE TABLE estimateSheet1
(
	documentNum varchar(20) NOT NULL COMMENT 'documentNum',
	-- ユーザ情報の固有ナンバー
	userNum int NOT NULL COMMENT '作成者 : ユーザ情報の固有ナンバー',
	userName varchar(30) COMMENT '作成者名前',
	userDepartment varchar(10) COMMENT '依頼部署',
	userPosition varchar(10) COMMENT '作成者役職',
	estimateDate varchar(20) COMMENT '作成日表示用',
	-- これがOZRが参照するデータテーブル名になる。
	-- 例）estimateSheet1 , BillSheet1
	documentTypeName varchar(20) DEFAULT 'estimateSheet1' NOT NULL COMMENT '文書種類の名前 : これがOZRが参照するデータテーブル名になる。
例）estimateSheet1 , BillSheet1',
	supplier varchar(40) COMMENT '供給者',
	address varchar(180) COMMENT '供給者住所',
	post varchar(10) COMMENT '供給者郵便番号',
	phoneNumber varchar(20) COMMENT '供給者電話番号',
	representative varchar(30) COMMENT '代表者',
	stamp varchar(25) COMMENT '印鑑',
	stampFileName varchar(25) COMMENT '印鑑ファイル名',
	-- logoイメージのファイル名
	logoFileName varchar(25) COMMENT 'logoFileName : logoイメージのファイル名',
	receiver varchar(40) COMMENT '顧客名',
	documentName varchar(40) COMMENT '件名',
	deadline varchar(20) COMMENT '納入期限',
	supplyPoint varchar(120) COMMENT '納入場所',
	expirationDate varchar(40) COMMENT '見積書の有効期限',
	payCondition varchar(40) COMMENT '支払い条件',
	cautions varchar(400) COMMENT '注意事項',
	sum bigint COMMENT '総計',
	tax bigint COMMENT '税金',
	sumWithTax bigint COMMENT '総計税金込み',
	sumWithTax2 bigint COMMENT '総計税金込み２',
	comment varchar(300) COMMENT 'コメント',
	-- このデータが挿入された日時。又はこのデータが有効になった日時。
	insertDate datetime DEFAULT CURRENT_TIMESTAMP COMMENT '格納日時 : このデータが挿入された日時。又はこのデータが有効になった日時。',
	-- 更新日時
	updateDate datetime COMMENT 'updateDate : 更新日時',
	-- データの更新者のuserNum
	updater int COMMENT '更新者 : データの更新者のuserNum',
	PRIMARY KEY (documentNum)
) COMMENT = '見積書１';


-- 見積書１のアイテム
CREATE TABLE estimateSheet1Items
(
	rowNum int COMMENT '行数',
	item varchar(2) COMMENT '項目',
	itemName varchar(60) COMMENT '項目名',
	amount int COMMENT '数量',
	unit varchar(5) COMMENT '単位',
	unitPrice int COMMENT '単価',
	price bigint COMMENT '値段',
	documentNum varchar(20) NOT NULL COMMENT 'documentNum'
) COMMENT = '見積書１のアイテム';


-- 参照ファイル名
CREATE TABLE fileNames
(
	category varchar(20) NOT NULL COMMENT 'ファイル種類',
	-- 印鑑イメージのファイル名
	fileName varchar(25) COMMENT 'ファイル名 : 印鑑イメージのファイル名',
	PRIMARY KEY (category),
	UNIQUE (fileName)
) COMMENT = '参照ファイル名';


-- sequence定義
CREATE TABLE masterSeq
(
	-- sequence番号
	id int NOT NULL COMMENT 'id : sequence番号',
	seqName varchar(20) NOT NULL COMMENT 'sequence名前'
) COMMENT = 'sequence定義';


-- 役職定義
CREATE TABLE position
(
	positionNum int NOT NULL AUTO_INCREMENT COMMENT '役職の固有番号',
	position varchar(10) NOT NULL COMMENT '役職名',
	PRIMARY KEY (positionNum)
) COMMENT = '役職定義';


-- システム定義
CREATE TABLE systemType
(
	systemNum int NOT NULL COMMENT 'システムの固有番号',
	-- 例）見積システム、請求システム
	systemName varchar(20) NOT NULL COMMENT '文書種類の名前 : 例）見積システム、請求システム',
	explanation varchar(80) COMMENT '説明',
	PRIMARY KEY (systemNum)
) COMMENT = 'システム定義';


-- ユーザ情報
CREATE TABLE userInform
(
	-- ユーザ情報の固有ナンバー
	userNum int NOT NULL AUTO_INCREMENT COMMENT 'ユーザ情報の固有番号 : ユーザ情報の固有ナンバー',
	-- ユーザがログインするとき使うID
	userId varchar(30) NOT NULL COMMENT 'ユーザID : ユーザがログインするとき使うID',
	-- ユーザがログインするとき使う暗証番号。
	password varchar(15) NOT NULL COMMENT '暗証番号 : ユーザがログインするとき使う暗証番号。',
	-- ユーザの名前
	userName varchar(30) NOT NULL COMMENT '名前 : ユーザの名前',
	departmentNum int COMMENT '所属Num',
	positionNum int COMMENT '役職の固有番号',
	-- u(ユーザ)、a(管理者)、sa(システム管理者)
	auth varchar(3) NOT NULL COMMENT '権限 : u(ユーザ)、a(管理者)、sa(システム管理者)',
	-- ユーザの携帯電話番号。
	phoneNumber varchar(15) COMMENT 'phoneNumber : ユーザの携帯電話番号。',
	-- メールアドレス
	email varchar(30) COMMENT 'email : メールアドレス',
	-- このユーザの状態。在職、休業、削除、退職
	state varchar(3) DEFAULT '在職' COMMENT '状態 : このユーザの状態。在職、休業、削除、退職',
	-- ユーザがログインする時更新。
	loginDate datetime COMMENT '最近接続日時 : ユーザがログインする時更新。',
	-- このデータが挿入された日時。又はこのデータが有効になった日時。
	insertDate datetime DEFAULT CURRENT_TIMESTAMP COMMENT '格納日時 : このデータが挿入された日時。又はこのデータが有効になった日時。',
	-- 更新日時
	updateDate datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'updateDate : 更新日時',
	-- データの更新者のuserNum
	updater int DEFAULT 1 COMMENT '更新者 : データの更新者のuserNum',
	PRIMARY KEY (userNum),
	UNIQUE (userId)
) COMMENT = 'ユーザ情報';


-- WF状況テーブル
CREATE TABLE workflow
(
	workflowNum int NOT NULL AUTO_INCREMENT COMMENT 'WF情報の固有番号',
	-- ユーザ情報の固有ナンバー
	userNum int COMMENT 'ユーザ情報の固有番号 : ユーザ情報の固有ナンバー',
	documentTypeNum int NOT NULL COMMENT '文書種類の固有番号',
	-- このWFを使っている文書番号
	documentNum varchar(20) COMMENT '文書番号 : このWFを使っている文書番号',
	approver1 int COMMENT '承認者１',
	approver2 int COMMENT '承認者２',
	approver3 int COMMENT '承認者３',
	-- n(not yet), a(approved)
	approver1State varchar(1) DEFAULT 'n' COMMENT '承認者１の承認状態 : n(not yet), a(approved)',
	-- n(not yet), a(approved)
	approver2State varchar(1) DEFAULT 'n' COMMENT '承認者２の承認状態 : n(not yet), a(approved)',
	-- n(not yet), a(approved)
	approver3State varchar(1) DEFAULT 'n' COMMENT '承認者３の承認状態 : n(not yet), a(approved)',
	-- 現在approver1,2,3のなかで何の段階か
	presentApproverNum int DEFAULT 1 COMMENT '現在承認者順番 : 現在approver1,2,3のなかで何の段階か',
	presentApprover int COMMENT '現在承認者',
	-- 承認済みの判断条件になる。
	targetKey varchar(5) COMMENT 'targetKey : 承認済みの判断条件になる。',
	targetValue varchar(5) COMMENT 'targetValue',
	-- このデータが挿入された日時。又はこのデータが有効になった日時。
	insertDate datetime DEFAULT CURRENT_TIMESTAMP COMMENT '格納日時 : このデータが挿入された日時。又はこのデータが有効になった日時。',
	-- 更新日時
	updateDate datetime COMMENT 'updateDate : 更新日時',
	-- データの更新者のuserNum
	updater int COMMENT '更新者 : データの更新者のuserNum',
	systemNum int NOT NULL COMMENT 'システムの固有番号',
	PRIMARY KEY (workflowNum),
	UNIQUE (documentNum)
) COMMENT = 'WF状況テーブル';


-- WF情報定義
CREATE TABLE workflowInform
(
	workflowInformNum int NOT NULL AUTO_INCREMENT COMMENT 'WF情報の固有番号',
	approver1 int DEFAULT -1 COMMENT '承認者１',
	approver2 int DEFAULT -1 COMMENT '承認者２',
	approver3 int DEFAULT -1 COMMENT '承認者３',
	-- 承認済みの判断条件になる。
	targetKey varchar(5) DEFAULT 'aaa' COMMENT 'targetKey : 承認済みの判断条件になる。',
	-- このデータが挿入された日時。又はこのデータが有効になった日時。
	insertDate datetime DEFAULT CURRENT_TIMESTAMP COMMENT '格納日時 : このデータが挿入された日時。又はこのデータが有効になった日時。',
	-- 更新日時
	updateDate datetime COMMENT 'updateDate : 更新日時',
	-- データの更新者のuserNum
	updater int COMMENT '更新者 : データの更新者のuserNum',
	systemNum int NOT NULL COMMENT 'システムの固有番号',
	PRIMARY KEY (workflowInformNum),
	UNIQUE (systemNum)
) COMMENT = 'WF情報定義';



/* Create Foreign Keys */

ALTER TABLE userInform
	ADD FOREIGN KEY (auth)
	REFERENCES auth (auth)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE billSheet1Items
	ADD FOREIGN KEY (documentNum)
	REFERENCES billSheet1 (documentNum)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;


ALTER TABLE userInform
	ADD FOREIGN KEY (departmentNum)
	REFERENCES department (departmentNum)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE estimateMaster
	ADD FOREIGN KEY (documentNum)
	REFERENCES documentMaster (documentNum)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;


ALTER TABLE documentMaster
	ADD FOREIGN KEY (state)
	REFERENCES documentState (state)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE documentMaster
	ADD FOREIGN KEY (documentTypeName)
	REFERENCES documentType (documentTypeName)
	ON UPDATE CASCADE
	ON DELETE RESTRICT
;


ALTER TABLE documentMaster
	ADD FOREIGN KEY (nextDocumentTypeName)
	REFERENCES documentType (documentTypeName)
	ON UPDATE CASCADE
	ON DELETE RESTRICT
;


ALTER TABLE billSheet1
	ADD FOREIGN KEY (documentNum)
	REFERENCES estimateMaster (documentNum)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;


ALTER TABLE estimateSheet1
	ADD FOREIGN KEY (documentNum)
	REFERENCES estimateMaster (documentNum)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;


ALTER TABLE estimateSheet1Items
	ADD FOREIGN KEY (documentNum)
	REFERENCES estimateSheet1 (documentNum)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;


ALTER TABLE userInform
	ADD FOREIGN KEY (positionNum)
	REFERENCES position (positionNum)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE documentType
	ADD FOREIGN KEY (systemNum)
	REFERENCES systemType (systemNum)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE workflow
	ADD FOREIGN KEY (systemNum)
	REFERENCES systemType (systemNum)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE workflowInform
	ADD FOREIGN KEY (systemNum)
	REFERENCES systemType (systemNum)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE billSheet1
	ADD FOREIGN KEY (userNum)
	REFERENCES userInform (userNum)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE estimateSheet1
	ADD FOREIGN KEY (userNum)
	REFERENCES userInform (userNum)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE documentMaster
	ADD FOREIGN KEY (workflowNum)
	REFERENCES workflow (workflowNum)
	ON UPDATE RESTRICT
	ON DELETE SET NULL
;



-- 役職
INSERT INTO `interline_estimatesystem`.`position` (`position`) VALUES ('社員');
INSERT INTO `interline_estimatesystem`.`position` (`position`) VALUES ('主任');
INSERT INTO `interline_estimatesystem`.`position` (`position`) VALUES ('代理');
INSERT INTO `interline_estimatesystem`.`position` (`position`) VALUES ('課長');
INSERT INTO `interline_estimatesystem`.`position` (`position`) VALUES ('社長');


-- 部署
INSERT INTO `interline_estimatesystem`.`department` (`department`) VALUES ('営業日');
INSERT INTO `interline_estimatesystem`.`department` (`department`) VALUES ('SI事業部');
INSERT INTO `interline_estimatesystem`.`department` (`department`) VALUES ('人事総務部');
INSERT INTO `interline_estimatesystem`.`department` (`department`) VALUES ('経理部');
INSERT INTO `interline_estimatesystem`.`department` (`department`) VALUES ('代表');


-- 権限
INSERT INTO `interline_estimatesystem`.`auth` (`auth`, `explanation`) VALUES ('sa', 'system admin.');
INSERT INTO `interline_estimatesystem`.`auth` (`auth`, `explanation`) VALUES ('a', 'normal admin.');
INSERT INTO `interline_estimatesystem`.`auth` (`auth`, `explanation`) VALUES ('u', 'normal user.');

-- files
INSERT INTO `interline_estimatesystem`.`filenames` (`category`, `fileName`) VALUES ('logo', 'defaultLogo.png');
INSERT INTO `interline_estimatesystem`.`filenames` (`category`, `fileName`) VALUES ('stamp', 'defaultStamp.png');

-- system
INSERT INTO `interline_estimatesystem`.`systemtype` (`systemNum`,`systemName`) VALUES (1,'estimateSystem');
INSERT INTO `interline_estimatesystem`.`systemtype` (`systemNum`,`systemName`) VALUES (2,'billSystem');

INSERT INTO `interline_estimatesystem`.`documenttype` (`documentTypeName`,`systemNum`) VALUES ('estimateSheet1',1);
INSERT INTO `interline_estimatesystem`.`documenttype` (`documentTypeName`,`systemNum`) VALUES ('billSheet1',2);

INSERT INTO `interline_estimatesystem`.`workflowinform` (`systemNum`) VALUES ('1');
INSERT INTO `interline_estimatesystem`.`workflowinform` (`systemNum`) VALUES ('2');

INSERT INTO `interline_estimatesystem`.`documentstate` (`state`, `name`) VALUES ('wri', '作成中');
INSERT INTO `interline_estimatesystem`.`documentstate` (`state`, `name`) VALUES ('req', '依頼済');
INSERT INTO `interline_estimatesystem`.`documentstate` (`state`, `name`) VALUES ('app', '承認済');



-- system管理者
INSERT INTO `interline_estimatesystem`.`userinform` (`userId`, `password`, `userName`, `auth`) VALUES ('sadmin', 'sadmin', 'sadmin', 'sa');

-- 管理者
INSERT INTO `interline_estimatesystem`.`userinform` (`userId`, `password`, `userName`, `auth`) VALUES ('admin', 'admin', 'admin', 'a');

-- testユーザ
INSERT INTO `interline_estimatesystem`.`userinform` (`userId`, `password`, `userName`, `auth`) VALUES ('test', 'test', 'test', 'u');
INSERT INTO `interline_estimatesystem`.`userinform` (`userId`, `password`, `userName`, `auth`) VALUES ('test2', 'test2', 'test2', 'u');






-- ERM에서 작성이안되어 따로 작성함
ALTER TABLE workflow
	ADD FOREIGN KEY (documentNum)
	REFERENCES documentMaster (documentNum)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;


INSERT INTO masterSeq VALUES (0, 'estimateSeq');
INSERT INTO masterSeq VALUES (0, 'billSeq');


CREATE EVENT IF NOT EXISTS setSeqZero
    ON SCHEDULE
           EVERY 1 DAY
           STARTS '2021-06-16 23:59:59'
    ON COMPLETION NOT PRESERVE
    ENABLE
    COMMENT 'setSeqZero'
    DO 
	update materSeq set id = 0;
    

DROP FUNCTION IF EXISTS getSeq;  /*혹시 이미 만들어져 있다면 삭제합니다.*/
DELIMITER $$
CREATE FUNCTION getSeq (p_seq_name VARCHAR(45))
RETURNS INT READS SQL DATA
BEGIN
DECLARE RESULT_ID INT;
UPDATE masterSeq SET id = LAST_INSERT_ID(id+1) WHERE seqName = p_seq_name;
SET RESULT_ID = (SELECT LAST_INSERT_ID());
RETURN RESULT_ID;
END $$
DELIMITER ;