-- -------------------------------------------------------------
-- TablePlus 5.5.2(512)
--
-- https://tableplus.com/
--
-- Database: accounts.sqlite
-- Generation Time: 2024-05-13 23:06:09.4020
-- -------------------------------------------------------------


DROP TABLE IF EXISTS "Accounts";
CREATE TABLE "Accounts" (
    "AccountId"             VARCHAR(32) NOT NULL,
    "ServiceProviderId"     INTEGER NOT NULL,
    "AccountName"           VARCHAR(32) NOT NULL DEFAULT '',
    "AccountTypeId"         INTEGER NOT NULL,
    "AccountPassword"       VARCHAR(32) NOT NULL DEFAULT '',
    "RestrictionId"         INTEGER NOT NULL,
    "Note"                  TEXT,
    PRIMARY KEY (AccountId),
    FOREIGN KEY (ServiceProviderId) REFERENCES ServiceProvider(ServiceProviderId),
    FOREIGN KEY (AccountTypeId) REFERENCES AccountType(AccountTypeId),
    FOREIGN KEY (RestrictionId) REFERENCES Restriction(RestrictionId)
);

DROP TABLE IF EXISTS "AccountsLog";
CREATE TABLE AccountsLog (
    LogId INTEGER PRIMARY KEY AUTOINCREMENT,
    AccountId VARCHAR(32),
    Operation TEXT,
    Timestamp DATETIME DEFAULT CURRENT_TIMESTAMP
);

DROP VIEW IF EXISTS "AccountsView";


DROP TABLE IF EXISTS "AccountType";
CREATE TABLE "AccountType" (
    "AccountTypeId"       INTEGER PRIMARY KEY AUTOINCREMENT,
    "AccountTypeName"     VARCHAR(32) NOT NULL DEFAULT '',
    UNIQUE ("AccountTypeName")
);

DROP TABLE IF EXISTS "AccountTypeLog";
CREATE TABLE AccountTypeLog (
    LogId INTEGER PRIMARY KEY AUTOINCREMENT,
    AccountTypeId INTEGER,
    Operation TEXT,
    Timestamp DATETIME DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS "Restriction";
CREATE TABLE "Restriction" (
"RestrictionId" INTEGER PRIMARY KEY AUTOINCREMENT,
"RestrictionName" varchar(32) NOT NULL DEFAULT '',
UNIQUE("RestrictionName")
);

DROP TABLE IF EXISTS "RestrictionLog";
CREATE TABLE RestrictionLog (
    LogId INTEGER PRIMARY KEY AUTOINCREMENT,
    RestrictionId INTEGER,
    Operation TEXT,
    Timestamp DATETIME DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS "ServiceProvider";
CREATE TABLE "ServiceProvider" (
"ServiceProviderId" INTEGER PRIMARY KEY AUTOINCREMENT,
"ServiceProvider" varchar(32) NOT NULL DEFAULT '',
UNIQUE ("ServiceProvider")
);

DROP TABLE IF EXISTS "ServiceProviderLog";
CREATE TABLE ServiceProviderLog (
    LogId INTEGER PRIMARY KEY AUTOINCREMENT,
    ServiceProviderId INTEGER,
    Operation TEXT,
    Timestamp DATETIME DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS "sqlite_sequence";
CREATE TABLE sqlite_sequence(name,seq);

INSERT INTO "AccountType" ("AccountTypeId", "AccountTypeName") VALUES
('1', 'Unknown'),
('2', 'Work'),
('3', 'Temporary'),
('4', 'Financial'),
('5', 'Social'),
('6', 'Shopping'),
('7', 'Entertainment'),
('8', 'Productivity'),
('9', 'Recruitment'),
('10', 'Travelling'),
('11', 'Blog'),
('12', 'Education'),
('13', 'Scientific'),
('14', 'Infrastructures'),
('15', 'Development'),
('99', 'Others');

INSERT INTO "Restriction" ("RestrictionId", "RestrictionName") VALUES
('1', 'Unknow'),
('2', 'Global'),
('3', 'China Prohibited'),
('4', 'Hong Kong and Macao Prohibited'),
('5', 'Taiwan Prohibited'),
('6', 'Great China Area Prohibited'),
('7', 'China Only'),
('8', 'Hong Kong and Macao Only'),
('9', 'Taiwan Only'),
('10', 'Great China Area Only'),
('11', 'Japan Prohibited'),
('12', 'United States Prohibited'),
('13', 'United Kingdom Prohibited'),
('14', 'Europe Prohibited'),
('15', 'Canada Prohibited'),
('16', 'Australia Prohibited'),
('17', 'New Zealand Prohibited'),
('18', 'Japan Only'),
('19', 'United States Only'),
('20', 'United Kingdom Only'),
('21', 'Europe Only'),
('22', 'Canada Only'),
('23', 'Australia Only'),
('24', 'New Zealand Only');

INSERT INTO "ServiceProvider" ("ServiceProviderId", "ServiceProvider") VALUES
('1', 'Unknown'),
('2', 'Interactive Broker'),
('3', 'Paypal'),
('4', 'IEEE'),
('5', 'IELTS'),
('6', 'EA'),
('7', 'HP'),
('8', 'IBM'),
('9', 'Ubisoft'),
('10', 'NVIDIA'),
('11', 'Sony'),
('12', 'Steam'),
('13', 'Bank of China'),
('14', 'China Construction Bank'),
('15', 'Agricultural Bank of China'),
('16', 'Industrial & Commercial Bank of China'),
('17', 'East West Bank'),
('18', 'Japan Post Bank'),
('19', 'Open Sources'),
('20', 'Apache'),
('21', 'GNU'),
('22', 'Google'),
('23', 'Facebook'),
('24', 'Twitter'),
('25', 'Adobe'),
('26', 'Instagram'),
('27', 'LinkedIn'),
('28', 'Microsoft'),
('29', 'Apple'),
('30', 'Yahoo'),
('31', 'Amazon'),
('32', 'Tencent'),
('33', 'Alibaba'),
('34', 'Baidu'),
('35', 'JD'),
('36', 'Mei Tuan'),
('37', 'Manufacturers'),
('38', 'Air Lines'),
('39', 'Government'),
('40', 'CSDN'),
('41', 'Wang Yi'),
('42', 'Bilibili'),
('43', 'Oracle'),
('44', 'VMware'),
('45', 'Wacom'),
('46', 'JetBrain'),
('47', 'Dell'),
('90', 'Other Financial Organ.'),
('91', 'Other Research Organ.'),
('92', 'Other Telecom Operators'),
('93', 'Other Development Organ.'),
('94', 'Other Cloud Servicers'),
('95', 'Other Network Communities'),
('96', 'Online Security'),
('99', 'Others');

CREATE VIEW AccountsView AS
SELECT
    a.AccountId AS AccountId,
    b.ServiceProvider AS ServiceProvider,
    a.AccountName AS AccountName,
    c.AccountTypeName AS AccountType,
    a.AccountPassword AS AccountPassword,
    d.RestrictionName AS Restriction,
    a.Note as Note
FROM Accounts AS a
LEFT JOIN ServiceProvider AS b ON a.ServiceProviderId = b.ServiceProviderId
LEFT JOIN AccountType AS c ON a.AccountTypeId = c.AccountTypeId
LEFT JOIN Restriction AS d ON a.RestrictionId = d.RestrictionId;
