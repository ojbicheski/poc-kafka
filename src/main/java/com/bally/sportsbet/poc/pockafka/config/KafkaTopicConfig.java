/**
 * 
 */
package com.bally.sportsbet.poc.pockafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaAdmin;

/**
 * @author Orlei Bicheski
 *
 */
@Configuration
@EnableKafka
public class KafkaTopicConfig {
	
	public static final String SCHEMA_REGISTER_CONFIG = "schema.registry.url";

    @Value(value = "${kafka.bootstrap-address}")
    private String bootstrapAddress;
    
    @Value(value = "${kafka.topics.transfer-message}")
    private String topicTransferMessage;
    
    @Value(value = "${kafka.topics.customer-contact}")
    private String topicCustomerContact;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }
    
    @Bean
    public NewTopic topicTransferMessage() {
         return new NewTopic(topicTransferMessage, 1, (short) 1);
    }
    
    @Bean
    public NewTopic topicCustomerContact() {
         return new NewTopic(topicCustomerContact, 1, (short) 1);
    }

}
