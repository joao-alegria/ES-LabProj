
from flask import Flask, render_template
from flask_sockets import Sockets
from kafka import KafkaConsumer
import threading
import json

app = Flask(__name__)
sockets = Sockets(app)


def startMapping(ws):
    consumer = KafkaConsumer(
        bootstrap_servers=['kafka:9092'], auto_offset_reset='smallest')

    consumer.subscribe(["iss", "iss-logs"])
    for msg in consumer:
        if msg.topic == "iss":
            ws.send(json.dumps(
                {"topic": "iss", "value": msg.value.decode("utf-8")}))
        else:
            ws.send(json.dumps(
                {"topic": "iss-logs", "value": msg.value.decode("utf-8")}))


@sockets.route('/data')
def echo_socket(ws):
    t1 = threading.Thread(target=startMapping, args=(ws,))
    t1.start()
    while not ws.closed:
        message = ws.receive()
        if message == "close":
            break


@app.route('/')
def serveIndex():
    return render_template("index.html")


if __name__ == "__main__":
    from gevent import pywsgi
    from geventwebsocket.handler import WebSocketHandler
    server = pywsgi.WSGIServer(('', 5000), app, handler_class=WebSocketHandler)
    server.serve_forever()
