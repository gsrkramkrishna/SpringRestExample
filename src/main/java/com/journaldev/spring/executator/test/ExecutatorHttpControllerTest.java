package com.journaldev.spring.executator.test;

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

@Controller
public class ExecutatorHttpControllerTest {
	
	private static final Logger logger = LoggerFactory.getLogger(ExecutatorHttpControllerTest.class);
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public void execute() {
		logger.info("calling service...");
		ExecutorService executorService = Executors.newScheduledThreadPool(300);
		BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(100);
		for(int i=0;i<100;i++){
			queue.add(i);
		}
		for (int i=0;i<300;i++){
			WorkerThread wt = new WorkerThread(queue);
			executorService.submit(wt);
		}
		while(queue.isEmpty()){
			logger.info("calling service shutdown...");
			executorService.shutdown();
		}
		
	}
	
	

}
