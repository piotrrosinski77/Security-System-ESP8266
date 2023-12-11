from flask import Flask, request, jsonify
import json
import pymysql

app = Flask(__name__)


def db_connection():
    conn = None
    try:
        conn = pymysql.connect(
            host="database-security-system.cvsya5hj2kmb.eu-central-1.rds.amazonaws.com",
            database="values",
            user="Piotr",
            password="fI1zuJwbKf",
            charset="utf8mb4",
            cursorclass=pymysql.cursors.DictCursor,
        )
    except pymysql.Error as e:
        print(e)
    return conn


@app.route("/water", methods=["GET", "POST"])
def water():
    conn = db_connection()
    cursor = conn.cursor()

    if request.method == "GET":
        cursor.execute("SELECT * FROM water_values")
        water_values = [
            dict(id=row["id"], value=row["value"]) for row in cursor.fetchall()
        ]
        if water_values is not None:
            return jsonify(water_values), 200

    if request.method == "POST":
        new_value = request.form["value"]
        sql = """INSERT INTO water_values (value)
                 VALUES (%s)"""

        cursor = cursor.execute(sql, (new_value))
        conn.commit()
        return "Value created successfully", 201


@app.route("/specwater/<int:id>", methods=["GET", "PUT", "DELETE"])
def waterSpecific(id):
    conn = db_connection()
    cursor = conn.cursor()
    water = None

    if request.method == "GET":
        cursor.execute("SELECT * FROM water_values WHERE id=%s", (id,))
        rows = cursor.fetchall()
        for r in rows:
            water = r
        if water is not None:
            return jsonify(water), 200
        else:
            return "Something wrong", 404

    if request.method == "PUT":
        sql = """UPDATE water_values SET value=%s WHERE id=%s"""
        value = request.form["value"]
        updated_water = {"id": id, "value": value}
        cursor.execute(sql, (value, id))
        conn.commit()
        return jsonify(updated_water), 201

    if request.method == "DELETE":
        sql = """DELETE FROM water_values WHERE id=%s"""
        cursor.execute(sql, (id,))
        conn.commit()
        return "The value with id: {} has been deleted.".format(id), 200



if __name__ == "__main__":
    app.run()

