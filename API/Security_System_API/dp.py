import pymysql

conn = pymysql.connect(
    host='',
    database='',
    user='',
    password='',
    charset='',
    cursorclass=''
)

cursor = conn.cursor()
sql_query = """ CREATE TABLE water_values (
    id integer PRIMARY KEY,
    value integer NOT NULL
)"""

cursor.execute(sql_query)
conn.close()
