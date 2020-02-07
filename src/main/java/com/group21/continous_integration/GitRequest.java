package com.group21.continous_integration;

import org.json.JSONObject;
import org.json.JSONException;
public class GitRequest{
    public String author;
    public String repository;
    public String cloneUrl;
    public String commit_hash;
    public String branch;
    public String email_addr;
    public String statuses_url;

    public GitRequest(String payload){
        //make JSON object
        try {
            JSONObject jsonObject = new JSONObject(payload);
        
            //Set all parameters
            this.author = jsonObject.getJSONObject("pusher").get("name").toString();
            this.repository = jsonObject.getJSONObject("repository").get("name").toString();
            this.cloneUrl = jsonObject.getJSONObject("repository").get("clone_url").toString();
            this.branch = jsonObject.get("ref").toString();
            this.commit_hash = jsonObject.getJSONObject("head_commit").get("id").toString();
            this.email_addr = jsonObject.getJSONObject("pusher").get("email").toString();
            this.statuses_url = jsonObject.getJSONObject("repository").get("statuses_url").toString();
            
        } catch (JSONException e) {
            return;
        }
       
    }

}