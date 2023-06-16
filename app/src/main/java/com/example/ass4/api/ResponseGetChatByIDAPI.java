package com.example.ass4.api;

import java.util.List;

//get/api/chats/{id}
public class ResponseGetChatByIDAPI {
    private String id;
    private List<User> users;
    public List<Message> messages;

    // Constructor
    public ResponseGetChatByIDAPI(String id, List<User> users, List<Message> messages) {
        this.id = id;
        this.users = users;
        this.messages = messages;
    }

    // Getters
    public String getId() {
        return id;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public Message getLastMessage() {
        return messages.get(messages.size() - 1);
    }

    // User class
    public static class User {
        private String username;
        private String displayName;
        private String profilePic;

        // Constructor
        public User(String username, String displayName, String profilePic) {
            this.username = username;
            this.displayName = displayName;
            this.profilePic = profilePic;
        }

        // Getters
        public String getUsername() {
            return username;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getProfilePic() {
            return profilePic;
        }
    }

    // Message class
    public static class Message {
        private String id;
        private User sender;
        private String content;
        private String created;

        // Constructor
        public Message(String id, User sender, String content, String created) {
            this.id = id;
            this.sender = sender;
            this.content = content;
            this.created = created;
        }

        // Getters
        public String getId() {
            return id;
        }

        public User getSender() {
            return sender;
        }

        public String getContent() {
            return content;
        }

        public String getCreated() {
            return created;
        }
    }
}
