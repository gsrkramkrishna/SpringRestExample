package com.journaldev.spring.executator.test;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class WorkerThread implements Runnable{
	private static final Logger logger = LoggerFactory.getLogger(WorkerThread.class);
	
	private BlockingQueue<Integer> queue ;
	
	public WorkerThread(BlockingQueue<Integer> queue){
		this.queue = queue;
	}

	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		logger.info("calling service...:"+Thread.currentThread().getName());
		final String uri = "http://localhost:8080/SpringRestExample/rest/emp/dummy";
		RestTemplate restTemplate  = new RestTemplate();
		while(!queue.isEmpty()){
			Integer processId = queue.poll();
			logger.info("calling service invoke1:"+Thread.currentThread().getName()+":"+processId);
			
			invoke(restTemplate, uri,processId );
		}
	}

		public void invoke(RestTemplate restTemplate,String uri,Integer processId){
			logger.info("calling service invoke2:"+Thread.currentThread().getName()+":"+processId);
			HttpHeaders headers = new HttpHeaders();
		    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		     
		    ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
		    logger.info("HttpResult:"+result);
		}
}
