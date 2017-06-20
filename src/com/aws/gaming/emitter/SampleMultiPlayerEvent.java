package com.aws.gaming.emitter;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

public class SampleMultiPlayerEvent {
	
	private static Log LOG = LogFactory.getLog(SampleMultiPlayerEvent.class);
	
	String eventId;
	String eventType;
	String country;
	String mapName;
	Double posX;
	Double posY;
	Timestamp timestamp;
	String playerName;
	
	public SampleMultiPlayerEvent(){
		Faker faker = new Faker();
		
		this.eventId = UUID.randomUUID().toString();
		this.eventType = EventType.randomLetter().toString();
		this.country = faker.address().country();
		this.posX = faker.number().randomDouble(2, 0, 500);
		this.posY = faker.number().randomDouble(0, 50, 250);
		this.playerName = faker.name().username();
		this.mapName = MapType.randomLetter().toString();
		this.timestamp = new Timestamp(System.currentTimeMillis());
	}
	
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getMapName() {
		return mapName;
	}
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	public Double getPosX() {
		return posX;
	}
	public void setPosX(Double posX) {
		this.posX = posX;
	}
	public Double getPosY() {
		return posY;
	}
	public void setPosY(Double posY) {
		this.posY = posY;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
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
	
	public enum MapType{
		dusty,
		too_dusty,
		da_train,
		cs_eataly;
		
		private static final List<MapType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
		private static final int SIZE = VALUES.size();
		private static final Random RANDOM = new Random();

		public static MapType randomLetter()  {
			return VALUES.get(RANDOM.nextInt(SIZE));
		}
	}
	
	public enum EventType{
		spawn,
		kill,
		death,
		jump;
		
		private static final List<EventType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
		private static final int SIZE = VALUES.size();
		private static final Random RANDOM = new Random();

		public static EventType randomLetter()  {
			return VALUES.get(RANDOM.nextInt(SIZE));
		}
	}

}
