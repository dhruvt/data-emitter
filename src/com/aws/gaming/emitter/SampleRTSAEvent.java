package com.aws.gaming.emitter;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;


public class SampleRTSAEvent {
	
	private static Log LOG = LogFactory.getLog(SampleRTSAEvent.class);

	
	String emotion;
	Timestamp timestamp;
	Double confidence;
	
	public SampleRTSAEvent(){
		Faker faker = new Faker();
		this.emotion = Emotion.randomLetter().toString();
		this.confidence = faker.number().randomDouble(2, -100, 100);
		this.timestamp = new Timestamp(System.currentTimeMillis());
	}
	
	
	public String getEmotion() {
		return emotion;
	}
	public void setEmotion(String emotion) {
		this.emotion = emotion;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public Double getConfidence() {
		return confidence;
	}
	public void setConfidence(Double confidence) {
		this.confidence = confidence;
	}
	
	public String toJson(){
		ObjectMapper mapper = new ObjectMapper();
		try{
			return mapper.writeValueAsString(this);
		}catch(Exception e){
			LOG.error("Error Converting MultiPlayer Event to JSON: " +e.getMessage());
			return null;
		}
	}
	
	public enum Emotion{
		HAPPY,
		SAD,
		ANGRY,
		CONFUSED,
		DISGUSTED,
		SURPRISED,
		CALM;
		
		private static final List<Emotion> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
		private static final int SIZE = VALUES.size();
		private static final Random RANDOM = new Random();

		public static Emotion randomLetter()  {
			return VALUES.get(RANDOM.nextInt(SIZE));
		}
	}

}
