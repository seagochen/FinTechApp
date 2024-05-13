package com.seagosoft.kotlinaccountbook.models

import java.sql.DriverManager
import java.sql.PreparedStatement


abstract class BaseModel<T>(val url: String) {

    abstract fun insert(model: T): Boolean
    abstract fun delete(identifier: Any): Boolean
    abstract fun update(model: T): Boolean
    abstract fun getAll(): List<T>
    abstract fun getById(identifier: Any): T?
    abstract fun getByQuery(query: String): List<T>

    protected fun executeUpdate(sql: String, parameters: List<Any>, block: (stmt: PreparedStatement) -> Unit): Boolean {
        return DriverManager.getConnection(url).use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                setPreparedStatementParameters(stmt, parameters)
                block(stmt)
                stmt.executeUpdate() > 0
            }
        }
    }

    private fun setPreparedStatementParameters(stmt: PreparedStatement, parameters: List<Any>) {
        for ((index, parameter) in parameters.withIndex()) {
            when (parameter) {
                is String -> stmt.setString(index + 1, parameter)
                is Int -> stmt.setInt(index + 1, parameter)
                // Add more cases here for other types as needed
                else -> throw IllegalArgumentException("Unsupported parameter type: ${parameter::class}")
            }
        }
    }
}
