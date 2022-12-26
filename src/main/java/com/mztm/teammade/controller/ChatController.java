package com.mztm.teammade.controller;


import com.mztm.teammade.dto.ChatDTO;
import com.mztm.teammade.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Log4j2
@CrossOrigin("*")
public class ChatController {
    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatService chatService;


    //@MessageMapping : Websocket으로 들어오는 메시지 발행을 처리합니다.
    // 클라이언트에서 prefix를 붙여서 /pub/chat/message로 발행 요청을 하면
    @MessageMapping("/message")
    public ResponseEntity<?> message(ChatDTO message){
        //
        log.info(message);
        log.error(message);
        log.debug(message);
        try {
            chatService.saveMessage(message);
            messagingTemplate.convertAndSend("/sub/roomNum/"+message.getRoomNum(),message);
        }catch(Exception e){
            e.printStackTrace();
           return  new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("success",HttpStatus.OK);
    }

    @GetMapping("/roomNum/{roomNum}")
    public ResponseEntity<?> getMessage(@PathVariable String roomNum){

        try {

            return new ResponseEntity(chatService.getMessage(roomNum), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
           return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }








// ================================== 1차 채팅============================
//    private final ChatService chatService;
//
//    @PostMapping
//    public ChatRoom createRoom(@RequestParam String name){
//        return chatService.createRoom(name);
//    }
//
//    @GetMapping
//    public List<ChatRoom> findAllRoom(){
//        return chatService.findAllRoom();
//    }
}
