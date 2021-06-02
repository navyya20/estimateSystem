use interline_EstimateSystem;
SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS accountInform;
DROP TABLE IF EXISTS billSheet1Items;
DROP TABLE IF EXISTS billSheet1;
DROP TABLE IF EXISTS estimateSheet1Items;
DROP TABLE IF EXISTS estimateSheet1;
DROP TABLE IF EXISTS userInform;
DROP TABLE IF EXISTS auth;
DROP TABLE IF EXISTS companyInform;
DROP TABLE IF EXISTS department;
DROP TABLE IF EXISTS documentState;
DROP TABLE IF EXISTS documentType;
DROP TABLE IF EXISTS fileNames;
DROP TABLE IF EXISTS masterSeq;
DROP TABLE IF EXISTS position;
DROP TABLE IF EXISTS workFlow;
DROP TABLE IF EXISTS workFlowInform;
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
	insertDate datetime default CURRENT_TIMESTAMP COMMENT '格納日時 : このデータが挿入された日時。又はこのデータが有効になった日時。',
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
	documentNum varchar(20) NOT NULL COMMENT '請求書の固有番号',
	estimateNum varchar(20) NOT NULL COMMENT '見積書の固有番号',
	-- ユーザ情報の固有ナンバー
	userNum int NOT NULL COMMENT '作成者 : ユーザ情報の固有ナンバー',
	userName varchar(20) COMMENT '作成者名前',
	userDepartment varchar(10) COMMENT '依頼部署',
	userPosition varchar(10) COMMENT '作成者役職',
	billDate varchar(20) COMMENT '作成日表示用',
	documentTypeNum int NOT NULL COMMENT '文書種類の固有番号',
	supplier varchar(40) COMMENT '供給者',
	address varchar(120) COMMENT '供給者住所',
	post varchar(10) COMMENT '供給者郵便番号',
	phoneNumber varchar(20) COMMENT '供給者電話番号',
	representative varchar(30) COMMENT '代表者',
	stamp varchar(20) COMMENT '印鑑',
	-- 印鑑イメージのファイル名
	stampFileName varchar(20) NOT NULL COMMENT '印鑑 : 印鑑イメージのファイル名',
	-- logoイメージのファイル名
	logoFileName varchar(20) NOT NULL COMMENT 'logoFileName : logoイメージのファイル名',
	logoFileName varchar(20) COMMENT 'logoファイル名',
	reciever varchar(40) COMMENT '顧客名',
	documentName varchar(40) COMMENT '件名',
	payCondition varchar(40) COMMENT 'お支払い条条件',
	deadline varchar(20) COMMENT '支払期限',
	bankName varchar(30) COMMENT '銀行名',
	branchName varchar(20) COMMENT '支店名',
	acountName varchar(30) COMMENT '口座名',
	hurigana varchar(60) COMMENT '口座名フリカナ',
	acountNumber varchar(20) COMMENT '口座番号',
	depositeClassification varchar(10) COMMENT '預金区分',
	condition varchar(400) COMMENT '備考',
	-- wri(作成中),req(依頼済),app(承認済)
	state varchar(3) NOT NULL COMMENT '状態コード : wri(作成中),req(依頼済),app(承認済)',
	comment varchar(300) COMMENT 'コメント',
	workflowNum int COMMENT 'WF情報の固有番号',
	-- このデータが挿入された日時。又はこのデータが有効になった日時。
	insertDate datetime default CURRENT_TIMESTAMP COMMENT '格納日時 : このデータが挿入された日時。又はこのデータが有効になった日時。',
	-- 更新日時
	updateDate datetime COMMENT 'updateDate : 更新日時',
	-- データの更新者のuserNum
	updater int COMMENT '更新者 : データの更新者のuserNum',
	PRIMARY KEY (documentNum)
) COMMENT = '請求書１';


-- 請求書１のアイテム
CREATE TABLE billSheet1Items
(
	documentNum varchar(20) NOT NULL COMMENT '請求書の固有番号',
	itemNum int COMMENT '行数',
	itemName varchar(60) COMMENT '項目名',
	itemAmount int COMMENT '数量',
	itemUnit varchar(5) COMMENT '単位',
	itemUnitPrice int COMMENT '単価',
	itemPrice bigint COMMENT '値段',
	itemSum bigint COMMENT '総合税抜き',
	itemTax bigint COMMENT '税金',
	itemSumWithTax bigint COMMENT '総合税金込み'
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
	insertDate datetime default CURRENT_TIMESTAMP COMMENT '格納日時 : このデータが挿入された日時。又はこのデータが有効になった日時。',
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
	documentTypeNum int NOT NULL AUTO_INCREMENT COMMENT '文書種類の固有番号',
	-- これがOZRが参照するデータテーブル名になる。
	-- 例）estimateSheet1 , BillSheet1
	documentType varchar(20) NOT NULL COMMENT '文書種類の名前 : これがOZRが参照するデータテーブル名になる。
例）estimateSheet1 , BillSheet1',
	-- 例）製品用見積書の書式
	explanation varchar(40) COMMENT '文書種類の説明 : 例）製品用見積書の書式',
	PRIMARY KEY (documentTypeNum),
	UNIQUE (documentType)
) COMMENT = '文書の種類';


