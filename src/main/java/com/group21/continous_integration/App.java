package com.group21.continous_integration;

import org.eclipse.jetty.server.Server;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class App 
{
    public static Logger logger = Logger.getLogger(App.class);

    // used to start the CI server in command line
    public static void main(String[] args) throws Exception
    {
        System.out.println("* Starting CI-service...");
        PropertyConfigurator.configure("log4j.properties");

        BuildHistory.load();
        

        Server server = new Server(8080);
        server.setHandler(new ContinuousIntegrationServer()); 
        server.start();
        server.join();
    }
}
