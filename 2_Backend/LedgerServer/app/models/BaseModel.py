import sqlite3


class BaseModel:
    def __init__(self, db_path, table_name):
        self.conn = sqlite3.connect(db_path)
        self.table_name = table_name

    def add(self, fields, values):
        with self.conn:
            self.conn.execute(f"INSERT INTO {self.table_name} {fields} VALUES ({', '.join('?' * len(values))})", values)

    def get(self, field, value):
        cur = self.conn.cursor()
        cur.execute(f"SELECT * FROM {self.table_name} WHERE {field} = ?", (value,))
        return cur.fetchall()

    def update(self, identifier, id_value, fields, new_values):
        with self.conn:
            self.conn.execute(f"UPDATE {self.table_name} SET {fields} WHERE {identifier} = ?", new_values + (id_value,))

    def delete(self, field, value):
        with self.conn:
            self.conn.execute(f"DELETE FROM {self.table_name} WHERE {field} = ?", (value,))

    def get_all(self):
        results = self.command(f"SELECT * FROM {self.table_name}")
        if len(results) is not None:
            return results
        else:
            return []

    def delete_all(self):
        self.command(f"DELETE FROM {self.table_name}")

    def command(self, command, values=None):
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
                return None

    def __del__(self):
        self.conn.close()
