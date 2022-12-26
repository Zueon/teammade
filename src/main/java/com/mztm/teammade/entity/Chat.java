package com.mztm.teammade.entity;


import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "chat")
public class Chat {
    private int cid;

    private String msg;


    private String sender;


    private String roomNum;


    private LocalDateTime creatAt;


    @DynamoDBHashKey
    public int getCid(){return cid;}
    @DynamoDBAttribute
    public String getRoomNum() {
        return roomNum;
    }

    @DynamoDBAttribute
    public String getMsg() {
        return msg;
    }

    @DynamoDBAttribute
    public String getSender() {
        return sender;
    }



    @DynamoDBAttribute
    @DynamoDBRangeKey
    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    public LocalDateTime getCreatAt() {
        return creatAt;
    }


    static public class LocalDateTimeConverter implements DynamoDBTypeConverter<String, LocalDateTime>{
        @Override
        public String convert(final LocalDateTime localDateTime) {
            return localDateTime.toString();
        }

        @Override
        public LocalDateTime unconvert(final String s) {
            return LocalDateTime.parse(s);
        }
    }
}
