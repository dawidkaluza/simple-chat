# Simple chat

Simple chat server based on reactive Spring Webflux and bidirectional WebSockets protocol.

Clients can connect to the server and send messages. Each message sent to the server is distributed to all clients currently connected with the server.

## Try it out

Open chat-server dir and run `./gradlew bootRun` to open the server.

Open chat-server dir and run `./gradlew run` to open the client.

You can open multiple clients. 

Once connection is opened, type a message in your terminal and send it by clicking Enter.

If you prefer, you can use online websocket clients, such as https://ws-playground.netlify.app/. As a socket URL, use `ws://localhost:8080/chat`.