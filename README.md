# Friendify

Friendify is a client-server social media and messaging application written in Java. Users can create accounts, search for other users, manage friends and blocked users, control messaging privacy, and send direct messages through a graphical interface.

The project was developed as a team project for CS 180 and focuses on object-oriented programming, networking, persistent data storage, GUI development, and software testing.

## Features

* Account creation and login
* User search
* Friend and unfriend functionality
* Block and unblock functionality
* Direct messaging
* Message deletion
* Messaging privacy settings
* Password changes
* Profile picture support
* Persistent user data
* Client-server communication
* Input validation and error handling

## Tech Stack

**Language**

* Java

**User Interface**

* Java Swing
* AWT event handling

**Networking**

* Java `Socket`
* Java `ServerSocket`
* TCP client-server architecture
* BufferedReader and PrintWriter streams

**Data Storage**

* File-based persistent storage
* Java file I/O
* Text files for user account information
* Text files for message history

**Testing**

* Java unit test classes covering account, messaging, search, and application behavior

## Architecture

Friendify follows a client-server architecture.

```text
             ┌──────────────────────┐
             │      User / GUI      │
             │     Java Swing       │
             └──────────┬───────────┘
                        │
                        ▼
             ┌──────────────────────┐
             │   MainApplication    │
             │       Client         │
             └──────────┬───────────┘
                        │
                 TCP Socket :4545
                        │
                        ▼
             ┌──────────────────────┐
             │      AppServer       │
             │      Java Server     │
             └──────────┬───────────┘
                        │
           ┌────────────┼─────────────┐
           ▼            ▼             ▼
      LoginMethods  SearchMethods  DirectMessageMethods
           │            │             │
           └────────────┼─────────────┘
                        ▼
                     User
                        │
                        ▼
                 File Storage
```

The client handles user interaction and sends commands to the server. The server performs application logic and accesses stored user and message data.

This separation keeps sensitive account and data-management operations on the server side rather than directly inside the user interface.

## Networking

Friendify uses Java's built-in networking APIs to establish communication between the client and server.

The server creates a:

```java
ServerSocket
```

on port:

```text
4545
```

and waits for clients to connect.

The client connects using a Java:

```java
Socket
```

to:

```text
localhost:4545
```

Communication between the client and server occurs through input and output streams using:

* `BufferedReader`
* `InputStreamReader`
* `PrintWriter`
* `OutputStreamWriter`

The client sends commands and data to the server, and the server interprets those commands and returns the appropriate result.

Examples include:

```text
Login request
        ↓
Client
        ↓
TCP Socket
        ↓
Server
        ↓
LoginMethods
        ↓
Stored user data
        ↓
Response sent to client
```

This architecture separates the application's interface from its data-processing logic.

## Data Storage

Friendify uses file-based persistence instead of a traditional relational database.

Each user has stored account information that can include:

* Username
* Password
* Friends
* Blocked users
* Messaging privacy settings
* Profile information

Direct-message conversations are also stored in text files so that message histories remain available across application sessions.

Java file I/O is used to read and update this information.

## Main Components

### `AppServer.java`

Runs the Friendify server.

Responsibilities include:

* Opening the server socket
* Accepting client connections
* Receiving commands from clients
* Calling the appropriate application methods
* Reading and updating stored information
* Returning responses to the client

### `MainApplication.java`

Acts as the main client application.

Responsibilities include:

* Connecting to the server
* Displaying the graphical interface
* Receiving user input
* Sending commands to the server
* Processing server responses
* Handling navigation and errors

### `LoginMethods.java`

Handles account authentication and creation.

Responsibilities include:

* Creating accounts
* Checking whether usernames already exist
* Validating usernames and passwords
* Reading stored login information

### `User.java`

Represents and manages user account information.

Responsibilities include:

* Managing friends
* Managing blocked users
* Changing passwords
* Updating profile information
* Managing messaging privacy settings
* Reading and updating stored user data

### `DirectMessageMethods.java`

Handles direct-message functionality.

Responsibilities include:

* Sending messages
* Reading conversation history
* Deleting messages
* Updating stored conversations

### `SearchMethods.java`

Provides user-search functionality by finding usernames that contain the user's search input.

## Running the Project

### Requirements

* Java Development Kit (JDK)

Clone the repository:

```bash
git clone https://github.com/ummjanavi/cs-180-team-project.git
cd cs-180-team-project
```

### 1. Compile the server

```bash
javac AppServer.java
```

### 2. Start the server

```bash
java AppServer
```

Leave the server running.

### 3. Open another terminal and compile the client

```bash
javac MainApplication.java
```

### 4. Run the client

```bash
java MainApplication
```

The client connects to the server on:

```text
localhost:4545
```

## User Workflow

After launching the application, users can:

1. Create an account or log in.
2. Search for another user.
3. Add or remove users as friends.
4. Block or unblock users.
5. Send and delete direct messages.
6. Modify account and messaging privacy settings.
7. Log out while preserving account and conversation data.

## Testing

The project includes test classes for several major components:

```text
AppServerTest.java
DirectMessageMethodsTests.java
LoginMethodsTest.java
MainApplicationTest.java
SearchMethodsTest.java
UserTest.java
```

Tests cover functionality such as:

* Successful and unsuccessful logins
* Account creation
* Duplicate usernames
* Password changes
* Invalid user input
* User search
* Friend and block functionality
* Sending messages
* Message persistence
* Navigation between menus
