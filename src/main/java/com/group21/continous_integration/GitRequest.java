package com.group21.continous_integration;

import org.json.JSONObject;
public class GitRequest{
    public String author;
    public String repository;
    public String cloneUrl;


    public GitRequest(String payload){
        //make JSON object
        JSONObject jsonObject = new JSONObject(payload);
        
        
        //Set all parameters
        this.author = jsonObject.getJSONObject("pusher").get("name").toString();
        this.repository = jsonObject.getJSONObject("repository").get("name").toString();
        this.cloneUrl = jsonObject.getJSONObject("repository").get("clone_url").toString();
    }

}