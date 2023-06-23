import mongoose from 'mongoose';
const Schema = mongoose.Schema;
const userSchema = new mongoose.Schema({
    username: {
      type: String,
      required: true
    },
    displayName: {
      type: String,
      default: ''
    },
    profilePic: {
      type: String,
      default: ''
    }
  });
  
  const UserModel = mongoose.model('UserModel', userSchema);
  export default UserModel;
