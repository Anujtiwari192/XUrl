package com.crio.shorturl;

import java.util.HashMap;
import java.util.Random;
import java.util.Map.Entry;

class XUrlImpl implements XUrl{
    static final String ALPHA_NUM_STRING = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    HashMap<String, String>urlMapShortToLong;
    HashMap<String, String>urlMapLongToShort;
    HashMap<String, Integer>urlMapLongToHitCount;
    XUrlImpl(){
        urlMapShortToLong = new HashMap<>();
        urlMapLongToShort = new HashMap<>();
        urlMapLongToHitCount= new HashMap<>();
    }

    private String generateShortUrl_helper(String longUrl){
    
    StringBuilder random_stringBuilder = new StringBuilder();
    Random r = new Random();
    for (int i = 0; i < 9; i++) {
        random_stringBuilder.append( ALPHA_NUM_STRING.charAt(r.nextInt(ALPHA_NUM_STRING.length())));
    } 

        String Random_9_Alphanumeric = random_stringBuilder.toString();
        return "http://short.url/"+Random_9_Alphanumeric;
    }

    public String registerNewUrl(String longUrl){
        String shortUrl;
        if(!urlMapLongToShort.containsKey(longUrl))
        {
            // generate a short url mapping
            shortUrl = this.generateShortUrl_helper(longUrl);
            this.registerNewUrl(longUrl, shortUrl);
        }
        else
        {
            shortUrl = urlMapLongToShort.get(longUrl);
        }
        return shortUrl;
    }

    public String registerNewUrl(String longUrl, String shortUrl){
        // System.out.println(longUrl+"   "+shortUrl);
        if(!urlMapShortToLong.containsKey(shortUrl))
        {
            urlMapLongToShort.put(longUrl, shortUrl);
            urlMapShortToLong.put(shortUrl, longUrl);
            return shortUrl;
        }
        return null;
    }

    public String delete(String longUrl){
        String res = "Long url not found";
        if(urlMapLongToShort.containsKey(longUrl)){
            String shortUrl = urlMapLongToShort.get(longUrl);
            urlMapShortToLong.remove(shortUrl);
            urlMapLongToShort.remove(longUrl);
        }
        return res;
    }

    public String getUrl(String shortUrl){
        String longUrl = null;
        if(urlMapShortToLong.containsKey(shortUrl))
        {
            longUrl = urlMapShortToLong.get(shortUrl);
            Integer hitCount = urlMapLongToHitCount.getOrDefault(longUrl, 0)+1;
            urlMapLongToHitCount.put(longUrl, hitCount);
        }
       return longUrl;
    }

    public Integer getHitCount(String longUrl){
        return urlMapLongToHitCount.getOrDefault( longUrl ,0);
    }


}