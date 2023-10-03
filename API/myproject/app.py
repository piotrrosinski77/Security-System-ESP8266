from flask import Flask, request, jsonify


app = Flask(__name__)

water_values = [
{
    "id": 0,
    "value": "231"
},
{
    "id": 1,
    "value": "352"
},
{
    "id": 2,
    "value": "640"
}
]

@app.route('/water', methods=['GET','POST'])
def water():
    if request.method == 'GET':
        if len(water_values) > 0:
            return jsonify(water_values)
        else:
            'Nothing Found', 404
    
    if request.method == 'POST':
        new_value = request.form['value']
        iD = water_values[-1]['id']+1
        
        new_obj = {
            'id': iD,
            'value': new_value,
        }
        water_values.append(new_obj)
        return jsonify(water_values), 201
        
if __name__ == '__main__':
    app.run()
    
