package org.mbr.authservice.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import org.mbr.authservice.eventProducer.UserInfoEvent;
import org.mbr.authservice.model.UserInfoDto;

import java.util.Map;

public class UserInfoSerializer implements Serializer<UserInfoEvent> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey){

    }
    @Override
    public byte[] serialize(String arg0, UserInfoEvent arg1){
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            retVal = objectMapper.writeValueAsString(arg1).getBytes();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return retVal;
    }
    @Override
    public void close(){}
}
