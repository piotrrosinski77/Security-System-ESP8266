import pymysql

conn = pymysql.connect(
    host="********",
    database="********",
    user="********",
    password="********",
    port=********,
    charset="********,
    cursorclass=pymysql.cursors.DictCursor,
)

cursor = conn.cursor()

sql_query = """ CREATE TABLE water (
    id integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
    value integer NOT NULL
)"""

# sql_query = """DROP TABLE water"""

cursor.execute(sql_query)
conn.close()
