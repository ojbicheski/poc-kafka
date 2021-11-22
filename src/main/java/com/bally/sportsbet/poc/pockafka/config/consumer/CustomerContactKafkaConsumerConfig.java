/**
 * 
 */
package com.bally.sportsbet.poc.pockafka.config.consumer;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import com.bally.sportsbet.poc.pockafka.config.KafkaTopicConfig;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import management.customer.contact.avro.CustomerContact;

/**
 * @author Orlei Bicheski
 *
 */
@EnableKafka
@Configuration
public class CustomerContactKafkaConsumerConfig {

	@Value(value = "${kafka.bootstrap-address}")
	private String bootstrapAddress;

	@Value(value = "${kafka.schema-register}")
	private String schemaRegister;

	@Value(value = "${kafka.consumers.customer-contact.group-id}")
	private String groupId;

	@Bean
	public ConsumerFactory<String, CustomerContact> consumerFactoryCustomerContact() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
		props.put(KafkaTopicConfig.SCHEMA_REGISTER_CONFIG, schemaRegister);

		return new DefaultKafkaConsumerFactory<>(props);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, CustomerContact> kafkaListenerContainerFactoryCustomerContact(
			ConsumerFactory<String, CustomerContact> consumerFactoryCustomerContact) {
		ConcurrentKafkaListenerContainerFactory<String, CustomerContact> factory = new ConcurrentKafkaListenerContainerFactory<>();

		factory.setConsumerFactory(consumerFactoryCustomerContact);
//		factory.setBatchListener(true);
//		factory.setMessageConverter(new BatchMessagingMessageConverter(converter));
//		factory.getContainerProperties().setAckMode(AckMode.MANUAL);

		return factory;
	}

}
