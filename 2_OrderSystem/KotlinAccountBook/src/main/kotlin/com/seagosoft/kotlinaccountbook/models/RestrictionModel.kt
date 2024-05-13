package com.seagosoft.kotlinaccountbook.models

import java.sql.DriverManager


/**
 * DROP TABLE IF EXISTS "Restriction";
 * CREATE TABLE "Restriction" (
 * "RestrictionId" INTEGER PRIMARY KEY AUTOINCREMENT,
 * "RestrictionName" varchar(32) NOT NULL DEFAULT '',
 * UNIQUE("RestrictionName")
 * );
 */
class Restriction (
    var RestrictionId: Int = 0,
    var RestrictionName: String = "") { }


class RestrictionModel(url: String) : BaseModel<Restriction>(url) {

    override fun insert(model: Restriction): Boolean {
        val sql = """
        INSERT INTO Restriction (RestrictionName)
        VALUES (?)
    """.trimIndent()
        return executeUpdate(sql, listOf(model.RestrictionName)) { }
    }

    override fun delete(identifier: Any): Boolean {
        if (identifier !is Int) throw IllegalArgumentException("RestrictionId must be an Int")
        val sql = "DELETE FROM Restriction WHERE RestrictionId = ?"
        return executeUpdate(sql, listOf(identifier)) { }
    }

    override fun update(model: Restriction): Boolean {
        val sql = """
    UPDATE Restriction 
    SET RestrictionName = ?
    WHERE RestrictionId = ?
""".trimIndent()
        return executeUpdate(sql, listOf(model.RestrictionName, model.RestrictionId)) { }
    }

    override fun getAll(): List<Restriction> {
        return getByQuery("SELECT * FROM Restriction")
    }

    override fun getById(identifier: Any): Restriction? {
        if (identifier !is Int) throw IllegalArgumentException("RestrictionId must be an Int")
        val sql = "SELECT * FROM Restriction WHERE RestrictionId = ?"
        var restriction: Restriction? = null
        DriverManager.getConnection(url).use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setInt(1, identifier)
                val rs = stmt.executeQuery()
                if (rs.next()) {
                    restriction = createRestrictionFromResultSet(rs)
                }
            }
        }
        return restriction
    }

    override fun getByQuery(query: String): List<Restriction> {
        val restrictions = mutableListOf<Restriction>()
        DriverManager.getConnection(url).use { conn ->
            conn.prepareStatement(query).use { stmt ->
                val rs = stmt.executeQuery()
                while (rs.next()) {
                    restrictions.add(createRestrictionFromResultSet(rs))
                }
            }
        }
        return restrictions
    }

    private fun createRestrictionFromResultSet(rs: java.sql.ResultSet): Restriction {
        return Restriction(
            rs.getInt("RestrictionId"),
            rs.getString("RestrictionName")
        )
    }

}

