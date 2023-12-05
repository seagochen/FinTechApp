/* Log tables for all tables in the database */

CREATE TABLE AccountsLog (
    LogId INTEGER PRIMARY KEY AUTOINCREMENT,
    AccountId VARCHAR(32),
    Operation TEXT,
    Timestamp DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE ServiceProviderLog (
    LogId INTEGER PRIMARY KEY AUTOINCREMENT,
    ServiceProviderId INTEGER,
    Operation TEXT,
    Timestamp DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE RestrictionLog (
    LogId INTEGER PRIMARY KEY AUTOINCREMENT,
    RestrictionId INTEGER,
    Operation TEXT,
    Timestamp DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE AccountTypeLog (
    LogId INTEGER PRIMARY KEY AUTOINCREMENT,
    AccountTypeId INTEGER,
    Operation TEXT,
    Timestamp DATETIME DEFAULT CURRENT_TIMESTAMP
);

/* Trigger for Accounts */

CREATE TRIGGER LogAccountInsert
AFTER INSERT ON Accounts
BEGIN
    INSERT INTO AccountsLog (AccountId, Operation)
    VALUES (NEW.AccountId, 'INSERT');
END;

CREATE TRIGGER LogAccountDelete
AFTER DELETE ON Accounts
BEGIN
    INSERT INTO AccountsLog (AccountId, Operation)
    VALUES (OLD.AccountId, 'DELETE');
END;

CREATE TRIGGER LogAccountUpdate
AFTER UPDATE ON Accounts
BEGIN
    INSERT INTO AccountsLog (AccountId, Operation)
    VALUES (NEW.AccountId, 'UPDATE');
END;

/* Trigger for ServiceProvider */

CREATE TRIGGER LogServiceProviderInsert
AFTER INSERT ON ServiceProvider
BEGIN
    INSERT INTO ServiceProviderLog (ServiceProviderId, Operation)
    VALUES (NEW.ServiceProviderId, 'INSERT');
END;

CREATE TRIGGER LogServiceProviderDelete
AFTER DELETE ON ServiceProvider
BEGIN
    INSERT INTO ServiceProviderLog (ServiceProviderId, Operation)
    VALUES (OLD.ServiceProviderId, 'DELETE');
END;

CREATE TRIGGER LogServiceProviderUpdate
AFTER UPDATE ON ServiceProvider
BEGIN
    INSERT INTO ServiceProviderLog (ServiceProviderId, Operation)
    VALUES (NEW.ServiceProviderId, 'UPDATE');
END;

/* Trigger for Restriction */

CREATE TRIGGER LogRestrictionInsert
AFTER INSERT ON Restriction
BEGIN
    INSERT INTO RestrictionLog (RestrictionId, Operation)
    VALUES (NEW.RestrictionId, 'INSERT');
END;

CREATE TRIGGER LogRestrictionDelete
AFTER DELETE ON Restriction
BEGIN
    INSERT INTO RestrictionLog (RestrictionId, Operation)
    VALUES (OLD.RestrictionId, 'DELETE');
END;

CREATE TRIGGER LogRestrictionUpdate
AFTER UPDATE ON Restriction
BEGIN
    INSERT INTO RestrictionLog (RestrictionId, Operation)
    VALUES (NEW.RestrictionId, 'UPDATE');
END;

/* Trigger for AccountType */

CREATE TRIGGER LogAccountTypeInsert
AFTER INSERT ON AccountType
BEGIN
    INSERT INTO AccountTypeLog (AccountTypeId, Operation)
    VALUES (NEW.AccountTypeId, 'INSERT');
END;

CREATE TRIGGER LogAccountTypeDelete
AFTER DELETE ON AccountType
BEGIN
    INSERT INTO AccountTypeLog (AccountTypeId, Operation)
    VALUES (OLD.AccountTypeId, 'DELETE');
END;

CREATE TRIGGER LogAccountTypeUpdate
AFTER UPDATE ON AccountType
BEGIN
    INSERT INTO AccountTypeLog (AccountTypeId, Operation)
    VALUES (NEW.AccountTypeId, 'UPDATE');
END;
