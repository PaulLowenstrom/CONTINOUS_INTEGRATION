package com.group21.continous_integration;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

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

        Integration integ = new Integration("https://github.com/perfah/CONTINOUS_INTEGRATION.git", "master");
        
        Integration.IntegrationResult compilationResult = integ.compile();
        System.out.println("* Compilation returned: " + compilationResult.toString());

        response.getWriter().println("CI job done: " + compilationResult.toString());
    }
}
