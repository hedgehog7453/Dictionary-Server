# Dictionary Server

This is a simple dictionary system that allows clients to search the meaning(s) of a word, add a new word, and remove an existing word. 

The system follows a client-server architecture in which multiple clients can connect to a multi-threaded server and perform operations concurrently. 

- The server implements a thread-per-request architecture
- Communications between server and clients are via TCP sockets
- Used CSV format for the message exchange protocol 

## Functionalities

- Query the meaning(s) of a given word 
  - Input: Word to search 
  - Output: Meaning(s) of the word 

- Add a new word and one or more of its meanings to the dictionary
  - Input: Word to add, meaning(s) 
  - Output: Status of the operation (e.g., success, duplicate) 

- Remove a word and all of its associated meanings from the dictionary
  - Input: Word to remove 
  - Output: Status of the operation (e.g., success, not found) 

## Running the program

To start the server:

`java –jar DictionaryServer.jar <port> <dictionary-file>` 

- `<port>`: the port number where the server will listen for incoming client connections
- `<dictionary-file>`: the path to the file containing the initial dictionary data

To start the client:

`java –jar DictionaryClient.jar <server-address> <server-port>` 
