import tokenService from "../services/tokenService.js";
import messageService from "../services/messageService.js";
import chatModel from "../models/chatModel.js";

export async function sendMessage(req,res){
    var username="";
    try{
        username = await tokenService.isLoggedIn(req.headers.authorization);
    }catch(error){
        res.status(401).send();
        return;
    }
    try{
        const message = await messageService.sendMessage(req.params.id,username,req.body.msg);
        res.status(200).send(message);
        return;
    }catch(error){
        if(error.message==="Unauthorized"){
            res.status(401).send();
            return;
        }else{
        res.status(500).send();
        return;
    }
}
}
export async function getMessages(req,res){
    var username="";
    try{
        username = await tokenService.isLoggedIn(req.headers.authorization);
    }catch(error){
        res.status(401).send();
        return;
    }
    try{
        const messages = await messageService.getMessages(req.params.id,username);
        res.status(200).send(messages);
        return;
    }catch(error){
        if(error.message==="Unauthorized"){
            res.status(401).send();
            return;
        }else{
        res.status(401).send();
        return;
    }
}

    

}
