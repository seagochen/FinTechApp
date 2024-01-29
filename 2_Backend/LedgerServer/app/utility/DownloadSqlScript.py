import requests
import sqlite3
import os

GITHUB_CREATION_URL = "https://raw.githubusercontent.com/seagochen/FinTechApp/master/1_Database/create_db.sql"
GITHUB_UPDATEVIEW_URL = "https://raw.githubusercontent.com/seagochen/FinTechApp/master/1_Database/create_account_view.sql"
DATABASE_NAME = "database.db"


def download_file(url: str, filename="temp.sql"):
    # Download the file from GitHub and save it in the buffer
    response = requests.get(url)
    if response.status_code == 200:

        # Create a temporary file
        with(open(filename, "wb")) as f:
            f.write(response.content)

        # Return True if the file is downloaded successfully
        return True
    else:
        return False


def execute_script(filename: str, database=DATABASE_NAME):
    try:
        # Print message
        print(f"Executing script {filename}...")

        # Connect to the SQLite file
        conn = sqlite3.connect(database)

        # Load the sql script from the file
        with open(filename, 'r') as f:
            sql_script = f.read()

            # Perform database operations here
            cursor = conn.cursor()

            # Execute the sql script
            cursor.executescript(sql_script)

        # Close the connection
        conn.close()

        # Print message
        print("Completed successfully!")

    except sqlite3.Error as e:
        print(f"Database error: {e}")


if __name__ == "__main__":

    # Download the creation script from GitHub
    download_file(GITHUB_CREATION_URL, filename="create_table.sql")

    # Download another sql script from GitHub
    download_file(GITHUB_UPDATEVIEW_URL, filename="update_view.sql")

    # Check if the database file exists
    if os.path.exists(DATABASE_NAME):
        # If the database file exists, delete it
        os.remove(DATABASE_NAME)

    # Create the database
    execute_script("create_table.sql", database=DATABASE_NAME)
    
    # Execute the sql script
    execute_script("update_view.sql", database=DATABASE_NAME)
