package com.example.sample;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TweetObjectsArray {

	public TweetObjectsArray(){
		
	}
	
	@JsonProperty("trends")
	public List<TweetObjectStructure> items = new ArrayList<>();
	
	public List<TweetObjectStructure> getItems() {
		return items;
	}

	public void setItems(List<TweetObjectStructure> items) {
		this.items = items;
	}
	
	
}
