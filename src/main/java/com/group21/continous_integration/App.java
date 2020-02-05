package com.group21.continous_integration;

import java.io.File;
import java.util.ArrayList;
import org.eclipse.jetty.server.Server;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import org.apache.maven.shared.invoker.*;

public class App 
{
    public static Logger logger = Logger.getLogger(App.class);

    // used to start the CI server in command line
    public static void main(String[] args) throws Exception
    {
        System.out.println("Starting...");
        PropertyConfigurator.configure("log4j.properties");

 
        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile( new File( "/home/perfah/Programming/CONTINOUS_INTEGRATION/pom.xml" ) );

        ArrayList<String> lst = new ArrayList<String>();
        lst.add("test");
        request.setGoals( lst );
         
        Invoker invoker = new DefaultInvoker();
        InvocationResult result = invoker.execute( request );
        
        result.getExecutionException();
        System.out.println("=======================================");
        System.out.println("EXIT CODE = " + result.getExitCode());
        System.out.println("ERROR");
        
        System.out.println("END");

        Server server = new Server(8080);
        server.setHandler(new ContinuousIntegrationServer()); 
        server.start();
        server.join();
    }
}
