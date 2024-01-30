import re
import sqlite3


def camel_to_snake(name):
    name = re.sub('(.)([A-Z][a-z]+)', r'\1_\2', name)
    return re.sub('([a-z0-9])([A-Z])', r'\1_\2', name).lower()


class BaseModel:
    def __init__(self, db_path, table_name):
        self.conn = sqlite3.connect(db_path)
        self.table_name = table_name

    def query(self, command, values=None):
        with self.conn:
            cur = self.conn.cursor()
            if values:
                res = cur.execute(command, values)
            else:
                res = cur.execute(command)

            if 'SELECT' in command.upper():
                return cur.fetchall()
            else:
                self.conn.commit()
                return res

    def update_from_dict(self, update_dict):
        for key, value in update_dict.items():
            snake_key = camel_to_snake(key)
            if hasattr(self, snake_key):
                setattr(self, snake_key, value)

    def to_dict(self):
        return {}

    def __del__(self):
        self.conn.close()

    def __dict__(self):
        return self.to_dict()
