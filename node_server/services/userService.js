import e from 'express';
import UserModel from '../models/userModel.js';
import UserPassName from '../models/userPassNameModel.js';
import UserPass from '../models/userpassModel.js';

const createUser = async (username, password, displayName, profilePic) => {
  try {
    // check if the username is already taken
    const existingUser = await UserPassName.findOne({ username });
    if (existingUser) {
      return { error: 'Username is already taken', status: 409 };
    }

    const userpassname = new UserPassName({
      username,
      password,
      displayName,
      profilePic
    });
    await userpassname.save();

    const userpass = new UserPass({
      username,
      password
    });
    await userpass.save();

    const user = new UserModel({
      username,
      displayName,
      profilePic
    });
    await user.save();
    return {status: 200 };
  } catch (error) {
    return { error: 'Internal Server Error', status: 500 };
  }
};
const  isLoginValid = async (username,password) => {
  const existingUser = await UserPass.findOne({ username, password });
    if (existingUser) {
      return true;
    }
    return false;
};
const getUserDetails = async(username)=>{

  const user= await UserModel.findOne({username});
  return {username:user.username,displayName:user.displayName,profilePic:user.profilePic};
}
const getFullUserDetails = async(username)=>{
const user = await UserModel.findOne({username});
return user;
}
export default { createUser, isLoginValid, getUserDetails,getFullUserDetails };
