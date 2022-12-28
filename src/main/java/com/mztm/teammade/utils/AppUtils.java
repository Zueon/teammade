package com.mztm.teammade.utils;

import com.mztm.teammade.dto.ChatDTO;
import com.mztm.teammade.entity.Chat;
import org.springframework.beans.BeanUtils;

public class AppUtils {

    public static ChatDTO entityToDto(Chat chat){
        ChatDTO chatDTO = new ChatDTO();
        BeanUtils.copyProperties(chat, chatDTO);
        return chatDTO;
    }

    public static Chat dtoToEntity(ChatDTO chatDTO){
        Chat chat = new Chat();
        BeanUtils.copyProperties(chatDTO, chat);
        return chat;
    }
}
