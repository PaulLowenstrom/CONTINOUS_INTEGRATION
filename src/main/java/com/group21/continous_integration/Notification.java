package com.group21.continous_integration;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.*;
import org.json.JSONObject;

import java.nio.file.Path;
import java.nio.file.Paths;


public class Notification
{
  /*
  Sends build sucess state to github
{
  "state": "success",
  "target_url": "https://example.com/build/status",
  "description": "The build succeeded!",
  "context": "continuous-integration/jenkins"
}
*/
  public static void sendBuildSuccess(HttpServletResponse response, String branchName) throws IOException, ServletException {
    JSONObject message = new JSONObject();

    message.put("state", "success");
    message.put("description", "The build succeeded!" + " see #" + branchName);
    message.put("context", "continuous-integration");

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().println(message);
    System.out.println(message);


  }
  /*
  *Sends build failure to github
{
  "state": "failure",
  "description": "The build failed!",
  "context": "continuous-integration/jenkins"
}
*/
  public static void sendBuildFailure(HttpServletResponse response, String branchName) throws IOException, ServletException {
    JSONObject message = new JSONObject();

    message.put("state", "failure");
    message.put("description", "The build failed!" + " see #" + branchName);
    message.put("context", "continuous-integration");

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().println(message);
    System.out.println(message);
  }
}
