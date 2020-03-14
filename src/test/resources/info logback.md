

import org.slf4j.MDC;

MDC.put("RequestId", requestId); //Here is the trick, by putting the requestId into the the SLF4J MDC with name of "RequestId" this is the name what we defined in our "logback.xml" file under the <pattern> node.


<!-- scan for configuration modifications every 60 secs. -->
    <configuration scan="true" scanPeriod="60000 milliseconds">
        <!--Include a configuration file from an external folder. We get to root path from enviroment an variable which is set in the tomcat's setenv.sh file.-->
        <include file="./logback-included.xml"/> 
    </configuration>
    
    