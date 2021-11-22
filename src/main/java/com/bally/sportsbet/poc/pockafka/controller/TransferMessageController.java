/**
 * 
 */
package com.bally.sportsbet.poc.pockafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Controller;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Orlei Bicheski
 *
 */
@Controller
@RequestMapping("/api/transfer-messages")
@Slf4j
public class TransferMessageController {

	@Value(value = "${kafka.topics.transfer-message}")
    private String topic;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@PostMapping("/send/{key}")
	public ResponseEntity<Void> sendMessage(@PathVariable String key, @RequestBody String message) {
		ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, key, message);
		
		future.addCallback(callback());

		return ResponseEntity.ok().build();
	}

	private ListenableFutureCallback<? super SendResult<String, String>> callback() {
		return new ListenableFutureCallback<SendResult<String, String>>() {
	        @Override
	        public void onSuccess(SendResult<String, String> result) {
	            log.info("Sent message=[{}, {}] with offset=[{}]", 
	            		result.getProducerRecord().key(), 
	            		result.getProducerRecord().value(), 
	            		result.getRecordMetadata().offset());
	        }
	        @Override
	        public void onFailure(Throwable ex) {
	        	log.error("Unable to send message due to : {}", ex.getMessage());
	        }
	    };
	}
	
}
