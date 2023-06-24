import admin from 'firebase-admin';
import serviceAccount from '../chatter-key.json'  assert { type: 'json' };;

const userTokens = {};

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  // Add any additional configuration options you may need
});

function sendMessage(chatId, username, messageContent,senderDisplayname) {
  const message = {
    token: userTokens[username],
    notification: {
      title: senderDisplayname,
      body: messageContent,
    },
    data: {
      chatID: chatId,
    },
  };
  
  admin.messaging().send(message)
    .then((response) => {
    })
    .catch((error) => {
      userTokens[username] = undefined;
    });
}

function signNewToken(username, firebaseToken) {
  userTokens[username] = firebaseToken;
  return true;
}

function isConnectedToFirebase(username) {
  return userTokens[username] !== undefined;
}

function removeUsername(username) {


  delete userTokens[username];

}

export default { sendMessage, signNewToken, isConnectedToFirebase, removeUsername };
