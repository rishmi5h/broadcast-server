# Java Broadcast Server

A simple Java-based broadcast server and client application.

## Files

1. **BroadcastApp.java**
   - Main entry point
   - Usage: `java BroadcastApp [start|connect]`

2. **BroadcastServer.java**
   - Implements server functionality
   - Manages client connections and message broadcasting

3. **BroadcastClient.java**
   - Implements client functionality
   - Connects to server, sends and receives messages

4. **ClientHandler.java**
   - Manages individual client connections on the server side
   - Handles message reception and client disconnection

## Setup

1. Compile: `javac *.java`
2. Start server: `java BroadcastApp start`
3. Connect client: `java BroadcastApp connect`

## Features

- Multi-client support
- Real-time message broadcasting
- Graceful client disconnection
- Server shutdown hook

## Usage

- Server runs on port 5005 by default
- Clients connect to localhost:5005
- Type 'exit' to disconnect a client

## Note

Modify PORT in BroadcastApp.java to change the default port.


[challenge](https://roadmap.sh/projects/broadcast-server)