-- 見積書１
CREATE TABLE estimateSheet1
(
	documentNum varchar(20) NOT NULL COMMENT '見積書の固有番号',
	-- ユーザ情報の固有ナンバー
	userNum int NOT NULL COMMENT '作成者 : ユーザ情報の固有ナンバー',
	userName varchar(30) COMMENT '作成者名前',
	userDepartment varchar(10) COMMENT '依頼部署',
	userPosition varchar(10) COMMENT '作成者役職',
	estimateDate varchar(20) COMMENT '作成日表示用',
	documentTypeNum int NOT NULL COMMENT '文書種類の固有番号',
	supplier varchar(40) COMMENT '供給者',
	address varchar(180) COMMENT '供給者住所',
	post varchar(10) COMMENT '供給者郵便番号',
	phoneNumber varchar(20) COMMENT '供給者電話番号',
	representative varchar(30) COMMENT '代表者',
	stamp varchar(20) COMMENT '印鑑',
	-- 印鑑イメージのファイル名
	stampFileName varchar(20) NOT NULL COMMENT '印鑑ファイル名 : 印鑑イメージのファイル名',
	-- logoイメージのファイル名
	logoFileName varchar(20) NOT NULL COMMENT 'logoファイル名 : logoイメージのファイル名',
	reciever varchar(40) COMMENT '顧客名',
	documentName varchar(40) COMMENT '件名',
	deadline varchar(20) COMMENT '納入期限',
	supplyPoint varchar(120) COMMENT '納入場所',
	expirationDate varchar(40) COMMENT '見積書の有効期限',
	payCondition varchar(40) COMMENT '支払い条件',
	cautions varchar(400) COMMENT '注意事項',
	-- wri(作成中),req(依頼済),app(承認済)
	state varchar(3) NOT NULL COMMENT '状態コード : wri(作成中),req(依頼済),app(承認済)',
	comment varchar(300) COMMENT 'コメント',
	workflowNum int COMMENT 'WF情報の固有番号',
	-- このデータが挿入された日時。又はこのデータが有効になった日時。
	insertDate datetime default CURRENT_TIMESTAMP COMMENT '格納日時 : このデータが挿入された日時。又はこのデータが有効になった日時。',
	-- 更新日時
	updateDate datetime COMMENT 'updateDate : 更新日時',
	-- データの更新者のuserNum
	updater int COMMENT '更新者 : データの更新者のuserNum',
	PRIMARY KEY (documentNum)
) COMMENT = '見積書１';


-- 見積書１のアイテム
CREATE TABLE estimateSheet1Items
(
	documentNum varchar(20) NOT NULL COMMENT '見積書の固有番号',
	itemNum int COMMENT '行数',
	itemName varchar(60) COMMENT '項目名',
	itemAmount int COMMENT '数量',
	itemUnit varchar(5) COMMENT '単位',
	itemUnitPrice int COMMENT '単価',
	itemPrice bigint COMMENT '値段',
	itemSum bigint COMMENT '総合税抜き',
	itemTax bigint COMMENT '税金',
	itemSumWithTax bigint COMMENT '総合税金込み'
) COMMENT = '見積書１のアイテム';


-- 参照ファイル名
CREATE TABLE fileNames
(
	fileNamesNum int NOT NULL AUTO_INCREMENT COMMENT 'ファイル名の固有番号',
	-- 印鑑イメージのファイル名
	stampFileName varchar(20) COMMENT '印鑑 : 印鑑イメージのファイル名',
	-- logoイメージのファイル名
	logoFileName varchar(20) COMMENT 'logoFileName : logoイメージのファイル名',
	PRIMARY KEY (fileNamesNum),
	UNIQUE (stampFileName),
	UNIQUE (logoFileName)
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
	systemNum int NOT NULL AUTO_INCREMENT COMMENT 'システムの固有番号',
	-- 例）見積システム、請求システム
	systemName varchar(20) NOT NULL COMMENT 'システムの名前 : 例）見積システム、請求システム',
	explanation varchar(80) COMMENT 'システムの説明',
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
	UNIQUE (uesrId)
) COMMENT = 'ユーザ情報';


