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

        GitRequest req = null;
        if(request.getMethod().equals("POST")){
            
            String payload = IOUtils.toString(request.getReader());
            
            req = new GitRequest(payload); 
            /* //PRINTS FOR TESTING
            System.out.println(req.email_addr);
            System.out.println(req.commit_hash);
            System.out.println(req.branch);
            System.out.println(req.cloneUrl);
            System.out.println(req.repository);
            System.out.println(req.author);
            System.out.println(req.statuses_url);
            */
            
        }

        if(target.equalsIgnoreCase("/")){
            Integration integ = new Integration("https://github.com/perfah/CONTINOUS_INTEGRATION.git", req);
            
            BuildResult compilation = integ.build();
            BuildHistory.getInstance().insert(compilation);


            System.out.println("* Compilation returned: " + compilation.status);
    
            response.getWriter().println("CI job done: " + compilation.status);
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
