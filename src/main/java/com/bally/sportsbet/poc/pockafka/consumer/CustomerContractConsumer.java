/**
 * 
 */
package com.bally.sportsbet.poc.pockafka.consumer;

import org.apache.avro.generic.GenericData;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.bally.sportsbet.poc.pockafka.mapper.GenericRecordMapper;

import lombok.extern.slf4j.Slf4j;
import management.customer.contact.avro.CustomerContact;

/**
 * @author Orlei Bicheski
 *
 */
@Component
@Slf4j
public class CustomerContractConsumer {

	@KafkaListener(
			containerFactory = "kafkaListenerContainerFactoryCustomerContact",
			topics = "${kafka.topics.customer-contact}", 
			groupId = "${kafka.consumers.customer-contact.group-id}")
	public void consume(GenericData.Record message) {
		log.info("Received Message: Payload: {}", new GenericRecordMapper().toObject(message, CustomerContact.class));
		// .key(), message.partition(), message.value().getName()
	}
}
