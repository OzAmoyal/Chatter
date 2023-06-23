import { signNewToken,removeUsername } from '../controllers/firebaseController.js';
import express from 'express';

const router = express.Router();

router.post('/', signNewToken);
router.delete('/', removeUsername)
export default router;
