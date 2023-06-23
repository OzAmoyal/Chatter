# Chat-Application

## Introduction

#### Android Client
This repository contains an Android client application for a chatbot system. The application allows users to register, log in, and engage in chat conversations with other contacts. It incorporates various screens, including login, registration, chat list, single chat, and settings. The application communicates with the server using API endpoints to perform authentication, manage contacts, chats, and messages. Additionally, the Android client saves relevant information locally using an SQLite database and utilizes push notifications for message updates.

#### Server
The server component of the Chat-Application which was built using Node.js and Express.js, provides the necessary backend logic and API endpoints for user authentication, contact management, chat conversations, and message exchange. It leverages the power of MongoDB as the database to store user information, chat data, etc. Additionally, the server has been updated to support the Android client application, enabling communication with both web clients and Android clients using Firebase services. With WebSocket communication and Firebase integration for push notifications, the server ensures real-time and efficient message delivery, enhancing the overall chat experience across different platforms.
<br><br><br>

## Demo Video of the Chat App

### Please watch the video before installing the app! Click to watch:
[![Video Title](https://img.youtube.com/vi/UXQj3TBe2os/0.jpg)](https://www.youtube.com/watch?v=UXQj3TBe2os)


## Description
The Chat-Application is a web-based chatbot that provides a user-friendly interface for seamless communication. It offers the following features:
<br><br><br>
💥 <b> Login screen: </b>Users can authenticate themselves by entering their credentials.

💥 <b> Registration screen: </b> New users can create an account by providing the necessary details.

💥 <b> Chat list screen: </b>Displays a list of chats with contacts. Once logged in, users can view their contacts and engage in real-time chat conversations.

💥 <b> Single Chat screen: </b> Opens a chat conversation with a selected contact.

💥 <b> Settings screen: </b> Allows editing relevant application settings, such as the server address and theme.


<br><br>

## Design Considerations & Repository Description

The Android client application follows a uniform design theme across all screens. The design incorporates appropriate colors, text fonts, and button styles to provide a visually appealing and user-friendly experience. Care has been taken to ensure a reasonable design for each screen, maintaining consistency throughout the application.. The backend is powered by Node.js and Express.js, providing the server-side logic and API endpoints. MongoDB serves as the database for storing user information and chat data. The server also serves the static files for the React application, eliminating the need for a separate build step.

Inside the cloned repository, you will find two additional folders, namely "Android-app" and "Server."
<br><br>
<b>Android_app:</b> This folder contains all the code and resources related to the Android client application. It is responsible for the frontend of the chatbot system on the Android platform. Within the "Android-app" folder, you can expect to find Java classes, XML layouts, assets, and other resources necessary for building the user interface and functionalities of the Android client application. The folder also includes the necessary dependencies and build configurations for the Android app development environment.
<br><br>
<b>node_server:</b> This folder contains all the server-side code for the chatbot system. It encompasses the backend logic required to handle user authentication, manage contacts, chats, and messages, as well as interact with the database. Following the Model-View-Controller (MVC) architecture, the "Server" folder includes files related to models, views, controllers, routes, and any additional server configuration or utility files. The server is implemented using technologies such as Node.js, Express.js, and integrates Firebase services for push notifications and real-time message updates. Additionally, the server utilizes an SQLite database to store chats, messages, and relevant information locally within the Android client application.
<br><br>



## Installation - Important!
1. Make sure you have nodeJS, npm, and MongoDB installed on your local machine, and the MongoDB is running.

2. Clone the repository to your local machine:
    ```
    git clone https://github.com/TopazAvraham/chatAss4
    ```

#### To start the server
1. Navigate to the folder where you cloned the repository - chatAss4
2. Navigate to the folder of the server by executing the command ``` cd Server ```
3. execute the command ``` npm install ``` to download node_modules folder for the server.
4. execute the command ``` npm start ``` to start the server.
5. Access the chatbot interface in your browser by visiting http://localhost:3000
6. Register a new account, log in, add contacts and start chatting with them.
<br><br>

#### To start the android app:
1. In andorid studio, open the folder called Android_app (which is inside chatAss4)
2. Start the app on the emulator by pressing the green button.
3. Register a new account, log in, add contacts and start chatting with them.
<br><br>
## Built With

### Frontend

Built With
Frontend (Android Client)

- Java
- XML
  
The frontend of the Android client application is developed using Java for the application logic and XML for defining the user interface layouts. These technologies enable the creation of a native Android app that provides a rich and interactive user experience.


### Backend (MVC)
- Node.js
- Express.js
- MongoDB
-SQLite

The backend of the application is implemented using Node.js and Express.js. It follows a server-client architecture, where the Android client communicates with the server using API endpoints for user authentication, contact management, chat conversations, and message exchange. The server utilizes SQLite as the local database to store chats, messages, and relevant information within the Android client application.

<br />
