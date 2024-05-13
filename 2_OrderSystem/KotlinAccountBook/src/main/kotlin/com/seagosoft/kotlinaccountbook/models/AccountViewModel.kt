package com.seagosoft.kotlinaccountbook.models

import java.sql.DriverManager
import java.sql.ResultSet

/**
 * CREATE VIEW AccountsView AS
 * SELECT
 * a.AccountId AS AccountId,
 * b.ServiceProvider AS ServiceProvider,
 * a.AccountName AS AccountName,
 * c.AccountTypeName AS AccountType,
 * a.AccountPassword AS AccountPassword,
 * d.RestrictionName AS Restriction,
 * a.Note as Note
 * FROM Accounts AS a
 * LEFT JOIN ServiceProvider AS b ON a.ServiceProviderId = b.ServiceProviderId
 * LEFT JOIN AccountType AS c ON a.AccountTypeId = c.AccountTypeId
 * LEFT JOIN Restriction AS d ON a.RestrictionId = d.RestrictionId;
 */
data class AccountsView(val accountId: String,
                        val serviceProvider: String,
                        val accountName: String,
                        val accountType: String,
                        val accountPassword: String,
                        val restriction: String,
                        val note: String) {
}

class AccountViewModel (url: String) : BaseModel<AccountsView>(url) {

    override fun insert(model: AccountsView): Boolean {
        throw UnsupportedOperationException("Insert operation is not supported on AccountsView")
    }

    override fun delete(identifier: Any): Boolean {
        throw UnsupportedOperationException("Delete operation is not supported on AccountsView")
    }

    override fun getAll(): List<AccountsView> {
        return getByQuery("SELECT * FROM AccountsView")
    }

    override fun getById(identifier: Any): AccountsView? {
        if (identifier !is String) throw IllegalArgumentException("AccountId must be a String")
        val sql = "SELECT * FROM AccountsView WHERE AccountId = ?"
        var accountView: AccountsView? = null
        DriverManager.getConnection(url).use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, identifier)
                val rs = stmt.executeQuery()
                if (rs.next()) {
                    accountView = createAccountViewFromResultSet(rs)
                }
            }
        }
        return accountView
    }

    override fun getByQuery(query: String): List<AccountsView> {
        val accountsView = mutableListOf<AccountsView>()
        DriverManager.getConnection(url).use { conn ->
            conn.prepareStatement(query).use { stmt ->
                val rs = stmt.executeQuery()
                while (rs.next()) {
                    accountsView.add(createAccountViewFromResultSet(rs))
                }
            }
        }
        return accountsView
    }

    override fun update(model: AccountsView): Boolean {
        throw UnsupportedOperationException("Update operation is not supported on AccountsView")
    }

    private fun createAccountViewFromResultSet(rs: ResultSet): AccountsView {
        return AccountsView(
            rs.getString("AccountId"),
            rs.getString("ServiceProvider"),
            rs.getString("AccountName"),
            rs.getString("AccountType"),
            rs.getString("AccountPassword"),
            rs.getString("Restriction"),
            rs.getString("Note")
        )
    }
}