import re
import sqlite3


def camel_to_snake(name):
    name = re.sub('(.)([A-Z][a-z]+)', r'\1_\2', name)
    return re.sub('([a-z0-9])([A-Z])', r'\1_\2', name).lower()


class BaseModel:
    def __init__(self, db_path, table_name, primary_key=None, primary_val=None):
        self.conn = sqlite3.connect(db_path)
        self.table_name = table_name
        self.primary_key = primary_key
        self.primary_val = primary_val

    def query(self, command, values=None):
        try:
            with self.conn:
                cur = self.conn.cursor()
                if values:
                    cur.execute(command, values)
                else:
                    cur.execute(command)

                if 'SELECT' in command.upper():
                    return cur.fetchall()
                else:
                    self.conn.commit()
                    return "Query executed successfully"
        except sqlite3.Error as e:
            return f"An error occurred: {e}"

    def update_from_dict(self, update_dict):
        for key, value in update_dict.items():
            snake_key = camel_to_snake(key)
            if hasattr(self, snake_key):
                setattr(self, snake_key, value)

            # Update primary key
            if self.primary_key == key:
                self.primary_val = value

    def to_dict(self):
        return {}

    def close(self):
        self.conn.close()

    def __del__(self):
        self.conn.close()

    def __dict__(self):
        return self.to_dict()

    def __str__(self):
        return str(self.__dict__())

    def __len__(self):
        return len(self.__dict__())
