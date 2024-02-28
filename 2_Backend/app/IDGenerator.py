# -*- coding: utf-8 -*-
# Author: Orlando Chen
# Create: Dec 10, 2019
# Modifi: Dec 11, 2019


from siki.basics.Exceptions import InvalidParamException
from siki.basics.Exceptions import ArrayIndexOutOfBoundsException
from siki.pysql import PySQLConnection as pysc

class UserIDCreator(object):

    def __init__(self, bits=4):
        self.bits = bits


    def _datetime(self):
        from datetime import datetime
        return datetime.now().strftime("%Y%m%d%H%M%S")


    def _max_id(self, rows):
        """
        find out the maximum id of rows
        @return the integer of table id of maximum
        """
        # if
        if type(rows) is list:
            max_id = 0
            for r in rows:
                max_id = max(max_id, int(r))
            return max_id

        # else
        return 0


    def _table_id_max(self, con, db, table):
        """
        find the maximuma id of current table
        @return the integer type of table id
        """
        import pymysql

        if con is None or type(con) is not pymysql.connections.Connection:
            raise InvalidParamException("the connection is null or invalid")

        # generate the sql command to quiry the id
        where_cond = "'id' LIKE '{}%'".format(self._datetime())
        ret_rows = pysc.query(con, "SELECT 'id' FROM {}.{} WHERE {}".format(db, table, where_cond))

        # if
        if ret_rows is not None and len(ret_rows) >= 0:
            return self._max_id(ret_rows)

        # else
        return 0


    def _id_generate(self):
        """
        generate the id in format of YYYYmmDDHHMMSS00000000
        """
        id_strings = ""

        if self.bits <= 12 and self.bits > 0:
            id_strings = self._datetime() + "000000000000000000"[0 : self.bits]
        else:
            raise ArrayIndexOutOfBoundsException("bits of seed to generate id is out of range")

        return id_strings


    def generate_new_id(self, con, db, table):
        """
        generate a new id from given table of database
        @return the id in format of YYYYmmDDHHMMSSXXXX [default type]
        """
        max_id = self._table_id_max(con, db, table)

        # if 0
        if max_id == 0:
            id_str = self._id_generate()
            return str(int(id_str) + 1)
        else:
            id_str = str(max_id + 1)
            return id_str


if __name__ == "__main__":
    from siki.pysql import PySQLConnection as pysc
    idcreator = UserIDCreator()

    conn = pysc.connect(
        user="pxierra",
        password="Pxierra1234",
        host="192.168.12.110")

    id_str = idcreator.generate_new_id(conn, "GoodLightDB", "UserAccountInfo")
    print(id_str)