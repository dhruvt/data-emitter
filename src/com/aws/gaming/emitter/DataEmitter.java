package com.aws.gaming.emitter;


import java.nio.ByteBuffer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import com.amazonaws.services.kinesis.model.PutRecordRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataEmitter implements Runnable {
	
	private static Log LOG = LogFactory.getLog(DataEmitter.class);
    protected AmazonKinesis kinesisClient;
    protected ObjectMapper objectMapper;
    protected String eventType;
    Boolean infiniteLoop = false;
    Integer records = 0;

    public DataEmitter(){
    	try{
    		kinesisClient = AmazonKinesisClientBuilder.standard().withRegion(Regions.US_WEST_2).build();
    		eventType = EmitterConfiguration.getProperty("eventType");
    		infiniteLoop = Boolean.parseBoolean(EmitterConfiguration.getProperty("loopIndefinitely"));
    		if(!infiniteLoop)
    			records = Integer.parseInt(EmitterConfiguration.getProperty("numRecords"));
    		LOG.info("Started Kinesis Client");
    				
		}catch (Exception e){
			LOG.error("Error Loading Kinesis Client: " + e.getMessage());
		}
    	
    }
    
	@Override
	public void run() {
		
		int lines = 0;
		
		do{			
			++lines;
			infiniteLoop = lines==records?false:true;
			emitData(eventType, lines);
				
			try{
				Thread.sleep(100);
			}catch(InterruptedException ie){
				LOG.error("Thread execution was interrupted inside run()");
			}
		} while(infiniteLoop);

	}
	
	private void emitData(String eventType, int lines){
		
		String record = "";
		String partitionkey = "";
		
		if(eventType.equalsIgnoreCase("MultiPlayerEvent"))
		{
			SampleMultiPlayerEvent smpe = new SampleMultiPlayerEvent();
			record = smpe.toJson();
			partitionkey = smpe.getEventId();
		}
		
		if(eventType.equalsIgnoreCase("RTSAEvent"))
		{
			SampleRTSAEvent smpe = new SampleRTSAEvent();
			record = smpe.toJson();
			partitionkey = smpe.getTimestamp().toString();
		}
		
		PutRecordRequest putRecordRequest = new PutRecordRequest();
		putRecordRequest.setStreamName(EmitterConfiguration.getProperty("kinesisStreamName"));
		putRecordRequest.setData(ByteBuffer.wrap(record.getBytes()));
		putRecordRequest.setPartitionKey(partitionkey);
		
		kinesisClient.putRecord(putRecordRequest);
		try{
			LOG.info("Added " + lines + " records to Stream: " + putRecordRequest.getStreamName() + " with data: " + new String(putRecordRequest.getData().array(), "UTF-8"));
		}catch(Exception e){
			LOG.info("Added " + lines + " records to Stream: " + putRecordRequest.getStreamName());
		}
        
	}
	
	@Override
    protected void finalize() throws Throwable {
        super.finalize();
	}
	
	public static void main(String args[]) {
        (new Thread(new DataEmitter())).start();
    }


}
