package com.example.sample;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class App {
	
	public static void main(String args[]) throws Exception {
		
		String client_key = "";
		String client_secret = "";
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpResponse response = null;
	    HttpPost httppost = new HttpPost("https://api.twitter.com/oauth2/token");
	    try {
	    	String base64Credentials = Base64.getEncoder().encodeToString((client_key + ":" + client_secret).getBytes());
	    	
	    	httppost.addHeader("Authorization", "Basic " + base64Credentials);
	    	httppost.addHeader("Content-Type", "application/x-www-form-urlencoded");
	    	httppost.setEntity(new StringEntity("grant_type=client_credentials"));
	    	
	        response = httpclient.execute(httppost);
	        
	        if(response.getStatusLine().getStatusCode()==200) {
	        	
	        	HttpEntity jsonResponse = response.getEntity();
		        String accessToken = "";
		        
		        if (null != jsonResponse) {
		            try (InputStream inputStream = jsonResponse.getContent()) {
		                ObjectMapper mapper = new ObjectMapper();
		                Map<String, Object> jsonMap = mapper.readValue(inputStream, Map.class);
		                accessToken = jsonMap.get("access_token").toString();
		                System.out.println(accessToken);
		            }
		        }
	        	
	        	HttpGet httpget = new HttpGet("https://api.twitter.com/1.1/trends/place.json?id=1");
	        	httpget.addHeader("Authorization", "Bearer "+accessToken);
	        	
	        	response = httpclient.execute(httpget);
	        	
	        	String data = EntityUtils.toString(response.getEntity());
	        	analyzeTrends(data);
	        }
	        
	    } 
	    
	    catch (Exception e) {
	        e.printStackTrace();
	    }
			
	}
	
	private static void analyzeTrends(String jsonString) throws Exception {
		
		if(jsonString.startsWith("[")) {
			jsonString = jsonString.substring(1, jsonString.length()-1);
		}
		
		ObjectMapper mapper = new ObjectMapper();
		TweetObjectsArray tweetList = mapper.readValue(jsonString, TweetObjectsArray.class);
		
		List<TweetObjectStructure> list = tweetList.items;
		
		list = list.stream().filter(i->i.getName().startsWith("#"))
					.sorted(Comparator.comparing(TweetObjectStructure::getTweetVolume).reversed())
					.collect(Collectors.toList());
		
		List<String> lines =  Files.readAllLines(
				Paths.get(App.class.getResource("template.html").toURI()), Charset.defaultCharset());
		
		int count = 6;
		String s = "[\"Tweet\", \"Volume\", { role: \"style\" } ],";
		lines.add(count++, s);
		
		for(int n=0;n<10;n++) {
			TweetObjectStructure tweet = list.get(n);			
			if(n==10) {
				s = "[\""+tweet.getName()+"\", "+tweet.getTweetVolume()+", \""+randomColor()+"\" ]";
			}
			else {			
				s = "[\""+tweet.getName()+"\", "+tweet.getTweetVolume()+", \""+randomColor()+"\" ],";
			}
			lines.add(count++, s);			
		}
		
		File outputFile = new File(App.class.getResource("output.html").getPath());
		try(FileWriter writer = new FileWriter(outputFile, false)) {
			
			lines.forEach(line->{
								try{
										writer.write(line+System.lineSeparator());
									}
								catch(Exception e) {
									e.printStackTrace();
								}
							}
						);
			writer.flush();
			writer.close();
			
			Desktop.getDesktop().browse(outputFile.toURI());			
		}
		
		list.subList(0, 9).forEach(tw->System.out.println("Hash Tag: "+tw.getName()+" Volume: "+tw.getTweetVolume()));
	}
	
	public static String randomColor () {
	    Random rand = new Random();
	    Color color =  new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
	    return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
	}

}
