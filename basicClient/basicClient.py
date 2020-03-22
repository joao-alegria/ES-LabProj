
from flask import Flask, render_template
from flask_sockets import Sockets
from kafka import KafkaConsumer
import threading
import json

app = Flask(__name__)
sockets = Sockets(app)

t1 = None

myWS = None


@sockets.route('/data')
def echo_socket(ws):
    global myWS
    while not ws.closed:
        if myWS == None:
            myWS = ws
        else:
            continue


def startMapping():
    global myWS
    consumer = KafkaConsumer(
        bootstrap_servers=['kafka:9092'], auto_offset_reset='smallest')

    consumer.subscribe(["iss", "iss-logs"])
    for msg in consumer:
        if msg.topic == "iss":
            myWS.send(json.dumps(
                {"topic": "iss", "value": msg.value.decode("utf-8")}))
        else:
            myWS.send(json.dumps(
                {"topic": "iss-logs", "value": msg.value.decode("utf-8")}))


@app.route('/')
def serveIndex():
    global t1
    if t1 == None:
        t1 = threading.Thread(target=startMapping)
        t1.start()
    return render_template("index.html")


if __name__ == "__main__":
    from gevent import pywsgi
    from geventwebsocket.handler import WebSocketHandler
    server = pywsgi.WSGIServer(('', 5000), app, handler_class=WebSocketHandler)
    server.serve_forever()
