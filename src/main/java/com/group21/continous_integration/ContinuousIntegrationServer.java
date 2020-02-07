package com.group21.continous_integration;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.apache.commons.io.IOUtils;


/** 
 Skeleton of a ContinuousIntegrationServer which acts as webhook
 See the Jetty documentation for API documentation of those classes.
*/
public class ContinuousIntegrationServer extends AbstractHandler
{
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) 
        throws IOException, ServletException
    {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        System.out.println("* Handling request: " + target);


        if(request.getMethod().equals("POST")){
            String payload = IOUtils.toString(request.getReader());
            GitRequest req = new GitRequest(payload);  
           
        }

        if(target.equalsIgnoreCase("/")){
            Integration integ = new Integration("https://github.com/perfah/CONTINOUS_INTEGRATION.git", "master");
            
            BuildResult compilation = integ.build();
            BuildHistory.getInstance().insert(compilation);

            boolean testStatus = integ.runTest();

            System.out.println("* Test returned: " + testStatus);

            System.out.println("* Compilation returned: " + compilation.status);
    
            response.getWriter().println("CI job done: " + (compilation.status && testStatus));
        }
        else if(target.equalsIgnoreCase("/history")){
            

            String specifiedBuild = request.getParameter("build");
            if(specifiedBuild != null){        
                System.out.println("* Sending info for build #" + specifiedBuild);        
                response.getWriter().println(BuildHistory.getInstance().getBuildInfoWebPage(Integer.parseInt(specifiedBuild)));
            }
            else{
                System.out.println("* Sending build history list");
                response.getWriter().println(BuildHistory.getInstance().getBuildListWebPage());
            }
        }
    }
}
