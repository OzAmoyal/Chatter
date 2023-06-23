import mongoose from 'mongoose';
import express from 'express';
import bodyParser from 'body-parser';
import cors from 'cors';
import customEnv from 'custom-env';
import userRouter from './routes/userRoute.js';
import tokenRouter from './routes/tokenRoute.js';
import chatRouter from './routes/chatRouter.js';
import socketService from './services/socketService.js';
import {Server} from 'socket.io';
import firebaseRouter from './routes/firebaseRouter.js';
import http from 'http';

const app = express();
const server = http.createServer(app); // Use require instead of import for http module
const io = new Server(server);
io.on('connection', (socket) => {
socketService.initConnection(socket);
});
app.use(bodyParser.json({ limit: '10mb' }));
app.use(bodyParser.urlencoded({ limit: '10mb', extended: true }));

customEnv.env(true, './config');
app.use(express.static('public'));

// Connect to MongoDB
mongoose.connect(process.env.DB_HOST, {
  useNewUrlParser: true,
  useUnifiedTopology: true
});
const db = mongoose.connection;


app.use(cors()); 



app.use(express.json());
app.use(express.static('public'));

app.use('/api/Users', userRouter);
app.use('/api/tokens/', tokenRouter);
app.use('/api/Chats/', chatRouter);
app.use('/api/firebaseToken/', firebaseRouter);

server.listen(3000, () => {
  console.log('Server is running on port 3000');
  console.log('http://localhost:3000')
});
