import tokenService from "./tokenService.js";
import chatService from "./chatService.js";
const userSockets = {};
async function initConnection(socket) {
    socket.emit('identify', 'Hello client!')
    socket.on('token', async (token) => {
      const username= await tokenService.isLoggedIn(token);
      userSockets[username]=socket;
    });
    socket.on('message', async (data) => {
      const username= data.sender;
      const chat = await chatService.getChatById(data.chatID,username);
      const otherUser= chat.users.find(u => u.username !== username);
      if(userSockets[otherUser.username]){
        userSockets[otherUser.username].emit('update', {chatID:data.chatID,sender:username,content:data.content});
      }
    });
    socket.on('logout', async (token) => {
      const username= await tokenService.isLoggedIn(token);
      delete userSockets[username];
    });
  }
  async function sendMessage(chatID,username,content){
  userSockets[username].emit('update', {chatID:chatID,sender:username,content:content});
  }
  function isConnectedToSocket(username){
  return userSockets[username]!==undefined
  }
  export default {initConnection,sendMessage,isConnectedToSocket};