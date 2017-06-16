package com.aws.gaming.emitter;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataEmitter implements Runnable {
	
	private static Log LOG = LogFactory.getLog(DataEmitter.class);
    protected AmazonKinesis kinesisClient;
    protected ObjectMapper objectMapper;
    protected Class className;
    Boolean infiniteLoop = false;

    public DataEmitter(){
    	try{
    		kinesisClient = AmazonKinesisClientBuilder.standard().withRegion(Regions.US_WEST_2).build();
    		className = ClassLoader.getSystemClassLoader().loadClass(EmitterConfiguration.getProperty("className"));
    		LOG.info("Started Kinesis Client");
		}catch (Exception e){
			LOG.error("Error Loading Kinesis Client: " + e.getMessage());
		}
    	
    }
    
	@Override
	public void run() {
		// TODO Auto-generated method stub
		do{
			emitDataFromClass(className);
		} while(infiniteLoop);

	}
	
	private void emitDataFromClass(Class<?> c){
		
	}

}
