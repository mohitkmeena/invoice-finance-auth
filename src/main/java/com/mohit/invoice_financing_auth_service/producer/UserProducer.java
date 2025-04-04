package com.mohit.invoice_financing_auth_service.producer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class UserProducer {

    @Value("${spring.kafka.company-topic-name}")
    private String COMPANY_TOPIC_NAME;
    @Value("${spring.kafka.investor-topic-name}")
    private String INVESTOR_TOPIC_NAME;

    private final KafkaTemplate<String,UserEventDto> template;
    @Autowired
    public UserProducer(KafkaTemplate<String ,UserEventDto> template){
        this.template=template;
    }

    public void sendCompanytoSave(UserEventDto userDto){
        Message<UserEventDto> message= MessageBuilder.withPayload(userDto).setHeader(KafkaHeaders.TOPIC, COMPANY_TOPIC_NAME).build();
        template.send(message);
        System.out.println("user sent ");

    }
    public void sendInvestortoSave(UserEventDto userDto){
        Message<UserEventDto> message= MessageBuilder.withPayload(userDto).setHeader(KafkaHeaders.TOPIC, INVESTOR_TOPIC_NAME).build();
        template.send(message);
        System.out.println("user sent ");

    }

}