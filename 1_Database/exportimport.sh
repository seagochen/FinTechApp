#!/bin/bash

# Ask the user if they want to export or import data
read -p "Do you want to export or import data? (export/import): " action

# Ask the user for the path to the folder containing the CSV files
read -p "Please enter the path to the folder containing the CSV files: " folder_path

# Export data to CSV files
if [ "$action" == "export" ]; then
    # Remove the old CSV files if they exist
    rm -f "$folder_path"/*.csv

    # Export each table to a separate CSV file
    for table_name in $(sqlite3 -header -list database.db "SELECT name FROM sqlite_master WHERE type='table'"); do
        sqlite3 -header -csv database.db "SELECT * FROM $table_name;" > "$folder_path/$table_name.csv"
    done

    # Replace commas with percentage marks
    sed -i 's/,/%/g' "$folder_path"/*.csv

    # Check if the export succeeded
    if [ $? -eq 0 ]; then
        echo "Data exported to CSV files successfully."
    else
        echo "Error exporting data to CSV files."
    fi

# Import data from CSV files
elif [ "$action" == "import" ]; then
    # Loop through each CSV file in the specified folder
    for csvfile in "$folder_path"/*.csv; do
        # Get the table name from the filename (without the .csv extension)
        tablename=$(basename "$csvfile" .csv)

        # Create the table in the database (if it doesn't already exist)
        sqlite3 database.db "CREATE TABLE IF NOT EXISTS $tablename ($(head -n 1 "$csvfile" | tr ',' ' '));"

        # Load the data from the CSV file into the table
        sqlite3 -separator ',' -csv database.db ".import $csvfile $tablename"

        # Check if the import succeeded
        if [ $? -eq 0 ]; then
            echo "Data imported from $csvfile to $tablename table successfully."
        else
            echo "Error importing data from $csvfile to $tablename table."
        fi
    done

else
    echo "Error: invalid action specified."
fi
