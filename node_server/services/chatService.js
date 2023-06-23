import chatModel from '../models/chatModel.js';
import userModel from '../models/userModel.js';
import messageModel from '../models/messageModel.js';
import userService from '../services/userService.js';

async function getChats(username){
    try {
      const getUser= await userService.getFullUserDetails(username);
      // fetch all the chats from the database
      const chats = await chatModel.find({ users: { $in: [getUser._id] } })
      // create an array to store the desired output
    const output = [];
    
// iterate over each chat object and extract the required information
for (const chat of chats) {
  const chatObject = {
    id: chat._id.toString(),
    user: {},
    lastMessage: {}
  };

  const userPromises = chat.users.map(async (user) => {
    try {
      const userDetails = await userModel.findById(user._id);
      return {id:userDetails._id.toString(),username:userDetails.username,displayName:userDetails.displayName,profilePic:userDetails.profilePic};
    } catch (error) {
      throw error;
    }
  });

  const messagePromises = chat.messages.map(async (message) => {
    try {
      const messageDetails = await messageModel.findById(message._id);
      const senderDetails = await userModel.findById(messageDetails.sender);
      return {id:messageDetails._id.toString(),sender:{username:senderDetails.username,displayName:senderDetails.displayName,profilePic:senderDetails.profilePic},content:messageDetails.content,created:messageDetails.created};
    } catch (error) {
      throw error; 
    }
  });


  try {
    const users = await Promise.all(userPromises);
    const messages = await Promise.all(messagePromises);

    chatObject.user = users.find(u => u.username !== username);
    chatObject.lastMessage = messages.length > 0 ? {id:messages[messages.length - 1].id,created:messages[messages.length - 1].created,content:messages[messages.length - 1].content} : null;


    output.push(chatObject);
  } catch (error) {
    throw error;
  }
}

return output;

    } catch (error) {
      // handle any errors that occur
      res.status(500).json({ error: 'Internal Server Error' });
    }
}

async function createChat(req, username){
    try {

        const otherUser= await userService.getFullUserDetails(req.body.username);
        if(!otherUser){
        throw new error("no such user");
        }

        // create a new chat
        const myUser= await userService.getFullUserDetails(username);
        const chat = new chatModel({users: [myUser, otherUser], messages: []});

        // save the chat to the database
        const savedChat = await chat.save();
        const chatObj = await chatModel.findById(savedChat._id);
        const myID= chatObj._id.toString();
        const responseChat = savedChat.toObject();
        const responseJson = { id: myID, user:{ username:otherUser.username,profilePic:otherUser.profilePic,displayName:otherUser.displayName} };
        return responseJson;
        } catch (error) {
        throw error; 
        
        }
}

    async function getChatById(chatID,username){
      
        const chatObject = {
          id: chatID,
          users: [],
          messages: []
        };
        const chat = await chatModel.findById(chatID);
        const userPromises = chat.users.map(async (user) => {
          try {
            const userDetails = await userModel.findById(user._id);
            
            return {username:userDetails.username,displayName:userDetails.displayName,profilePic:userDetails.profilePic};
          } catch (error) {
            throw error; 
          }
        });
        
        chatObject.users = await Promise.all(userPromises);
        if(!chatObject.users.find(u => u.username === username)){
        throw new Error("Unauthorized");
        }
        const getMessages = async function(chat){
          const messagePromises = chat.messages.map(async (message) => {
          try {
            const messageDetails = await messageModel.findById(message._id);
            const senderDetails = await userModel.findById(messageDetails.sender);
            const returnedMessage = {id:messageDetails._id.toString(),sender:{username:senderDetails.username,displayName:senderDetails.displayName,profilePic:senderDetails.profilePic},content:messageDetails.content,created:messageDetails.created};
            return returnedMessage;
          } catch (error) {
            throw error; // Rethrow the error to be caught in the outer try-catch block
          }
        });
        const returnValue=await Promise.all(messagePromises);
        return returnValue;
      }
        if(chat.messages.length===0){
          chatObject.messages=[];
        }
        else{
        chatObject.messages = await getMessages(chat);
        }
        return chatObject;
    }

async function deleteChatById(chatID){
  try{
    const deleteStatus = await chatModel.findByIdAndDelete(chatID);
    if(!deleteStatus){
      throw new error("no such chat");
    }
    return {status:204};
  }
  catch(error){
    throw error; // Rethrow the error to be caught in the outer try-catch block
  }
}

export default {createChat, getChats, getChatById,deleteChatById};


