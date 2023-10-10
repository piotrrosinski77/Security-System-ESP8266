import pymysql

conn = pymysql.connect(
    host="sql11.freesqldatabase.com",
    database="sql11652302",
    user="sql11652302",
    password="",
    charset="utf8mb4",
    cursorclass=pymysql.cursors.DictCursor,
)

cursor = conn.cursor()
sql_query = """ CREATE TABLE water_values (
    id integer PRIMARY KEY,
    value integer NOT NULL
)"""

cursor.execute(sql_query)
conn.close()
