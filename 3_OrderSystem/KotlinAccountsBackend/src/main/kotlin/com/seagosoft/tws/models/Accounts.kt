package com.seagosoft.tws.models

import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet

/**
 *     "AccountId"         VARCHAR(32) NOT NULL,
 *     "ServiceProviderId" INTEGER NOT NULL,
 *     "AccountName"       VARCHAR(32) NOT NULL DEFAULT '',
 *     "AccountTypeId"     INTEGER NOT NULL,
 *     "AccountPassword"   VARCHAR(32) NOT NULL DEFAULT '',
 *     "RestrictionId"     INTEGER NOT NULL,
 *     "Note"              TEXT,
 */
data class Accounts(val accountId: String,
                    val serviceProviderId: Int,
                    val accountName: String,
                    val accountTypeId: Int,
                    val accountPassword: String,
                    val restrictionId: Int,
                    val note: String) {
}
class AccountsModel (private val url: String) {

    private fun <T> useConnection(block: (Connection) -> T): T =
            DriverManager.getConnection(url).use(block)

    private fun <T> usePreparedStatement(sql: String, setParameters: (PreparedStatement) -> Unit, block: (ResultSet) -> T): T =
            useConnection { conn ->
                conn.prepareStatement(sql).use { stmt ->
                    setParameters(stmt)
                    stmt.executeQuery().use(block)
                }
            }

    private fun executeUpdate(sql: String, setParameters: (PreparedStatement) -> Unit) =
            useConnection { conn ->
                conn.prepareStatement(sql).use { stmt ->
                    setParameters(stmt)
                    stmt.executeUpdate()
                }
            }

    fun insertAccount(account: Accounts) {
        val sql = """
            INSERT INTO Accounts (AccountId, ServiceProviderId, AccountName, AccountTypeId, AccountPassword, RestrictionId, Note) 
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """.trimIndent()
        executeUpdate(sql) { stmt ->
            setPreparedStatementParameters(stmt, account.accountId, account.serviceProviderId, account.accountName, account.accountTypeId, account.accountPassword, account.restrictionId, account.note)
        }
    }

    fun deleteAccount(accountId: String) {
        val sql = "DELETE FROM Accounts WHERE AccountId = ?"
        executeUpdate(sql) { stmt -> stmt.setString(1, accountId) }
    }

    fun updateAccount(account: Accounts) {
        val sql = """
            UPDATE Accounts 
            SET ServiceProviderId = ?, AccountName = ?, AccountTypeId = ?, AccountPassword = ?, RestrictionId = ?, Note = ?
            WHERE AccountId = ?
        """.trimIndent()
        executeUpdate(sql) { stmt ->
            setPreparedStatementParameters(stmt, account.serviceProviderId, account.accountName, account.accountTypeId, account.accountPassword, account.restrictionId, account.note, account.accountId)
        }
    }

    fun getAccounts(): List<Accounts> =
            useConnection { conn ->
                conn.createStatement().use { stmt ->
                    stmt.executeQuery("SELECT * FROM Accounts").use { rs ->
                        generateSequence {
                            if (rs.next()) {
                                fillAccount(rs)
                            } else null
                        }.toList()
                    }
                }
            }

    fun getAccountById(accountId: String): Accounts? =
            usePreparedStatement("SELECT * FROM Accounts WHERE AccountId = ?", { it.setString(1, accountId) }) { rs ->
                if (rs.next()) {
                    fillAccount(rs)
                } else null
            }

    fun getAccountsByNote(note: String): List<Accounts> =
            usePreparedStatement("SELECT * FROM Accounts WHERE Note = ?", { it.setString(1, note) }) { rs ->
                generateSequence {
                    if (rs.next()) {
                        fillAccount(rs)
                    } else null
                }.toList()
            }

    private fun fillAccount(rs: ResultSet): Accounts {
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

    private fun setPreparedStatementParameters(stmt: PreparedStatement, vararg params: Any) {
        params.forEachIndexed { index, param ->
            when (param) {
                is String -> stmt.setString(index + 1, param)
                is Int -> stmt.setInt(index + 1, param)
                // 可以根据需要添加更多类型
                else -> throw IllegalArgumentException("Unsupported parameter type: ${param.javaClass.name}")
            }
        }
    }
}