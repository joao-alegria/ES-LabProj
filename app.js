// include http module in the file
var alert = require('alert-node')
var http = require('http');

// create a server listening on 8087
http.createServer(function (req, res) {
    res.writeHead(200, { 'Content-Type': 'text/html' });
    res.write("<table>")


    var kafka = require("kafka-node"),
        Consumer = kafka.Consumer,
        client = new kafka.KafkaClient(),
        consumer = new Consumer(client, [{ topic: "iss-logs", partition: 0 }], { fromOffset: 'latest' }),
        consumer2 = new Consumer(client, [{ topic: "iss", partition: 0 }], { fromOffset: 'latest' });

    consumer.on("message", (message) => {
        console.log(message);
        res.write("<tr><td>" + message.value + "</td></tr>");

    });

    consumer2.on("message", (message) => {
        alert(message);
    });

    consumer.on("error", (error) => {
        console.log(error)
    })





    // write the response and send it to the client
}).listen(8087);