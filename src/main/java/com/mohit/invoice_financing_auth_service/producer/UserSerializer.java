package com.mohit.invoice_financing_auth_service.producer;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

public class UserSerializer implements Serializer<UserEventDto> {

    @Override
    public byte[] serialize(String arg0, UserEventDto userDto) {
        byte [] retval=null;
        ObjectMapper objectMapper=new ObjectMapper();
        try{
            retval=objectMapper.writeValueAsString(userDto).getBytes();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return retval;
    }
}
