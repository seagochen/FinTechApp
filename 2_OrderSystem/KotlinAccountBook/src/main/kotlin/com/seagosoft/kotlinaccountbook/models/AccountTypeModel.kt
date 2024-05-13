package com.seagosoft.kotlinaccountbook.models

import java.sql.DriverManager

/**
 * DROP TABLE IF EXISTS "AccountType";
 * CREATE TABLE "AccountType" (
 *     "AccountTypeId"       INTEGER PRIMARY KEY AUTOINCREMENT,
 *     "AccountTypeName"     VARCHAR(32) NOT NULL DEFAULT '',
 *     UNIQUE ("AccountTypeName")
 * );
 */

class AccountType (val accountTypeId: Int,
                   val accountTypeName: String) {
}

class AccountTypeModel(url: String) : BaseModel<AccountType>(url) {

    override fun insert(model: AccountType): Boolean {
        val sql = """
            INSERT INTO AccountType (AccountTypeName)
            VALUES (?)
        """.trimIndent()
        return executeUpdate(sql, listOf(model.accountTypeName)) { }
    }

    override fun delete(identifier: Any): Boolean {
        if (identifier !is Int) throw IllegalArgumentException("AccountTypeId must be an Int")
        val sql = "DELETE FROM AccountType WHERE AccountTypeId = ?"
        return executeUpdate(sql, listOf(identifier)) { }
    }

    override fun update(model: AccountType): Boolean {
        val sql = """
        UPDATE AccountType 
        SET AccountTypeName = ?
        WHERE AccountTypeId = ?
    """.trimIndent()
        return executeUpdate(sql, listOf(model.accountTypeName, model.accountTypeId)) { }
    }

    override fun getAll(): List<AccountType> {
        return getByQuery("SELECT * FROM AccountType")
    }

    override fun getById(identifier: Any): AccountType? {
        if (identifier !is Int) throw IllegalArgumentException("AccountTypeId must be an Int")
        val sql = "SELECT * FROM AccountType WHERE AccountTypeId = ?"
        var accountType: AccountType? = null
        DriverManager.getConnection(url).use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setInt(1, identifier)
                val rs = stmt.executeQuery()
                if (rs.next()) {
                    accountType = createAccountTypeFromResultSet(rs)
                }
            }
        }
        return accountType
    }

    override fun getByQuery(query: String): List<AccountType> {
        val accountTypes = mutableListOf<AccountType>()
        DriverManager.getConnection(url).use { conn ->
            conn.prepareStatement(query).use { stmt ->
                val rs = stmt.executeQuery()
                while (rs.next()) {
                    accountTypes.add(createAccountTypeFromResultSet(rs))
                }
            }
        }
        return accountTypes
    }

    private fun createAccountTypeFromResultSet(rs: java.sql.ResultSet): AccountType {
        return AccountType(
            rs.getInt("AccountTypeId"),
            rs.getString("AccountTypeName")
        )
    }
}