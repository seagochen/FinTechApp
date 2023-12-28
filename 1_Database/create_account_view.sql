DROP VIEW IF EXISTS AccountsView;
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
