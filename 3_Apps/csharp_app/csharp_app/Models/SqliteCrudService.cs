namespace csharp_app.Models;

using System;
using System.Collections.Generic;
using Microsoft.Data.Sqlite;
using System.Linq;
using Entities;

public class SqliteCrudService
{
    private readonly string _connectionString;

    public SqliteCrudService(string connectionString)
    {
        _connectionString = connectionString;
    }

    public void Insert<T>(T entity) where T : IEntity
    {
        // Open a connection
        using var connection = new SqliteConnection(_connectionString);
        connection.Open();
        
        // Get all properties of T
        var propertiesToMap = typeof(T).GetProperties();
        
        // remove the keys of IEntity from the list of properties to map
        propertiesToMap = propertiesToMap.Where(p => p.Name != "TableName" && p.Name != "PrimaryKey").ToArray();

        // Assemble the query string
        var insertQuery = $"INSERT INTO {entity.TableName} (";
        foreach (var property in propertiesToMap)
        {
            insertQuery += $"{property.Name}, ";
        }
        insertQuery = insertQuery.Remove(insertQuery.Length - 2);
        insertQuery += ") VALUES (";
        foreach (var property in propertiesToMap)
        {
            insertQuery += $"@{property.Name}, ";
        }
        insertQuery = insertQuery.Remove(insertQuery.Length - 2);
        insertQuery += ")";
        
        // Create a command
        var command = connection.CreateCommand();
        command.CommandText = insertQuery;
        
        // Add parameters
        foreach (var property in propertiesToMap)
        {
            command.Parameters.AddWithValue($"@{property.Name}", property.GetValue(entity));
        }
        
        // Execute the command
        command.ExecuteNonQuery();
    }

    public void Update<T>(T entity) where T : IEntity
    {
        // Open a connection
        using var connection = new SqliteConnection(_connectionString);
        connection.Open();

        // Get all properties of T
        var propertiesToMap = typeof(T).GetProperties();
        
        // remove the keys of IEntity from the list of properties to map
        propertiesToMap = propertiesToMap.Where(p => p.Name != "TableName" && p.Name != "PrimaryKey").ToArray();

        // Assemble the query string
        var updateQuery = $"UPDATE {entity.TableName} SET ";
        foreach (var property in propertiesToMap)
        {
            updateQuery += $"{property.Name} = @{property.Name}, ";
        }
        updateQuery = updateQuery.Remove(updateQuery.Length - 2);
        
        // Add the WHERE clause
        updateQuery += $" WHERE {entity.PrimaryKey} = @{entity.PrimaryKey}";
        
        // Create a command
        var command = connection.CreateCommand();
        command.CommandText = updateQuery;
        
        // Add parameters
        foreach (var property in propertiesToMap)
        {
            command.Parameters.AddWithValue($"@{property.Name}", property.GetValue(entity));
        }
        
        // Execute the command
        command.ExecuteNonQuery();
    }

    public void Delete<T>(string id, T entity) where T : IEntity
    {
        // Open a connection
        using var connection = new SqliteConnection(_connectionString);
        connection.Open();
        
        // Assemble the query string
        var deleteQuery = $"DELETE FROM {entity.TableName} WHERE {entity.PrimaryKey} = @Id";

        // Create a command
        var command = connection.CreateCommand();
        command.CommandText = deleteQuery;
        command.Parameters.AddWithValue("@Id", id);
        command.ExecuteNonQuery();
    }
    
    public T Get<T>(string id, T entity) where T : IEntity, new()
    {
        // Open a connection
        using var connection = new SqliteConnection(_connectionString);
        connection.Open();
        
        // Assemble the query string
        var selectQuery = $"SELECT * FROM {entity.TableName} WHERE {entity.PrimaryKey} = @Id";

        // Create a command
        var command = connection.CreateCommand();
        command.CommandText = selectQuery;
        command.Parameters.AddWithValue("@Id", id);
        var reader = command.ExecuteReader();

        var result = new T();
        
        // Get all properties of T
        var propertiesToMap = typeof(T).GetProperties();
        
        // remove the keys of IEntity from the list of properties to map
        propertiesToMap = propertiesToMap.Where(p => p.Name != "TableName" && p.Name != "PrimaryKey").ToArray();

        while (reader.Read())
        {
            foreach (var property in propertiesToMap)
            {
                if (!reader.IsDBNull(reader.GetOrdinal(property.Name)))
                {
                    var value = reader[property.Name];
                    property.SetValue(result, Convert.ChangeType(value, property.PropertyType));
                }
            }
        }

        return result;
    }

    public IEnumerable<T> GetAll<T>() where T : IEntity, new()
    {
        using var connection = new SqliteConnection(_connectionString);
        connection.Open();

        var selectQuery = $"SELECT * FROM {new T().TableName}";

        var command = connection.CreateCommand();
        command.CommandText = selectQuery;

        var reader = command.ExecuteReader();

        var results = new List<T>();
        
        // Get all properties of T
        var propertiesToMap = typeof(T).GetProperties();
        
        // remove the keys of IEntity from the list of properties to map
        propertiesToMap = propertiesToMap.Where(p => p.Name != "TableName" && p.Name != "PrimaryKey").ToArray();

        while (reader.Read())
        {
            var result = new T();
            foreach (var property in propertiesToMap)
            {
                if (!reader.IsDBNull(reader.GetOrdinal(property.Name)))
                {
                    var value = reader[property.Name];
                    property.SetValue(result, Convert.ChangeType(value, property.PropertyType));
                }
            }
            results.Add(result);
        }

        return results;
    }
}
