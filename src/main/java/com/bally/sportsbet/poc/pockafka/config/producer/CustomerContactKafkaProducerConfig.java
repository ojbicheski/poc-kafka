/**
 * 
 */
package com.bally.sportsbet.poc.pockafka.config.producer;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.bally.sportsbet.poc.pockafka.config.KafkaTopicConfig;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import management.customer.contact.avro.CustomerContact;

/**
 * @author Orlei Bicheski
 *
 */
@Configuration
public class CustomerContactKafkaProducerConfig {

	@Value(value = "${kafka.bootstrap-address}")
	private String bootstrapAddress;

	@Value(value = "${kafka.schema-register}")
	private String schemaRegister;

	@Bean
	public ProducerFactory<String, CustomerContact> producerFactoryCustomerContact() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
		configProps.put(KafkaTopicConfig.SCHEMA_REGISTER_CONFIG, schemaRegister);

		return new DefaultKafkaProducerFactory<String, CustomerContact>(configProps);
	}

	@Bean
	public KafkaTemplate<String, CustomerContact> kafkaTemplateCustomerContact(
			ProducerFactory<String, CustomerContact> producerFactoryCustomerContact) {
		KafkaTemplate<String, CustomerContact> kafkaTemplate = new KafkaTemplate<>(producerFactoryCustomerContact);
//		kafkaTemplate.setMessageConverter(converter);

		return kafkaTemplate;
	}

}
