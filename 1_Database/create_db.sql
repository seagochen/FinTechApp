DROP TABLE IF EXISTS "AccountType";
CREATE TABLE "AccountType" (
    "AccountTypeId"       INTEGER PRIMARY KEY AUTOINCREMENT,
    "AccountTypeName"     VARCHAR(32) NOT NULL DEFAULT '',
    UNIQUE (AccountTypeName)
);

DROP TABLE IF EXISTS "ServiceProvider";
CREATE TABLE "ServiceProvider" (
    "ServiceProviderId" INTEGER PRIMARY KEY AUTOINCREMENT,
    "ServiceProvider"   VARCHAR(32) NOT NULL DEFAULT '',
    UNIQUE (ServiceProvider)
);

DROP TABLE IF EXISTS "Restriction";
CREATE TABLE "Restriction" (
    "RestrictionId"   INTEGER PRIMARY KEY AUTOINCREMENT,
    "RestrictionName" VARCHAR(32) NOT NULL DEFAULT '',
    UNIQUE (RestrictionName)
);

DROP TABLE IF EXISTS "Accounts";
CREATE TABLE "Accounts" (
    "AccountId"         VARCHAR(32) NOT NULL,
    "ServiceProviderId" INTEGER NOT NULL,
    "AccountName"       VARCHAR(32) NOT NULL DEFAULT '',
    "AccountTypeId"     INTEGER NOT NULL,
    "AccountPassword"   VARCHAR(32) NOT NULL DEFAULT '',
    "RestrictionId"     INTEGER NOT NULL,
    "Note"              TEXT,
    PRIMARY KEY (AccountId),
    FOREIGN KEY (ServiceProviderId) REFERENCES ServiceProvider(ServiceProviderId),
    FOREIGN KEY (AccountTypeId) REFERENCES AccountType(AccountTypeId),
    FOREIGN KEY (RestrictionId) REFERENCES Restriction(RestrictionId)
);
