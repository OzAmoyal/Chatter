import mongoose from 'mongoose';
const Schema = mongoose.Schema;
const userPassSchema = new mongoose.Schema({
    username: {
      type: String,
      required: true
    },
    password: {
      type: String,
      required: true
    }
  });
  
  const UserPass = mongoose.model('UserPass', userPassSchema);
  
  export default UserPass;