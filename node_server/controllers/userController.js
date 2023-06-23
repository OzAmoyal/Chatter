import usersService from '../services/userService.js';
import tokenService from '../services/tokenService.js';

export const createUser = async (req, res) => {
  const newUser = await usersService.createUser(req.body.username,req.body.password,req.body.displayName,req.body.profilePic);
  if(newUser.status===200){
  res.status(200).send();
  return;
  }else{
    res.status(409).send();
    return;
  }

};

export const getUserDetails = async (req, res) => {
  
  try {
    const username = await tokenService.isLoggedIn(req.headers.authorization);
    if(username!==req.params.username){
      res.status(401).send();
      return;
    }
    res.json(await usersService.getUserDetails(req.params.username));
    return;
  } catch (error) {
    res.status(401).send();
    return;
  }
};
