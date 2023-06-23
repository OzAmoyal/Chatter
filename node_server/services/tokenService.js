import usersService from '../services/userService.js';
import jwt from 'jsonwebtoken';

async function getToken(username, password) {
  try {
    if (await usersService.isLoginValid(username, password)) {
      const token = jwt.sign({ username: username }, process.env.JWT_SECRET, { expiresIn: '1h' });
      return { token, status: 200 };
    }
    return { error: 'Invalid username and/or password', status: 404 };
  } catch (error) {
    return { error: 'Internal server error', status: 500 };
  }
}

async function isLoggedIn(tokenHeader) {
  const extractedTokenString = tokenHeader.split(" ")[1]

  try {
   
    const username = jwt.verify(extractedTokenString, process.env.JWT_SECRET);
    return username.username;
  } catch (error) {
    
    throw error;
  }
}

export default { getToken, isLoggedIn };
