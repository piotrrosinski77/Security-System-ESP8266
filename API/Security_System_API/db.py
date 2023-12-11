import pymysql

conn = pymysql.connect(
    host="security-system-esp8266.000webhostapp.com",
    database="values",
    user="Piotr",
    password="fI1zuJwbKf",
    port=3306,
    charset="utf8mb4",
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
