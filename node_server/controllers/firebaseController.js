import firebaseMessageService from '../services/firebaseMessageService.js';
import tokenService from '../services/tokenService.js';


export const signNewToken = async (req, res) => {
var username="";
try{
    username = await tokenService.isLoggedIn(req.headers.authorization);
}catch(error){
    res.status(401).send();
    return;
}
  firebaseMessageService.signNewToken(username,req.body.firebaseToken);
  res.status(200).send();
  return;
   
};
export const removeUsername = async (req, res) => {
  var username="";
try{
    username = await tokenService.isLoggedIn(req.headers.authorization);
}catch(error){
    res.status(401).send();
    return;
}
  firebaseMessageService.removeUsername(username);
  res.status(200).send();
  return;
};
