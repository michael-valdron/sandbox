import json
from json.decoder import JSONDecodeError
import os
from flask import Flask, request, jsonify

DB = '/tmp/data.txt'
INDENT = 2
app = Flask(__name__)

@app.route('/', methods=['GET'])
def query_records():
    name = request.args.get('name')

    try:
        with open(DB, 'r') as f:
            data = f.read()
            records = json.loads(data)

            for r in records:
                if r['name'] == name:
                    return jsonify(r)
    except FileNotFoundError:
        return jsonify({'error': "database not found"})
    
    return jsonify({'error': ("record with \'%s\' was not found" % name) if name else "no name was specified"})


@app.route('/', methods=['PUT'])
def create_record():
    try:
        record = json.loads(request.data)
        
        if not os.path.exists(DB):
            open(DB, 'x').close()

        with open(DB, 'r') as f:
            data = f.read()

        if not data:
            records = [record]
        else:
            records = json.loads(data)
            records.append(record)
        
        with open(DB, 'w') as f:
            f.write(json.dumps(records, indent=INDENT))
    except JSONDecodeError as err:
        return jsonify({'error': err.msg})
    
    return jsonify(record)


@app.route('/', methods=['POST'])
def update_record():
    try:
        record = json.loads(request.data)
        new_records = []

        with open(DB, 'r') as f:
            data = f.read()
            records = json.loads(data)
            
        for r in records:
            if r['name'] == record['name']:
                r['email'] = record['email']
            new_records.append(r)
        
        with open(DB, 'w') as f:
            f.write(json.dumps(new_records, indent=INDENT))
    except JSONDecodeError as err:
        return jsonify({'error': err.msg})
    except FileNotFoundError:
        return jsonify({'error': "database not found"})

    return jsonify(record)


@app.route('/', methods=['DELETE'])
def delete_record():
    try:
        record = json.loads(request.data)
        new_records = []

        with open(DB, 'r') as f:
            data = f.read()
            records = json.loads(data)
            
            for r in records:
                if r['name'] == record['name']:
                    continue
                new_records.append(r)
        
        with open(DB, 'w') as f:
            f.write(json.dumps(new_records, indent=INDENT))
    except JSONDecodeError as err:
        return jsonify({'error': err.msg})
    except FileNotFoundError:
        return jsonify({'error': "database not found"})

    return jsonify(record)

app.run(debug=True)
