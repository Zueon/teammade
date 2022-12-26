package com.mztm.teammade.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.mztm.teammade.dto.ChatDTO;
import com.mztm.teammade.entity.Chat;
import com.mztm.teammade.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
@Log4j2
public class ChatService {

    //ObjectMapper JAVA 개체를 JSON으로 직렬화 또는 JSON문자열을 JAVA객체로 역직렬화
    private final DynamoDBMapper dynamoDBMapper;
    private DynamoDBMapperConfig dynamoDBMapperConfig = new DynamoDBMapperConfig(DynamoDBMapperConfig.SaveBehavior.PUT);

    public List<ChatDTO> getMessage(String roomNum){
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(roomNum));
        DynamoDBScanExpression queryExpression = new DynamoDBScanExpression().withFilterExpression("roomNum = :val1").withExpressionAttributeValues(eav);

        return dynamoDBMapper.scan(Chat.class, queryExpression).stream().map(AppUtils::entityToDto).collect(Collectors.toList());

    }

    public void saveMessage(ChatDTO chatDTO){

        chatDTO.setCreatAt(LocalDateTime.now());

        dynamoDBMapper.save(AppUtils.dtoToEntity(chatDTO),dynamoDBMapperConfig);
    }


}
