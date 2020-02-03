javac -cp servlet-api-2.5.jar:jetty-all-7.0.2.v20100331.jar ContinuousIntegrationServer.java &&

JETTY_VERSION=7.0.2.v20100331 &&
java -cp .:servlet-api-2.5.jar:jetty-all-$JETTY_VERSION.jar ContinuousIntegrationServer
