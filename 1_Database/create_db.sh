#!/bin/bash

# Remove the old database file if it exists
rm -f database.db

# Execute the queries in create_db.sql and save the output to the database file
sqlite3 database.db < create_db.sql

# Check the exit status of sqlite3
if [ $? -eq 0 ]; then
    echo "SQLite database created successfully."
    
    # Ask the user if they want to show the database schema
    read -p "Do you want to show the database schema? (y/n): " show_schema
    
    if [ "$show_schema" == "y" ]; then
        # Try to dump the database schema to a file
        sqlite3 database.db .schema > schema.sql
        if [ $? -eq 0 ]; then
            echo "Schema exported to schema.sql."
        else
            echo "Error exporting schema from SQLite database."
        fi
    fi
else
    echo "Error creating SQLite database."
fi