-- WF状況テーブル
CREATE TABLE workFlow
(
	workflowNum int NOT NULL AUTO_INCREMENT COMMENT 'WF情報の固有番号',
	systemNum int NOT NULL COMMENT 'システムの固有番号',
	-- ユーザ情報の固有ナンバー
	userNum int COMMENT 'ユーザ情報の固有番号 : ユーザ情報の固有ナンバー',
	documentTypeNum int NOT NULL COMMENT '文書種類の固有番号',
	-- このWFを使っている文書番号
	documentNum varchar(20) COMMENT '文書番号 : このWFを使っている文書番号',
	approver1 int COMMENT '承認者１',
	approver2 int COMMENT '承認者２',
	approver3 int COMMENT '承認者３',
	-- n(not yet), a(approved)
	approver1state varchar(1) DEFAULT 'n' COMMENT '承認者１の承認状態 : n(not yet), a(approved)',
	-- n(not yet), a(approved)
	approver2state varchar(1) DEFAULT 'n' COMMENT '承認者２の承認状態 : n(not yet), a(approved)',
	-- n(not yet), a(approved)
	approver3state varchar(1) DEFAULT 'n' COMMENT '承認者３の承認状態 : n(not yet), a(approved)',
	-- 現在approver1,2,3のなかで何の段階か
	presentApproverNum int DEFAULT 1 COMMENT '現在承認者順番 : 現在approver1,2,3のなかで何の段階か',
	presentApprover int COMMENT '現在承認者',
	-- 承認済みの判断条件になる。
	targetKey varchar(5) COMMENT 'targetKey : 承認済みの判断条件になる。',
	-- 承認済みの判断条件になる。
	targetKey varchar(5) COMMENT 'targetKey : 承認済みの判断条件になる。',
	-- このデータが挿入された日時。又はこのデータが有効になった日時。
	insertDate datetime default CURRENT_TIMESTAMP COMMENT '格納日時 : このデータが挿入された日時。又はこのデータが有効になった日時。',
	-- 更新日時
	updateDate datetime COMMENT 'updateDate : 更新日時',
	-- データの更新者のuserNum
	updater int COMMENT '更新者 : データの更新者のuserNum',
	PRIMARY KEY (workflowNum)
) COMMENT = 'WF状況テーブル';


-- WF情報定義
CREATE TABLE workFlowInform
(
	workFlowInformNum int NOT NULL AUTO_INCREMENT COMMENT 'WF情報の固有番号',
	systemNum int NOT NULL COMMENT 'システムの固有番号',
	approver1 int COMMENT '承認者１',
	approver2 int COMMENT '承認者２',
	approver3 int COMMENT '承認者３',
	-- 承認済みの判断条件になる。
	targetKey varchar(5) COMMENT 'targetKey : 承認済みの判断条件になる。',
	-- このデータが挿入された日時。又はこのデータが有効になった日時。
	insertDate datetime default CURRENT_TIMESTAMP COMMENT '格納日時 : このデータが挿入された日時。又はこのデータが有効になった日時。',
	-- 更新日時
	updateDate datetime COMMENT 'updateDate : 更新日時',
	-- データの更新者のuserNum
	updater int COMMENT '更新者 : データの更新者のuserNum',
	PRIMARY KEY (workFlowInformNum),
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
	ON DELETE RESTRICT
;


ALTER TABLE userInform
	ADD FOREIGN KEY (departmentNum)
	REFERENCES department (departmentNum)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE billSheet1
	ADD FOREIGN KEY (state)
	REFERENCES documentState (state)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE estimateSheet1
	ADD FOREIGN KEY (state)
	REFERENCES documentState (state)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE billSheet1
	ADD FOREIGN KEY (documentTypeNum)
	REFERENCES documentType (documentTypeNum)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE estimateSheet1
	ADD FOREIGN KEY (documentTypeNum)
	REFERENCES documentType (documentTypeNum)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE billSheet1
	ADD FOREIGN KEY (estimateNum)
	REFERENCES estimateSheet1 (documentNum)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE estimateSheet1Items
	ADD FOREIGN KEY (documentNum)
	REFERENCES estimateSheet1 (documentNum)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE billSheet1
	ADD FOREIGN KEY (stampFileName)
	REFERENCES fileNames (stampFileName)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE billSheet1
	ADD FOREIGN KEY (logoFileName)
	REFERENCES fileNames (logoFileName)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE estimateSheet1
	ADD FOREIGN KEY (stampFileName)
	REFERENCES fileNames (stampFileName)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE estimateSheet1
	ADD FOREIGN KEY (logoFileName)
	REFERENCES fileNames (logoFileName)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE userInform
	ADD FOREIGN KEY (positionNum)
	REFERENCES position (positionNum)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE workFlow
	ADD FOREIGN KEY (systemNum)
	REFERENCES systemType (systemNum)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE workFlowInform
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


ALTER TABLE billSheet1
	ADD FOREIGN KEY (workflowNum)
	REFERENCES workFlow (workflowNum)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE estimateSheet1
	ADD FOREIGN KEY (workflowNum)
	REFERENCES workFlow (workflowNum)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;




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


INSERT INTO masterSeq VALUES (1, 'estimateSeq');

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


-- system管理者
INSERT INTO `interline_estimatesystem`.`userinform` (`userId`, `password`, `userName`, `auth`) VALUES ('sadmin', 'sadmin', 'sadmin', 'sa');

-- 管理者
INSERT INTO `interline_estimatesystem`.`userinform` (`userId`, `password`, `userName`, `auth`) VALUES ('admin', 'admin', 'admin', 'a');

-- testユーザ
INSERT INTO `interline_estimatesystem`.`userinform` (`userId`, `password`, `userName`, `auth`) VALUES ('test', 'test', 'test', 'u');
INSERT INTO `interline_estimatesystem`.`userinform` (`userId`, `password`, `userName`, `auth`) VALUES ('test2', 'test2', 'test2', 'u');