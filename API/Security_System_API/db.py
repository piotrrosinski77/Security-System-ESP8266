import pymysql

conn = pymysql.connect(
    host="sql11.freesqldatabase.com",
    database="sql11653073",
    user="sql11653073",
    password="fI1zuJwbKf",
    charset="utf8mb4",
    cursorclass=pymysql.cursors.DictCursor,
)

cursor = conn.cursor()
sql_query = """ CREATE TABLE water (
    id integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
    value integer NOT NULL
)"""

#sql_query = """DROP TABLE water"""

cursor.execute(sql_query)
conn.close()