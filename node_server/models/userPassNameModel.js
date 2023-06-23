
import mongoose from 'mongoose';
const Schema = mongoose.Schema;
const userPassNameSchema = new mongoose.Schema({
  username: {
    type: String,
    required: true
  },
  password: {
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

const UserPassName = mongoose.model('UserPassName', userPassNameSchema);

export default UserPassName