import tokenService from '../services/tokenService.js';


export const getToken = async (req, res) => {
  const getTokenStatus= await tokenService.getToken(req.body.username,req.body.password);
  if(getTokenStatus.status==404){
  res.status(404).send('Incorrect username and/or password');
  return;
  }else{
  res.status(200).send(getTokenStatus.token);
  return;
  }
   
};
