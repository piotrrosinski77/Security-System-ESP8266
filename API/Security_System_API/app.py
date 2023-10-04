from flask import Flask, request, jsonify


app = Flask(__name__)

water_values = [
    {"id": 0, "value": "1"},
    {"id": 1, "value": "2"},
    {"id": 2, "value": "3"},
    {"id": 3, "value": "4"},
    {"id": 4, "value": "5"},
]


@app.route("/water", methods=["GET", "POST"])
def water():
    if request.method == "GET":
        if len(water_values) > 0:
            return jsonify(water_values)
        else:
            "Nothing Found", 404

    if request.method == "POST":
        new_value = request.form["value"]
        iD = water_values[-1]["id"] + 1

        new_obj = {
            "id": iD,
            "value": new_value,
        }
        water_values.append(new_obj)
        return jsonify(water_values), 201


@app.route("/waterSpecific/<int:id>", methods=["GET", "POST", "PUT", "DELETE"])
def waterSpecific(id):
    if request.method == "GET":
        for water in water_values:
            if water["id"] == id:
                return jsonify(water)
            pass

    if request.method == "PUT":
        for water in water_values:
            if water["id"] == id:
                water["value"] = request.form["value"]
                updated_water = {"id": id, "value": water["value"]}
                return jsonify(updated_water)

    if request.method == "DELETE":
        for index, water in enumerate(water_values):
            if water["id"] == id:
                water_values.pop(index)
                return jsonify(water_values)


if __name__ == "__main__":
    app.run()
