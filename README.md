# fetch-twitter-feed
Sample program to fetch twitter feed and show trending HashTags

A java program that fetches feed from Twitter by calling Authenticated API's and extracts the Trending Hash Tags of the Tweets and display the output in html.

The flow is explained below 👇.

# The setup consists of 4 steps:
1. Create a Twitter Developer Account
2. Register an app after logging into developer account
3. Add the app's ```key``` and ```secret``` in java program
4. Run the program and see output as html chart

## Problem

- Find top 10 trending Hash Tags
Assumptions & notes : 
1) A tweet is a text being input by tweeters. 
2) A main method in a java class to be implemented which takes the tweet as an input. 
3) You need to extract hashtag from a tweet text (Ex: sachin is hashtag in the tweet -> " Worlds best cricketer is #sachin") 
4) Maintain a data structure that keeps tracking of the count of each hashtag that is coming to your main method 
5) print the list of top 10 hashtags at the end of main method execution 

## Get Trending Tweets from Twitter

Fetching trending tweets simply translates to fetching sample tweets by call the REST API: ```https://api.twitter.com/1.1/trends/place.json?id=1```


The JSON data is retrieved and required fields from the response is extracted. For our problem, we need the following fields: ```name```, ```tweet_volume```


## Process to calculate the trend stats
Once, the required data is extracted, the extracted JSON string is appended to html.The html file graphically represents the data Hash Tag of the name on horizontal axis and tweet Volume on vertical axis. The same data is printed on command line before exit.

As seen from the attached images, it can be seen that the java program shows Trending HashTags.
