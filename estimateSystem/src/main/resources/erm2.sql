
use interline_EstimateSystem;
SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables  erm2 */

DROP TABLE IF EXISTS accountInform;
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
DROP TABLE IF EXISTS position2;
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
	phoneNumber varchar(20) COMMENT '電話番号',
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
	documentTypeName varchar(20) NOT NULL COMMENT '文書種類の名前 : これがOZRが参照するデータテーブル名になる。',
	-- これがOZRが参照するデータテーブル名になる。
	nextDocumentTypeName varchar(20) COMMENT '文書種類の名前 : これがOZRが参照するデータテーブル名になる。',
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
	documentTypeName varchar(20) NOT NULL COMMENT '文書種類の名前 : これがOZRが参照するデータテーブル名になる。',
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


-- 見積書[Solution事業部]
CREATE TABLE estimateSolution
(
	documentNum varchar(20) NOT NULL COMMENT 'documentNum',
	-- ユーザ情報の固有ナンバー
	userNum int NOT NULL COMMENT '作成者 : ユーザ情報の固有ナンバー',
	userName varchar(30) COMMENT '作成者名前',
	userDepartment varchar(10) COMMENT '依頼部署',
	userPosition varchar(10) COMMENT '作成者役職',
	estimateDate varchar(20) COMMENT '作成日表示用',
	-- これがOZRが参照するデータテーブル名になる。
	-- 例）estimateSolution , BillSolution
	documentTypeName varchar(20) DEFAULT 'estimateSolution' NOT NULL COMMENT '文書種類の名前 : これがOZRが参照するデータテーブル名になる。
例）estimateSolution , BillSolution',
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
    taxRate int ,
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


-- 見積書１のアイテム[Solution事業部]
CREATE TABLE estimateSolutionItems
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
CREATE TABLE position2
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
	password varchar(20) NOT NULL COMMENT '暗証番号 : ユーザがログインするとき使う暗証番号。',
	-- ユーザの名前
	userName varchar(30) NOT NULL COMMENT '名前 : ユーザの名前',
	departmentNum int COMMENT '所属Num',
	positionNum int COMMENT '役職の固有番号',
	-- u(ユーザ)、a(管理者)、sa(システム管理者)
	auth varchar(3) NOT NULL COMMENT '権限 : u(ユーザ)、a(管理者)、sa(システム管理者)',
	-- ユーザの携帯電話番号。
	phoneNumber varchar(20) COMMENT 'phoneNumber : ユーザの携帯電話番号。',
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
	documentTypeName varchar(20) NOT NULL COMMENT '文書種類',
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


ALTER TABLE userInform
	ADD FOREIGN KEY (positionNum)
	REFERENCES position2 (positionNum)
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


ALTER TABLE documentMaster
	ADD FOREIGN KEY (workflowNum)
	REFERENCES workflow (workflowNum)
	ON UPDATE RESTRICT
	ON DELETE SET NULL
;


ALTER TABLE estimateSolution
	ADD FOREIGN KEY (documentNum)
	REFERENCES estimateMaster (documentNum)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;

ALTER TABLE estimateSolutionItems
	ADD FOREIGN KEY (documentNum)
	REFERENCES estimateSolution (documentNum)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;



-- 役職
INSERT INTO `interline_estimatesystem`.`position2` (`position`) VALUES ('社員');
INSERT INTO `interline_estimatesystem`.`position2` (`position`) VALUES ('主任');
INSERT INTO `interline_estimatesystem`.`position2` (`position`) VALUES ('代理');
INSERT INTO `interline_estimatesystem`.`position2` (`position`) VALUES ('課長');
INSERT INTO `interline_estimatesystem`.`position2` (`position`) VALUES ('社長');


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

INSERT INTO `interline_estimatesystem`.`documenttype` (`documentTypeName`, `systemNum`) VALUES ('estimateSolution', '1');
INSERT INTO `interline_estimatesystem`.`documenttype` (`documentTypeName`, `systemNum`) VALUES ('billSolution', '2');
INSERT INTO `interline_estimatesystem`.`documenttype` (`documentTypeName`, `systemNum`) VALUES ('estimateLanguage', '1');

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


-- 請求書2 solution事業部
CREATE TABLE billSolution
(
	documentNum varchar(20) NOT NULL COMMENT 'documentNum',
	-- ユーザ情報の固有ナンバー
	userNum int NOT NULL COMMENT '作成者 : ユーザ情報の固有ナンバー',
	userName varchar(30) COMMENT '作成者名前',
	userDepartment varchar(10) COMMENT '依頼部署',
	userPosition varchar(10) COMMENT '作成者役職',
	billDate varchar(20) COMMENT '作成日表示用',
	-- これがOZRが参照するデータテーブル名になる。
	-- 例）billSolution.ozr
	documentTypeName varchar(20) DEFAULT 'billSolution' NOT NULL COMMENT '文書種類の名前 : これがOZRが参照するデータテーブル名になる。',
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
    taxRate int ,
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


-- 請求書2 solution事業部のアイテム
CREATE TABLE billSolutionItems
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

ALTER TABLE billSolution
	ADD FOREIGN KEY (documentNum)
	REFERENCES estimateMaster (documentNum)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;

ALTER TABLE billSolutionItems
	ADD FOREIGN KEY (documentNum)
	REFERENCES billSolution (documentNum)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;



-- 見積書[語学事業部]
CREATE TABLE estimateLanguage
(
	documentNum varchar(20) NOT NULL COMMENT 'documentNum',
	-- ユーザ情報の固有ナンバー
	userNum int NOT NULL COMMENT '作成者 : ユーザ情報の固有ナンバー',
	userName varchar(30) COMMENT '作成者名前',
	userDepartment varchar(10) COMMENT '依頼部署',
	userPosition varchar(10) COMMENT '作成者役職',
	estimateDate varchar(20) COMMENT '作成日表示用',
	-- これがOZRが参照するデータテーブル名になる。
	-- 例）estimateSolution , BillSolution
	documentTypeName varchar(20) DEFAULT 'estimateLanguage' NOT NULL COMMENT '文書種類の名前 : これがOZRが参照するデータテーブル名になる。',
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
	expirationDate varchar(40) COMMENT '見積書の有効期限',
	payCondition varchar(40) COMMENT '支払い条件',
    bankName varchar(30) COMMENT '銀行名',
	branchName varchar(20) COMMENT '支店名',
	accountName varchar(30) COMMENT '口座名',
	hurigana varchar(60) COMMENT '口座名フリカナ',
	accountNumber varchar(20) COMMENT '口座番号',
	depositeClassification varchar(10) COMMENT '預金区分',
	cautions varchar(400) COMMENT '注意事項',
	sum bigint COMMENT '総計',
    discountName varchar(20),
    discountRate int ,
    discount bigint ,
    taxRate int ,
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


-- 見積書１のアイテム[Solution事業部]
CREATE TABLE estimateLanguageItems
(
	rowNum int COMMENT '行数',
	itemName varchar(60) COMMENT '項目名',
	amount int COMMENT '数量',
	unitPrice int COMMENT '単価',
	price bigint COMMENT '値段',
	documentNum varchar(20) NOT NULL COMMENT 'documentNum'
) COMMENT = '語学事業部見積書';

ALTER TABLE estimateLanguage
	ADD FOREIGN KEY (documentNum)
	REFERENCES estimateMaster (documentNum)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;

ALTER TABLE estimateLanguageItems
	ADD FOREIGN KEY (documentNum)
	REFERENCES estimateLanguage (documentNum)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;



INSERT INTO `interline_estimatesystem`.`documenttype` (`documentTypeName`, `systemNum`) VALUES ('estimateSi', '1');
INSERT INTO `interline_estimatesystem`.`documenttype` (`documentTypeName`, `systemNum`) VALUES ('billSi', '2');

-- 見積書[SI事業部]
CREATE TABLE estimateSi
(
	documentNum varchar(20) NOT NULL COMMENT 'documentNum',
	-- ユーザ情報の固有ナンバー
	userNum int NOT NULL COMMENT '作成者 : ユーザ情報の固有ナンバー',
	userName varchar(30) COMMENT '作成者名前',
	userDepartment varchar(10) COMMENT '依頼部署',
	userPosition varchar(10) COMMENT '作成者役職',
	estimateDate varchar(20) COMMENT '作成日表示用',
	-- これがOZRが参照するデータテーブル名になる。
	-- 例）estimateSolution , BillSolution
	documentTypeName varchar(20) DEFAULT 'estimateSi' NOT NULL COMMENT '文書種類の名前 : これがOZRが参照するデータテーブル名になる。',
	supplier varchar(40) COMMENT '供給者',
	address varchar(180) COMMENT '供給者住所',
	post varchar(10) COMMENT '供給者郵便番号',
	phoneNumber varchar(20) COMMENT '供給者電話番号',
	representative varchar(30) COMMENT '代表者',
	stamp varchar(25) COMMENT '印鑑',
	stampFileName varchar(25) COMMENT '印鑑ファイル名',
	-- logoイメージのファイル名
	logoFileName varchar(25) COMMENT 'logoFileName : logoイメージのファイル名',
	receiver varchar(100) COMMENT '顧客名',
	documentName varchar(100) COMMENT '件名',
    workTime varchar(100) COMMENT '作業時間',
    supplyment varchar(100) COMMENT '納品物',
    workPeriodStart varchar(20),
    workPeriodEnd varchar(20),
    workPlace varchar(40),
    contractType varchar(40),
	payCondition varchar(40) COMMENT '支払い条件',
	expirationDate varchar(40) COMMENT '見積書の有効期限',
    requestNum varchar(40),
	cautions varchar(400) COMMENT '注意事項',
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
) COMMENT = 'SI見積書';


-- SI見積書のアイテム[SI事業部]
CREATE TABLE estimateSiItems
(
	rowNum int COMMENT '行数',
    item varchar(2) COMMENT '項目名',
	itemName varchar(30) COMMENT '項目名',
	workStart varchar(12),
    workEnd varchar(12),
	unitPrice int COMMENT '単価',
    manMonth DECIMAL(4,1),
	price bigint COMMENT '値段',
	documentNum varchar(20) NOT NULL COMMENT 'documentNum'
) COMMENT = 'SI見積書のアイテム';

ALTER TABLE estimateSi
	ADD FOREIGN KEY (documentNum)
	REFERENCES estimateMaster (documentNum)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;

ALTER TABLE estimateSiItems
	ADD FOREIGN KEY (documentNum)
	REFERENCES estimateSi (documentNum)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;

-- 請求書[SI事業部]
CREATE TABLE billSi
(
	documentNum varchar(20) NOT NULL COMMENT 'documentNum',
	-- ユーザ情報の固有ナンバー
	userNum int NOT NULL COMMENT '作成者 : ユーザ情報の固有ナンバー',
	userName varchar(30) COMMENT '作成者名前',
	userDepartment varchar(10) COMMENT '依頼部署',
	userPosition varchar(10) COMMENT '作成者役職',
	billDate varchar(20) COMMENT '作成日表示用',
	-- これがOZRが参照するデータテーブル名になる。
	-- 例）estimateSolution , BillSolution
	documentTypeName varchar(20) DEFAULT 'billSi' NOT NULL COMMENT '文書種類の名前 : これがOZRが参照するデータテーブル名になる。',
	supplier varchar(40) COMMENT '供給者',
	address varchar(180) COMMENT '供給者住所',
	post varchar(10) COMMENT '供給者郵便番号',
	phoneNumber varchar(20) COMMENT '供給者電話番号',
	representative varchar(30) COMMENT '代表者',
	stamp varchar(25) COMMENT '印鑑',
	stampFileName varchar(25) COMMENT '印鑑ファイル名',
	-- logoイメージのファイル名
	logoFileName varchar(25) COMMENT 'logoFileName : logoイメージのファイル名',
	
    receiver varchar(100) COMMENT '顧客名',
	documentName varchar(100) COMMENT '件名',
    workPeriodStart varchar(20),
    workPeriodEnd varchar(20),
    deadline varchar(20),
    
    bankName varchar(30) COMMENT '銀行名',
	branchName varchar(20) COMMENT '支店名',
	accountName varchar(30) COMMENT '口座名',
	hurigana varchar(60) COMMENT '口座名フリカナ',
	accountNumber varchar(20) COMMENT '口座番号',
	depositeClassification varchar(10) COMMENT '預金区分',
    
    note varchar(200) COMMENT '注意事項',
    expenseTotal int,
    benefitTotal int,
    total int,
    sum bigint COMMENT '総計',
    taxRate int ,
	tax bigint COMMENT '税金',
	sumWithTax bigint COMMENT '総計税金込み',
	sumWithTax2 bigint COMMENT '総計税金込み２',
    
	insertDate datetime DEFAULT CURRENT_TIMESTAMP COMMENT '格納日時 : このデータが挿入された日時。又はこのデータが有効になった日時。',
	updateDate datetime COMMENT 'updateDate : 更新日時',
	updater int COMMENT '更新者 : データの更新者のuserNum',
	PRIMARY KEY (documentNum)
) COMMENT = 'SI請求書';


-- SI請求書のアイテム[SI事業部]
CREATE TABLE billSiItems
(
	rowNum int COMMENT '行数',
    monthlyUnitPrice int,
	standardMin int,
    standardMax int,
    workTime DECIMAL(5,2),
    extraTime DECIMAL(5,2),
    overTimeUnitPrice int,
    underTimeUnitPrice int,
	price bigint COMMENT '値段',
    expense int,
    benefit int,
    subtotal int,
	documentNum varchar(20) NOT NULL COMMENT 'documentNum'
) COMMENT = 'SI見積書のアイテム';

ALTER TABLE billSi
	ADD FOREIGN KEY (documentNum)
	REFERENCES estimateMaster (documentNum)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;

ALTER TABLE billSiItems
	ADD FOREIGN KEY (documentNum)
	REFERENCES billSi (documentNum)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;





-- 매 0시마다 인덱스를 0으로 세팅
DROP EVENT IF EXISTS setSeqZero;
CREATE EVENT IF NOT EXISTS setSeqZero
    ON SCHEDULE
           EVERY 1 DAY
           STARTS '2021-06-16 23:59:59'
    ON COMPLETION PRESERVE
    ENABLE
    COMMENT 'setSeqZero'
    DO 
		update masterSeq set id = 0 where seqName in ('estimateSeq','billSeq');


    
alter table companyinform add incharge varchar(30) COMMENT '文書(業務)の担当者';
alter table estimatesolution add incharge varchar(30) COMMENT '文書(業務)の担当者';
alter table estimatesi add incharge varchar(30) COMMENT '文書(業務)の担当者';
alter table estimatelanguage add incharge varchar(30) COMMENT '文書(業務)の担当者';
alter table billsi add incharge varchar(30) COMMENT '文書(業務)の担当者';
alter table billsolution add incharge varchar(30) COMMENT '文書(業務)の担当者';
alter table documentmaster add approvedDate datetime COMMENT '承認日';

-- 누락사항 적용.
alter table estimatesi add sum BIGINT;
alter table estimatesi add tax BIGINT;


-- 현 회사정보 최대 입력 300자를 꽉체워 넣으면 오류가 남. 400으로 늘림.
ALTER TABLE `interline_estimatesystem`.`companyinform` 
CHANGE COLUMN `address` `address` VARCHAR(400) NULL DEFAULT NULL COMMENT '住所' ;

ALTER TABLE `interline_estimatesystem`.`estimatelanguage` 
CHANGE COLUMN `address` `address` VARCHAR(400) NULL DEFAULT NULL COMMENT '供給者住所' ;
ALTER TABLE `interline_estimatesystem`.`estimatesi` 
CHANGE COLUMN `address` `address` VARCHAR(400) NULL DEFAULT NULL COMMENT '供給者住所' ;
ALTER TABLE `interline_estimatesystem`.`estimatesolution` 
CHANGE COLUMN `address` `address` VARCHAR(400) NULL DEFAULT NULL COMMENT '供給者住所' ;
ALTER TABLE `interline_estimatesystem`.`billsi` 
CHANGE COLUMN `address` `address` VARCHAR(400) NULL DEFAULT NULL COMMENT '供給者住所' ;
ALTER TABLE `interline_estimatesystem`.`billsolution` 
CHANGE COLUMN `address` `address` VARCHAR(400) NULL DEFAULT NULL COMMENT '供給者住所' ;

-- 직위 소속을 생성, 변경, 삭제가 가능해짐에따라 인덱스종류 추가. 10번부터.
insert into masterSeq (id,seqName)values(10,"departmentSeq");
insert into masterSeq (id,seqName)values(10,"positionSeq");

-- 유저정보의 이메일 연락처 삭제
ALTER TABLE `interline_estimatesystem`.`userinform` 
DROP COLUMN `email`,
DROP COLUMN `phoneNumber`;

-- 직위, 소속을 삭제할경우를위해  삭제시 restrict -> set null 설정.
ALTER TABLE `interline_estimatesystem`.`userinform` 
DROP FOREIGN KEY `userinform_ibfk_1`,
DROP FOREIGN KEY `userinform_ibfk_2`,
DROP FOREIGN KEY `userinform_ibfk_3`;
ALTER TABLE `interline_estimatesystem`.`userinform` 
ADD CONSTRAINT `userinform_ibfk_1`
  FOREIGN KEY (`auth`)
  REFERENCES `interline_estimatesystem`.`auth` (`auth`)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT,
ADD CONSTRAINT `userinform_ibfk_2`
  FOREIGN KEY (`departmentNum`)
  REFERENCES `interline_estimatesystem`.`department` (`departmentNum`)
  ON DELETE SET NULL
  ON UPDATE RESTRICT,
ADD CONSTRAINT `userinform_ibfk_3`
  FOREIGN KEY (`positionNum`)
  REFERENCES `interline_estimatesystem`.`position2` (`positionNum`)
  ON DELETE SET NULL
  ON UPDATE RESTRICT;
  

  
UPDATE `interline_estimatesystem`.`documenttype` SET `explanation` = 'ソリューション事業部見積書' WHERE (`documentTypeName` = 'estimateSolution');
UPDATE `interline_estimatesystem`.`documenttype` SET `explanation` = 'ソリューション事業部請求書' WHERE (`documentTypeName` = 'billSolution');
UPDATE `interline_estimatesystem`.`documenttype` SET `explanation` = '語学事業部見積書' WHERE (`documentTypeName` = 'estimateLanguage');
UPDATE `interline_estimatesystem`.`documenttype` SET `explanation` = 'SI事業部見積書' WHERE (`documentTypeName` = 'estimateSi');
UPDATE `interline_estimatesystem`.`documenttype` SET `explanation` = 'SI事業部請求書' WHERE (`documentTypeName` = 'billSi');


  ##ver1.00
ALTER TABLE `interline_estimatesystem`.`estimatesi` 
ADD COLUMN `taxRate` INT NULL DEFAULT NULL AFTER `tax`;

  ##ver1.01
INSERT INTO `interline_estimatesystem`.`documenttype` (`documentTypeName`,`explanation`,`systemNum`) VALUES ('billC','請求書C','2');

-- 請求書C[???]
CREATE TABLE billC
(
	documentNum varchar(20) NOT NULL COMMENT 'documentNum',
	-- ユーザ情報の固有ナンバー
	userNum int NOT NULL COMMENT '作成者 : ユーザ情報の固有ナンバー',
	userName varchar(30) COMMENT '作成者名前',
	userDepartment varchar(10) COMMENT '依頼部署',
	userPosition varchar(10) COMMENT '作成者役職',
	billDate varchar(20) COMMENT '作成日表示用',
	-- これがOZRが参照するデータテーブル名になる。
	-- 例）estimateSolution , BillSolution
	documentTypeName varchar(20) DEFAULT 'billC' NOT NULL COMMENT '文書種類の名前 : これがOZRが参照するデータテーブル名になる。',
	address varchar(400) COMMENT '供給者住所',
	stamp varchar(25) COMMENT '印鑑',
	stampFileName varchar(25) COMMENT '印鑑ファイル名',
	logoFileName varchar(25) COMMENT 'logoFileName : logoイメージのファイル名',
	
    receiver varchar(100) COMMENT '顧客名',
	documentName varchar(100) COMMENT '件名',
    payCondition varchar(40) COMMENT '支払い条件',
    deadline varchar(20),
    itemTitle varchar(80) COMMENT '項目TITLE自由指定',
    
    bankName varchar(30) COMMENT '銀行名',
	branchName varchar(20) COMMENT '支店名',
	accountName varchar(30) COMMENT '口座名',
	hurigana varchar(60) COMMENT '口座名フリカナ',
	accountNumber varchar(20) COMMENT '口座番号',
	depositeClassification varchar(10) COMMENT '預金区分',
    
    note varchar(300) COMMENT '注意事項・備考',
    sum bigint COMMENT '総計',
    taxRate int ,
	tax bigint COMMENT '税金',
    commissionRate int ,
	commission bigint COMMENT '手数料',
	sumWithTax bigint COMMENT '総計税金込み',
	sumWithTax2 bigint COMMENT '総計税金込み２',
    
	insertDate datetime DEFAULT CURRENT_TIMESTAMP COMMENT '格納日時 : このデータが挿入された日時。又はこのデータが有効になった日時。',
	updateDate datetime COMMENT 'updateDate : 更新日時',
	updater int COMMENT '更新者 : データの更新者のuserNum',
	PRIMARY KEY (documentNum)
) COMMENT = '請求書C';


-- 請求書Cのアイテム[???]
CREATE TABLE billCItems
(
	rowNum int COMMENT '行数',
    itemName varchar(80),
    unitPrice bigint COMMENT '単価',
    amount int COMMENT '数量', 
	price bigint COMMENT '値段',
	documentNum varchar(20) NOT NULL COMMENT 'documentNum'
) COMMENT = '見積書Cのアイテム';

ALTER TABLE billC ADD FOREIGN KEY (documentNum) REFERENCES estimateMaster (documentNum) ON UPDATE RESTRICT ON DELETE CASCADE;
ALTER TABLE billCItems ADD FOREIGN KEY (documentNum) REFERENCES billC (documentNum) ON UPDATE RESTRICT ON DELETE CASCADE;
ALTER TABLE billC ADD FOREIGN KEY (documentTypeName) REFERENCES documentType (documentTypeName) ON UPDATE CASCADE ON DELETE CASCADE;

CREATE TABLE accountInformAbroad
(
	accountInformNum int AUTO_INCREMENT COMMENT 'PKID' primary key,
    accountInformName varchar(40),
    bnSwiftCode varchar(40),
    bnBankName varchar(40),
    bnBranchName varchar(40),
    bnBankAddress varchar(40),
    bnName varchar(40),
    bnAddress varchar(40),
    bnAccountNumber varchar(40),
    bnAccountName varchar(40),
    imSwiftCode varchar(40),
    imBankName varchar(40)
) COMMENT = 'accountInform for abroad';


INSERT INTO `interline_estimatesystem`.`documenttype` (`documentTypeName`,`explanation`,`systemNum`) VALUES ('billD','請求書D','2');
-- 請求書D[???]
CREATE TABLE billD
(
	documentNum varchar(20) NOT NULL COMMENT 'documentNum',
	-- ユーザ情報の固有ナンバー
	userNum int NOT NULL COMMENT '作成者 : ユーザ情報の固有ナンバー',
	userName varchar(30) COMMENT '作成者名前',
	userDepartment varchar(10) COMMENT '依頼部署',
	userPosition varchar(10) COMMENT '作成者役職',
	billDate varchar(20) COMMENT '作成日表示用',
	-- これがOZRが参照するデータテーブル名になる。
	-- 例）estimateSolution , BillSolution
	documentTypeName varchar(20) DEFAULT 'billC' NOT NULL COMMENT '文書種類の名前 : これがOZRが参照するデータテーブル名になる。',
	address varchar(400) COMMENT '供給者住所',
	stamp varchar(25) COMMENT '印鑑',
	stampFileName varchar(25) COMMENT '印鑑ファイル名',
	logoFileName varchar(25) COMMENT 'logoFileName : logoイメージのファイル名',
    receiver varchar(100) COMMENT '顧客名',
	documentName varchar(100) COMMENT '件名',
    
    sum bigint COMMENT '総計',
    taxRate int ,
	tax bigint COMMENT '税金',
    discountRate int ,
	discount bigint ,
	sumWithTax bigint COMMENT '総計税金込み',
	sumWithTax2 bigint COMMENT '総計税金込み２',
    
    note varchar(300) COMMENT '注意事項・備考',
    
    bnSwiftCode varchar(65),
	bnBankName varchar(65),
	bnBranchName varchar(65),
	bnBankAddress varchar(65),
	bnName varchar(65),
	bnAddress varchar(65),
	bnAccountNumber varchar(65),
	bnAccountName varchar(65),
	imSwiftCode	varchar(65),
	imBankName varchar(65),
    
	insertDate datetime DEFAULT CURRENT_TIMESTAMP COMMENT '格納日時 : このデータが挿入された日時。又はこのデータが有効になった日時。',
	updateDate datetime COMMENT 'updateDate : 更新日時',
	updater int COMMENT '更新者 : データの更新者のuserNum',
	PRIMARY KEY (documentNum)
) COMMENT = '請求書D';

-- 請求書Cのアイテム[???]
CREATE TABLE billDItems
(
	rowNum int COMMENT '行数',
    itemName varchar(80),
    unitPrice bigint COMMENT '単価',
    amount int COMMENT '数量', 
	price bigint COMMENT '値段',
	documentNum varchar(20) NOT NULL COMMENT 'documentNum'
) COMMENT = '見積書Dのアイテム';


ALTER TABLE billD ADD FOREIGN KEY (documentNum) REFERENCES estimateMaster (documentNum) ON UPDATE RESTRICT ON DELETE CASCADE;
ALTER TABLE billDItems ADD FOREIGN KEY (documentNum) REFERENCES billD (documentNum) ON UPDATE RESTRICT ON DELETE CASCADE;
ALTER TABLE billD ADD FOREIGN KEY (documentTypeName) REFERENCES documentType (documentTypeName) ON UPDATE CASCADE ON DELETE CASCADE;



-- ver 1.20
ALTER TABLE `interline_estimatesystem`.`documentmaster` 
ADD COLUMN `fileName` VARCHAR(45) NULL DEFAULT NULL AFTER `approvedDate`;
UPDATE `interline_estimatesystem`.`systemtype` SET `explanation` = '見積書' WHERE (`systemNum` = '1');
UPDATE `interline_estimatesystem`.`systemtype` SET `explanation` = '請求書' WHERE (`systemNum` = '2');
delete from documentMaster where (`documentTypeName` = 'billSheet1');
delete from documentMaster where (`nextDocumentTypeName` = 'billSheet1');
delete from documentMaster where (`documentTypeName` = 'estimateSheet1');
delete from documentMaster where (`nextDocumentTypeName` = 'estimateSheet1');
DELETE FROM `interline_estimatesystem`.`documenttype` WHERE (`documentTypeName` = 'billSheet1');
DELETE FROM `interline_estimatesystem`.`documenttype` WHERE (`documentTypeName` = 'estimateSheet1');
UPDATE `interline_estimatesystem`.`documenttype` SET `explanation` = '請求書C(語学事業部)' WHERE (`documentTypeName` = 'billC');
UPDATE `interline_estimatesystem`.`documenttype` SET `explanation` = '請求書D(語学事業部eng)' WHERE (`documentTypeName` = 'billD');
UPDATE `interline_estimatesystem`.`documenttype` SET `explanation` = '請求書B(SI事業部)' WHERE (`documentTypeName` = 'billSi');
UPDATE `interline_estimatesystem`.`documenttype` SET `explanation` = '請求書A(ソリューション事業部)' WHERE (`documentTypeName` = 'billSolution');
UPDATE `interline_estimatesystem`.`documenttype` SET `explanation` = '見積書C(語学事業部)' WHERE (`documentTypeName` = 'estimateLanguage');
UPDATE `interline_estimatesystem`.`documenttype` SET `explanation` = '見積書A(SI事業部)' WHERE (`documentTypeName` = 'estimateSi');
UPDATE `interline_estimatesystem`.`documenttype` SET `explanation` = '見積書B(ソリューション事業部)' WHERE (`documentTypeName` = 'estimateSolution');


-- 1.22
-- 청구서C 테수료 항목 이름을 변경할수 있게.
ALTER TABLE `interline_estimatesystem`.`billc` ADD COLUMN `commissionName` VARCHAR(10) NULL DEFAULT NULL AFTER `tax`;



-- ver 1.30
-- billSelect 순서 변경 및 표시항목 모델화
UPDATE `interline_estimatesystem`.`documenttype` SET `explanation` = '請求書A(SI事業部)' WHERE (`documentTypeName` = 'billSi');
UPDATE `interline_estimatesystem`.`documenttype` SET `explanation` = '請求書B(ソリューション事業部)' WHERE (`documentTypeName` = 'billSolution');
ALTER TABLE `interline_estimatesystem`.`documenttype` ADD COLUMN `orderNum` INT NULL AFTER `systemNum`, ADD UNIQUE INDEX `order` (`systemNum` ASC, `orderNum` ASC);
UPDATE `interline_estimatesystem`.`documenttype` SET `orderNum` = '1' WHERE (`documentTypeName` = 'billSi');
UPDATE `interline_estimatesystem`.`documenttype` SET `orderNum` = '2' WHERE (`documentTypeName` = 'billSolution');
UPDATE `interline_estimatesystem`.`documenttype` SET `orderNum` = '3' WHERE (`documentTypeName` = 'billC');
UPDATE `interline_estimatesystem`.`documenttype` SET `orderNum` = '4' WHERE (`documentTypeName` = 'billD');
UPDATE `interline_estimatesystem`.`documenttype` SET `orderNum` = '1' WHERE (`documentTypeName` = 'estimateSi');
UPDATE `interline_estimatesystem`.`documenttype` SET `orderNum` = '2' WHERE (`documentTypeName` = 'estimateSolution');
UPDATE `interline_estimatesystem`.`documenttype` SET `orderNum` = '3' WHERE (`documentTypeName` = 'estimateLanguage');


-- estimateDate, billDate 항목을 날짜 크기 비교하기 위해 date형식으로 변경. 조건절에 str_to_date()함수를 쓰지않기위해
ALTER TABLE `interline_estimatesystem`.`estimatelanguage` 
CHANGE COLUMN `estimateDate` `estimateDate` DATETIME NULL DEFAULT NULL COMMENT '作成日表示用' ;
ALTER TABLE `interline_estimatesystem`.`estimatesolution` 
CHANGE COLUMN `estimateDate` `estimateDate` DATETIME NULL DEFAULT NULL COMMENT '作成日表示用' ;
ALTER TABLE `interline_estimatesystem`.`estimatesi` 
CHANGE COLUMN `estimateDate` `estimateDate` DATETIME NULL DEFAULT NULL COMMENT '作成日表示用' ;
ALTER TABLE `interline_estimatesystem`.`billsi` 
CHANGE COLUMN `billDate` `billDate` DATETIME NULL DEFAULT NULL COMMENT '作成日表示用' ;
ALTER TABLE `interline_estimatesystem`.`billsolution` 
CHANGE COLUMN `billDate` `billDate` DATETIME NULL DEFAULT NULL COMMENT '作成日表示用' ;
ALTER TABLE `interline_estimatesystem`.`billc` 
CHANGE COLUMN `billDate` `billDate` DATETIME NULL DEFAULT NULL COMMENT '作成日表示用' ;
ALTER TABLE `interline_estimatesystem`.`billd` 
CHANGE COLUMN `billDate` `billDate` DATETIME NULL DEFAULT NULL COMMENT '作成日表示用' ;

-- billsi 아이템 컬럼추가(작업자)
ALTER TABLE `interline_estimatesystem`.`billsiitems` ADD COLUMN `workerName` VARCHAR(10) NULL DEFAULT NULL AFTER `rowNum`;

-- 청구서 집계를위한 뷰생성
CREATE VIEW billTotalView AS
SELECT v.documentNum, v.billDate, v.userNum, v.documentName, v.receiver, v.sumWithTax, v.sumWithTax2, v.insertDate, v.updateDate, v.updater, v.documentTypeName
FROM (select t.documentNum, t.billDate, t.userNum, t.documentName, t.receiver, t.sumWithTax, t.sumWithTax2, t.insertDate, t.updateDate, t.updater, t.documentTypeName from 
(SELECT documentNum, billDate, userNum, documentName, receiver, sumWithTax, sumWithTax2, insertDate, updateDate, updater, documentTypeName from billSi as si union
SELECT documentNum, billDate, userNum, documentName, receiver, sumWithTax, sumWithTax2, insertDate, updateDate, updater, documentTypeName from billSolution as so union
SELECT documentNum, billDate, userNum, documentName, receiver, sumWithTax, sumWithTax2, insertDate, updateDate, updater, documentTypeName from billC as c union
SELECT documentNum, billDate, userNum, documentName, receiver, sumWithTax, sumWithTax2, insertDate, updateDate, updater, documentTypeName from billD as d) as t
join (select documentNum, targetValue from workflow where systemNum = 2 and targetValue = "aaa") as w
on t.documentNum = w.documentNum) as v;


-- 1.40 주문서시스템추가
INSERT INTO `interline_estimatesystem`.`systemtype` (`systemNum`, `systemName`, `explanation`) VALUES ('3', 'orderSystem', '注文書');
INSERT INTO `interline_estimatesystem`.`documenttype` (`documentTypeName`, `explanation`, `systemNum`, `orderNum`) VALUES ('orderA', '注文書Ａ(SI事業部)', '3', '1');
INSERT INTO `interline_estimatesystem`.`workflowinform` (`workflowInformNum`, `approver1`, `approver2`, `approver3`, `targetKey`, `insertDate`, `systemNum`) VALUES ('3', '2', '-1', '-1', 'aaa', CURRENT_TIMESTAMP, '3');
INSERT INTO masterSeq VALUES (0, 'orderSeq');

-- 주문서 마스터 테이블
CREATE TABLE orderMaster
(
	documentNum varchar(20) NOT NULL COMMENT 'documentNum',
	receiver varchar(40) COMMENT '顧客名',
	documentName varchar(40) COMMENT '件名',
	-- 関われている見積書のdocumentNum
	PRIMARY KEY (documentNum)
) COMMENT = '新規テーブル';

ALTER TABLE orderMaster
	ADD FOREIGN KEY (documentNum)
	REFERENCES documentMaster (documentNum)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;

-- 주문서A[SI事業部]
CREATE TABLE orderA
(
	documentNum varchar(20) NOT NULL COMMENT 'documentNum',
	-- ユーザ情報の固有ナンバー
	userNum int NOT NULL COMMENT '作成者 : ユーザ情報の固有ナンバー',
	userName varchar(30) COMMENT '作成者名前',
	userDepartment varchar(10) COMMENT '依頼部署',
	userPosition varchar(10) COMMENT '作成者役職',
	orderDate datetime COMMENT '作成日表示用',
	-- これがOZRが参照するデータテーブル名になる。
	-- 例）estimateSolution , BillSolution
	documentTypeName varchar(20) DEFAULT 'orderA' NOT NULL COMMENT '文書種類の名前 : これがOZRが参照するデータテーブル名になる。',
	address varchar(400) COMMENT '供給者住所',
	stamp varchar(25) COMMENT '印鑑',
	stampFileName varchar(25) COMMENT '印鑑ファイル名',
	-- logoイメージのファイル名
	logoFileName varchar(25) COMMENT 'logoFileName : logoイメージのファイル名',
	receiver varchar(100) COMMENT '顧客名',
	documentName varchar(100) COMMENT '件名',
    deadline varchar(40) COMMENT '納入期日',
    place varchar(40) COMMENT '納品場所',
    contractType varchar(40) COMMENT '契約区分',
	output varchar(40) COMMENT '納品物',
	payCondition varchar(100) COMMENT '支払い条件',
    etc1 varchar(80) COMMENT 'その他1',
	etc2 varchar(400) COMMENT 'その他2',
	sumWithoutTax bigint COMMENT '総計税金込み',
	sumWithoutTax2 bigint COMMENT '総計税金込み２',
	insertDate datetime DEFAULT CURRENT_TIMESTAMP COMMENT '格納日時 : このデータが挿入された日時。又はこのデータが有効になった日時。',
	-- 更新日時
	updateDate datetime COMMENT 'updateDate : 更新日時',
	-- データの更新者のuserNum
	updater int COMMENT '更新者 : データの更新者のuserNum',
	PRIMARY KEY (documentNum)
) COMMENT = 'SI注文書';


-- 주문서A아이템[SI事業部]
CREATE TABLE orderAItems
(
	rowNum int COMMENT '行数',
    item varchar(2) COMMENT '項目名',
	itemName varchar(30) COMMENT '項目名',
	workStart varchar(12),
    workEnd varchar(12),
    manMonth DECIMAL(4,1),
    price bigint COMMENT '値段',
    note varchar(30) COMMENT '備考1',
	unitPrice int COMMENT '単価',
	standardMin int COMMENT '基準時間下限',
    standardMax int COMMENT '基準時間上限',
    underTimeUnitPrice int COMMENT '下限時間による控除',
    overTimeUnitPrice int COMMENT '上限時間による精算',
    note2 varchar(30) COMMENT '備考2',
	documentNum varchar(20) NOT NULL COMMENT 'documentNum'
) COMMENT = 'SI注文書のアイテム';

ALTER TABLE orderA
	ADD FOREIGN KEY (documentNum)
	REFERENCES orderMaster (documentNum)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;

ALTER TABLE orderAItems
	ADD FOREIGN KEY (documentNum)
	REFERENCES orderA (documentNum)
	ON UPDATE RESTRICT
	ON DELETE CASCADE
;

-- 매 0시마다 인덱스를 0으로 세팅 이벤트에 orderSeq 추가
DROP EVENT IF EXISTS setSeqZero;
CREATE EVENT IF NOT EXISTS setSeqZero
    ON SCHEDULE
           EVERY 1 DAY
           STARTS '2021-06-16 23:59:59'
    ON COMPLETION PRESERVE
    ENABLE
    COMMENT 'setSeqZero'
    DO 
		update masterSeq set id = 0 where seqName in ('estimateSeq','billSeq','orderSeq');