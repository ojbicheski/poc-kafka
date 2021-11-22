/**
 * 
 */
package com.bally.sportsbet.poc.pockafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Orlei Bicheski
 *
 */
@Component
@Slf4j
public class TransferMessageConsumer {

	@KafkaListener(
			containerFactory = "kafkaListenerContainerFactoryString", 
			topics = "${kafka.topics.transfer-message}", 
			groupId = "${kafka.consumers.transfer-message.group-id}")
	public void listenWithHeaders(@Payload String message, 
			@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key, 
			@Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
	      log.info("Received Message: [{}, {}] from partition: {}", key, message, partition);
	}
}
