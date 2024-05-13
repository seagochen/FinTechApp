package com.seagosoft.kotlinaccountbook.models

import java.sql.DriverManager
import java.sql.ResultSet


/**
 * DROP TABLE IF EXISTS "Accounts";
 * CREATE TABLE "Accounts" (
 * "AccountId"             VARCHAR(32) NOT NULL,
 * "ServiceProviderId"     INTEGER NOT NULL,
 * "AccountName"           VARCHAR(32) NOT NULL DEFAULT '',
 * "AccountTypeId"         INTEGER NOT NULL,
 * "AccountPassword"       VARCHAR(32) NOT NULL DEFAULT '',
 * "RestrictionId"         INTEGER NOT NULL,
 * "Note"                  TEXT,
 * PRIMARY KEY (AccountId),
 * FOREIGN KEY (ServiceProviderId) REFERENCES ServiceProvider(ServiceProviderId),
 * FOREIGN KEY (AccountTypeId) REFERENCES AccountType(AccountTypeId),
 * FOREIGN KEY (RestrictionId) REFERENCES Restriction(RestrictionId)
)
 */
data class Accounts(val accountId: String,
                    val serviceProviderId: Int,
                    val accountName: String,
                    val accountTypeId: Int,
                    val accountPassword: String,
                    val restrictionId: Int,
                    val note: String) {
}


class AccountsModel(url: String) : BaseModel<Accounts>(url) {

    override fun insert(model: Accounts): Boolean {
        val sql = """
            INSERT INTO Accounts (AccountId, ServiceProviderId, AccountName, AccountTypeId, AccountPassword, RestrictionId, Note)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """.trimIndent()
        return executeUpdate(sql, listOf(model.accountId, model.serviceProviderId, model.accountName,
            model.accountTypeId, model.accountPassword, model.restrictionId, model.note)) { }
    }

    override fun delete(identifier: Any): Boolean {
        if (identifier !is String) throw IllegalArgumentException("AccountId must be a String")
        val sql = "DELETE FROM Accounts WHERE AccountId = ?"
        return executeUpdate(sql, listOf(identifier)) { }
    }

    override fun update(model: Accounts): Boolean {
        val sql = """
        UPDATE Accounts 
        SET ServiceProviderId = ?, AccountName = ?, AccountTypeId = ?, AccountPassword = ?, RestrictionId = ?, Note = ?
        WHERE AccountId = ?
    """.trimIndent()
        return executeUpdate(sql, listOf(model.serviceProviderId, model.accountName,
            model.accountTypeId, model.accountPassword, model.restrictionId, model.note, model.accountId)) { }
    }

    override fun getAll(): List<Accounts> {
        return getByQuery("SELECT * FROM Accounts")
    }

    override fun getById(identifier: Any): Accounts? {
        if (identifier !is String) throw IllegalArgumentException("AccountId must be a String")
        val sql = "SELECT * FROM Accounts WHERE AccountId = ?"
        var account: Accounts? = null
        DriverManager.getConnection(url).use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, identifier)
                val rs = stmt.executeQuery()
                if (rs.next()) {
                    account = createAccountFromResultSet(rs)
                }
            }
        }
        return account
    }

    override fun getByQuery(query: String): List<Accounts> {
        val accounts = mutableListOf<Accounts>()
        DriverManager.getConnection(url).use { conn ->
            conn.prepareStatement(query).use { stmt ->
                val rs = stmt.executeQuery()
                while (rs.next()) {
                    accounts.add(createAccountFromResultSet(rs))
                }
            }
        }
        return accounts
    }

    private fun createAccountFromResultSet(rs: ResultSet): Accounts {
        return Accounts(
            rs.getString("AccountId"),
            rs.getInt("ServiceProviderId"),
            rs.getString("AccountName"),
            rs.getInt("AccountTypeId"),
            rs.getString("AccountPassword"),
            rs.getInt("RestrictionId"),
            rs.getString("Note")
        )
    }
}