import mongoose from 'mongoose';
import UserModel from './userModel.js';

const messageSchema = new mongoose.Schema({
  
    created: {
      type: Date,
      required: true,
      default: Date.now
    },
    sender: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'UserModel',
    },
    content: {
      type: String,
      default: ''
    }
  });
  
 
  const MessageModel= mongoose.model('MessageModel', messageSchema);
  export default MessageModel;