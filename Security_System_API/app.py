from flask import Flask, request, jsonify
import json
import pymysql

app = Flask(__name__)


def db_connection():
    conn = None
    try:
        conn = pymysql.connect(
            host="********",
            database="********",
            user="********",
            password="********",
            charset="********",
            cursorclass=pymysql.cursors.DictCursor,
        )
    except pymysql.Error as e:
        print(e)
    return conn

@app.route("/gas", methods=["GET", "POST"])
def gas():
    conn = db_connection()
    cursor = conn.cursor()

    if request.method == "GET":
        cursor.execute("SELECT * FROM gas_values")
        gas_values = [
            dict(id=row["id"], value=row["value"]) for row in cursor.fetchall()
        ]
        if gas_values is not None:
            return jsonify(gas_values), 200

    if request.method == "POST":
        new_value = request.form["value"]
        sql = """INSERT INTO gas_values (value)
                 VALUES (%s)"""

        cursor = cursor.execute(sql, (new_value))
        conn.commit()
        return "Value created successfully", 201


@app.route("/specgas/<int:id>", methods=["GET", "PUT", "DELETE"])
def gasSpecific(id):
    conn = db_connection()
    cursor = conn.cursor()
    gas = None

    if request.method == "GET":
        cursor.execute("SELECT * FROM gas_values WHERE id=%s", (id,))
        rows = cursor.fetchall()
        for r in rows:
            gas = r
        if gas is not None:
            return jsonify(gas), 200
        else:
            return "Something wrong", 404

    if request.method == "PUT":
        sql = """UPDATE gas_values SET value=%s WHERE id=%s"""
        value = request.form["value"]
        updated_gas = {"id": id, "value": value}
        cursor.execute(sql, (value, id))
        conn.commit()
        return jsonify(updated_gas), 201

    if request.method == "DELETE":
        sql = """DELETE FROM gas_values WHERE id=%s"""
        cursor.execute(sql, (id,))
        conn.commit()
        return "The value with id: {} has been deleted.".format(id), 200

@app.route("/motion", methods=["GET", "POST"])
def motion():
    conn = db_connection()
    cursor = conn.cursor()

    if request.method == "GET":
        cursor.execute("SELECT * FROM motion_values")
        motion_values = [
            dict(id=row["id"], value=row["value"]) for row in cursor.fetchall()
        ]
        if motion_values is not None:
            return jsonify(motion_values), 200

    if request.method == "POST":
        new_value = request.form["value"]
        sql = """INSERT INTO motion_values (value)
                 VALUES (%s)"""

        cursor = cursor.execute(sql, (new_value))
        conn.commit()
        return "Value created successfully", 201

@app.route("/specmotion/<int:id>", methods=["GET", "PUT", "DELETE"])
def motionSpecific(id):
    conn = db_connection()
    cursor = conn.cursor()
    motion = None

    if request.method == "GET":
        cursor.execute("SELECT * FROM motion_values WHERE id=%s", (id,))
        rows = cursor.fetchall()
        for r in rows:
            motion = r
        if motion is not None:
            return jsonify(motion), 200
        else:
            return "Something wrong", 404

    if request.method == "PUT":
        sql = """UPDATE motion_values SET value=%s WHERE id=%s"""
        value = request.form["value"]
        updated_motion = {"id": id, "value": value}
        cursor.execute(sql, (value, id))
        conn.commit()
        return jsonify(updated_motion), 201

    if request.method == "DELETE":
        sql = """DELETE FROM motion_values WHERE id=%s"""
        cursor.execute(sql, (id,))
        conn.commit()
        return "The value with id: {} has been deleted.".format(id), 200

@app.route("/reedswitch", methods=["GET", "POST"])
def reedswitch():
    conn = db_connection()
    cursor = conn.cursor()

    if request.method == "GET":
        cursor.execute("SELECT * FROM reedswitch_values")
        reedswitch_values = [
            dict(id=row["id"], value=row["value"]) for row in cursor.fetchall()
        ]
        if reedswitch_values is not None:
            return jsonify(reedswitch_values), 200

    if request.method == "POST":
        new_value = request.form["value"]
        sql = """INSERT INTO reedswitch_values (value)
                 VALUES (%s)"""

        cursor = cursor.execute(sql, (new_value))
        conn.commit()
        return "Value created successfully", 201

@app.route("/specreedswitch/<int:id>", methods=["GET", "PUT", "DELETE"])
def reedswitchSpecific(id):
    conn = db_connection()
    cursor = conn.cursor()
    reedswitch = None

    if request.method == "GET":
        cursor.execute("SELECT * FROM reedswitch_values WHERE id=%s", (id,))
        rows = cursor.fetchall()
        for r in rows:
            reedswitch = r
        if reedswitch is not None:
            return jsonify(reedswitch), 200
        else:
            return "Something wrong", 404

    if request.method == "PUT":
        sql = """UPDATE reedswitch_values SET value=%s WHERE id=%s"""
        value = request.form["value"]
        updated_reedswitch = {"id": id, "value": value}
        cursor.execute(sql, (value, id))
        conn.commit()
        return jsonify(updated_reedswitch), 201

    if request.method == "DELETE":
        sql = """DELETE FROM reedswitch_values WHERE id=%s"""
        cursor.execute(sql, (id,))
        conn.commit()
        return "The value with id: {} has been deleted.".format(id), 200

@app.route("/sound", methods=["GET", "POST"])
def sound():
    conn = db_connection()
    cursor = conn.cursor()

    if request.method == "GET":
        cursor.execute("SELECT * FROM sound_values")
        sound_values = [
            dict(id=row["id"], value=row["value"]) for row in cursor.fetchall()
        ]
        if sound_values is not None:
            return jsonify(sound_values), 200

    if request.method == "POST":
        new_value = request.form["value"]
        sql = """INSERT INTO sound_values (value)
                 VALUES (%s)"""

        cursor = cursor.execute(sql, (new_value))
        conn.commit()
        return "Value created successfully", 201

@app.route("/specsound/<int:id>", methods=["GET", "PUT", "DELETE"])
def soundSpecific(id):
    conn = db_connection()
    cursor = conn.cursor()
    sound = None

    if request.method == "GET":
        cursor.execute("SELECT * FROM sound_values WHERE id=%s", (id,))
        rows = cursor.fetchall()
        for r in rows:
            sound = r
        if sound is not None:
            return jsonify(sound), 200
        else:
            return "Something wrong", 404

    if request.method == "PUT":
        sql = """UPDATE sound_values SET value=%s WHERE id=%s"""
        value = request.form["value"]
        updated_sound = {"id": id, "value": value}
        cursor.execute(sql, (value, id))
        conn.commit()
        return jsonify(updated_sound), 201

    if request.method == "DELETE":
        sql = """DELETE FROM sound_values WHERE id=%s"""
        cursor.execute(sql, (id,))
        conn.commit()
        return "The value with id: {} has been deleted.".format(id), 200

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

