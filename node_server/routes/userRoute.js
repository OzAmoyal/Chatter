import { createUser,getUserDetails } from '../controllers/userController.js';
import express from 'express';

const router = express.Router();

router.post('/', createUser);
router.get('/:username',getUserDetails)
export default router;
