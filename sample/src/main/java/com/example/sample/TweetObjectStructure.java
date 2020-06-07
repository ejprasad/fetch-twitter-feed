package com.example.sample;
import org.apache.commons.text.StringEscapeUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TweetObjectStructure {
	
	public TweetObjectStructure() {
		
	}

	@Override
	public String toString() {
		return "JsonPojo [name=" + name + ", tweetVolume=" + tweetVolume + "]";
	}

	@JsonProperty(value="name")
	private String name;
	
	@JsonProperty(value="tweet_volume")
	private Integer tweetVolume;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = StringEscapeUtils.unescapeJava(name);
	}

	public Integer getTweetVolume() {
		return tweetVolume;
	}

	public void setTweetVolume(Integer tweetVolume) {
		if(tweetVolume==null)
			tweetVolume=-1;
		this.tweetVolume = tweetVolume;
	}
}
